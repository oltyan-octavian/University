using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Data.SqlClient;

namespace Lab_5_csharp
{
    internal static class Program
    {

        static void Main(string[] args)
        {
            Console.WriteLine("Hello, World!");
            string connectionString = @"Server=DESKTOP-J8BBV28\SQLEXPRESS;" +
                   " Database=lab4Tranzactii; Integrated Security = true;" +
                   " TrustServerCertificate=true;";

            int retryCount = 0;
            bool success = false;

            while (!success && retryCount < 3)
            {
                Console.WriteLine("Retry count: " + retryCount);

                Thread thread1 = new Thread(() =>
                {
                    Console.WriteLine("Thread1 is running!");

                    using (SqlConnection connection = new SqlConnection(connectionString))
                    {
                        connection.Open();

                        // Set the deadlock priority to HIGH
                        using (SqlCommand setDeadlockPriorityCommand = connection.CreateCommand())
                        {
                            setDeadlockPriorityCommand.CommandText = "SET DEADLOCK_PRIORITY HIGH";
                            setDeadlockPriorityCommand.ExecuteNonQuery();
                        }

                        // Create a new transaction
                        using (SqlTransaction transaction = connection.BeginTransaction())
                        {
                            try
                            {
                                using (SqlCommand command = connection.CreateCommand())
                                {
                                    command.Transaction = transaction;

                                    // Update statement 1
                                    command.CommandText = "UPDATE Movies SET title = 'csDEADLOCK1' WHERE id = 13;";
                                    command.ExecuteNonQuery();

                                    // Delay for 7 seconds
                                    Thread.Sleep(6000);

                                    // Update statement 2
                                    command.CommandText = "UPDATE Producers SET name_p = 'csDEADLOCK1' WHERE id = 2;";
                                    command.ExecuteNonQuery();
                                }

                                // Commit the transaction
                                transaction.Commit();
                                Console.WriteLine("Transaction 1 committed successfully.");
                                success = true;
                            }
                            catch (SqlException ex)
                            {
                                if (ex.Number == 1205) // Deadlock error number
                                {
                                    // Handle deadlock, rollback the transaction, and retry
                                    Console.WriteLine("Deadlock occurred with trans1. Retrying...");

                                    transaction.Rollback();
                                    Console.WriteLine("Transaction 1 rolled back.");
                                    retryCount++;
                                }
                                else
                                {
                                    // Handle other exceptions
                                    Console.WriteLine("Error occurred: " + ex.Message);
                                    transaction.Rollback();
                                    Console.WriteLine("Transaction 1 rolled back.");
                                }
                            }
                        }
                    }
                });

                Thread thread2 = new Thread(() =>
                {
                    Console.WriteLine("Thread2 is running!");
                    using (SqlConnection connection = new SqlConnection(connectionString))
                    {
                        connection.Open();

                        // Create a new transaction
                        using (SqlTransaction transaction = connection.BeginTransaction())
                        {
                            try
                            {
                                using (SqlCommand command = connection.CreateCommand())
                                {
                                    command.Transaction = transaction;

                                    // Update statement 1
                                    command.CommandText = "UPDATE Producers SET name_p = 'csDEADLOCK2' WHERE id = 2;";
                                    command.ExecuteNonQuery();

                                    // Delay for 7 seconds
                                    Thread.Sleep(7000);

                                    // Update statement 2
                                    command.CommandText = "UPDATE Movies SET title = 'csDEADLOCK2' WHERE id = 13;";
                                    command.ExecuteNonQuery();
                                }

                                // Commit the transaction
                                transaction.Commit();
                                Console.WriteLine("Transaction 2 committed successfully.");
                                success = true;
                            }
                            catch (SqlException ex)
                            {
                                if (ex.Number == 1205) // Deadlock error number
                                {
                                    // Handle deadlock, rollback the transaction, and retry
                                    Console.WriteLine("Deadlock occurred at trans 2. Retrying...");

                                    transaction.Rollback();
                                    Console.WriteLine("Transaction 2 rolled back.");
                                    retryCount++;
                                }
                                else
                                {
                                    // Handle other exceptions
                                    Console.WriteLine("Error occurred with trans 2: " + ex.Message);
                                    transaction.Rollback();
                                    Console.WriteLine("Transaction 2 rolled back.");
                                }
                            }
                        }
                    }
                });

                thread1.Start();
                thread2.Start();
                thread1.Join();
                thread2.Join();
            }

            if (retryCount >= 3)
            {
                Console.WriteLine("Exceeded maximum retry attempts. Aborting.");
            }
            else
            {
                Console.WriteLine("All transactions completed.");
            }
        }
    }
}
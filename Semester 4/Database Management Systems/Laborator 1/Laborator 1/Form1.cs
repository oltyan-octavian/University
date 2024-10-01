using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Laborator_1
{
    public partial class Form1 : Form
    {
        string connectionString = @"Server=Octa-Laptop\SQLEXPRESS;Database=Muzee;
Integrated Security=true;TrustServerCertificate=true;";
        DataSet ds = new DataSet();
        SqlDataAdapter parentAdapter;
        SqlDataAdapter childAdapter;
        BindingSource bsParent;
        BindingSource bsChild;
        
        public Form1()
        {
            InitializeComponent();
        }
    
        private void Form1_Load(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(connectionString))
                {

                    conn.Open();
                    MessageBox.Show("Starea conexiunii: " + conn.State.ToString());
                    parentAdapter = new SqlDataAdapter("SELECT * FROM Muzeu;",
                        conn);
                    childAdapter = new SqlDataAdapter("SELECT * FROM Expozitie;",
                        conn);
                    parentAdapter.Fill(ds, "Muzeu");
                    childAdapter.Fill(ds, "Expozitie");
                    bsParent = new BindingSource();
                    bsParent.DataSource = ds.Tables["Muzeu"];
                    dataGridViewParent.DataSource = bsParent;
                    dataGridViewParent.ClearSelection();
                    DataColumn parentColumn = ds.Tables["Muzeu"].Columns["MuzeuID"];
                    DataColumn childColumn = ds.Tables["Expozitie"].Columns["MuzeuID"];
                    DataRelation relation = new DataRelation("FK__Expozitie__Muzeu__07C12930", parentColumn, childColumn);
                    ds.Relations.Add(relation);
                    bsChild = new BindingSource();

                    bsChild.DataSource = bsParent;
                    bsChild.DataMember = "FK__Expozitie__Muzeu__07C12930";
                    dataGridViewChild.DataSource = bsChild;


                    dataGridViewParent.ClearSelection();
                    dataGridViewChild.ClearSelection();

                    

                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }


        }

        private void dataGridViewChild_SelectionChanged(object sender, EventArgs e)
        {
            if (dataGridViewChild.SelectedRows.Count > 0)
            {
                DataGridViewRow selectedRow = dataGridViewChild.SelectedRows[0];

                string expNume = Convert.ToString(selectedRow.Cells["Nume"].Value);
                DateTime expData = Convert.ToDateTime(selectedRow.Cells["DataExpozitie"].Value);
                string expTip = Convert.ToString(selectedRow.Cells["Tip"].Value);


                repairCostTextBox.Text = expNume;
                dateTimePicker1.Value = expData;
                repairDescriptionTextBox.Text = expTip;

            }
        }
        

        private void label1_Click_1(object sender, EventArgs e)
        {
        }
        
        private void addButton_Click(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    parentAdapter.SelectCommand.Connection = connection;
                    childAdapter.SelectCommand.Connection = connection;
                    childAdapter.InsertCommand =
                        new SqlCommand(
                            "Insert into Expozitie(MuzeuID,DataExpozitie,Tip, Nume) values (@c1,@c2,@c3,@c4)",
                            connection);
                    int id = int.Parse(dataGridViewParent.SelectedRows[0].Cells["MuzeuID"].Value.ToString());
                    childAdapter.InsertCommand.Parameters.Add("@c1", SqlDbType.Int).Value = id;
                    childAdapter.InsertCommand.Parameters.Add("@c2", SqlDbType.DateTime).Value = dateTimePicker1.Value;
                    childAdapter.InsertCommand.Parameters.Add("@c3", SqlDbType.VarChar).Value =
                        repairDescriptionTextBox.Text.Trim();
                    childAdapter.InsertCommand.Parameters.Add("@c4", SqlDbType.VarChar).Value =
                        repairCostTextBox.Text.Trim();
                    connection.Open();
                    childAdapter.InsertCommand.ExecuteNonQuery();
                    MessageBox.Show("Inserted successfully in the database");
                    connection.Close();

                    ds.Tables["Expozitie"].Clear();
                    ds.Tables["Muzeu"].Clear();
                    parentAdapter.Fill(ds, "Muzeu");
                    childAdapter.Fill(ds, "Expozitie");

                    dataGridViewParent.ClearSelection();
                    dataGridViewChild.ClearSelection();

                    repairCostTextBox.Text = "";
                    repairDescriptionTextBox.Text = "";
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

        }
        
        private void deleteButton_Click(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    parentAdapter.SelectCommand.Connection = connection;
                    childAdapter.SelectCommand.Connection = connection;

                    childAdapter.DeleteCommand = new SqlCommand("Delete from Expozitie where ExpozitieID=@id", connection);

                    int id = int.Parse(dataGridViewChild.SelectedRows[0].Cells["ExpozitieID"].Value.ToString());
                    childAdapter.DeleteCommand.Parameters.Add("@id", SqlDbType.Int).Value = id;

                    connection.Open();
                    childAdapter.DeleteCommand.ExecuteNonQuery();
                    MessageBox.Show("Deleted succesfully from the database");
                    connection.Close();

                    ds.Tables["Expozitie"].Clear();
                    ds.Tables["Muzeu"].Clear();
                    parentAdapter.Fill(ds, "Muzeu");
                    childAdapter.Fill(ds, "Expozitie");

                    dataGridViewParent.ClearSelection();
                    dataGridViewChild.ClearSelection();

                    repairCostTextBox.Text = "";
                    repairDescriptionTextBox.Text = "";

                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

        }
        
        private void updateButton_Click(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    parentAdapter.SelectCommand.Connection = connection;
                    childAdapter.SelectCommand.Connection = connection;


                    childAdapter.UpdateCommand = new SqlCommand("Update Expozitie set Nume=@c1,Tip=@c2, DataExpozitie=@c3 where ExpozitieID=@id", connection);


                    childAdapter.UpdateCommand.Parameters.Add("@c1", SqlDbType.VarChar).Value = repairCostTextBox.Text.Trim();
                    childAdapter.UpdateCommand.Parameters.Add("@c2", SqlDbType.VarChar).Value = repairDescriptionTextBox.Text.Trim();
                    childAdapter.UpdateCommand.Parameters.Add("@c3", SqlDbType.DateTime).Value = dateTimePicker1.Value;
                    int id = int.Parse(dataGridViewChild.SelectedRows[0].Cells["ExpozitieID"].Value.ToString());
                    childAdapter.UpdateCommand.Parameters.Add("@id", SqlDbType.Int).Value = id;
                    connection.Open();
                    childAdapter.UpdateCommand.ExecuteNonQuery();
                    MessageBox.Show("Updated succesfully");
                    connection.Close();

                    ds.Tables["Expozitie"].Clear();
                    ds.Tables["Muzeu"].Clear();
                    parentAdapter.Fill(ds, "Muzeu");
                    childAdapter.Fill(ds, "Expozitie");

                    dataGridViewParent.ClearSelection();
                    dataGridViewChild.ClearSelection();

                    repairCostTextBox.Text = "";
                    repairDescriptionTextBox.Text = "";
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void label2_Click(object sender, EventArgs e)
        {
            throw new System.NotImplementedException();
        }
    }
    
    
}
package ro.ubbcluj.cs.map.ui;

import ro.ubbcluj.cs.map.domain.Friendship;
import ro.ubbcluj.cs.map.domain.User;
import ro.ubbcluj.cs.map.service.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Console implements ConsoleInterface {

    private Service<Long> serv;

    public Console(Service serv) {
        this.serv = serv;
    }

    public void AddUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("The first name of the user is: ");
        String firstName = scanner.nextLine();

        System.out.print("The last name of the user is: ");
        String lastName = scanner.nextLine();

        try {
            User user = new User(firstName, lastName);
            if (serv.addUser(user)) {
                System.out.println("User added successfully!");
            } else {
                System.out.println("Couldn't add user!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void DeleteUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("The ID of the deleted user is: ");
        Long id = scanner.nextLong();
        try {
            if (serv.deleteUser(id)) {
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("Couldn't delete user!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void PrintAllUsers() {
        if (serv.getAllUsers() == null) {
            System.out.println("There aren't any users!");
        } else {
            serv.getAllUsers().forEach(System.out::println);
        }
    }

    public void AddFriendship() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("The ID of the first user is: ");
        var id1 = scanner.nextLong();

        System.out.print("The ID of the second user is: ");
        var id2 = scanner.nextLong();

        try {
            if (serv.addFriendship(id1, id2)) {
                System.out.println("Friendship added successfully!");
            } else {
                System.out.println("Couldn't add friendship!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void DeleteFriendship() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("The ID of the first user is: ");
        var id1 = scanner.nextLong();

        System.out.print("The ID of the second user is: ");
        var id2 = scanner.nextLong();

        try {
            if (serv.deleteFriendship(id1, id2)) {
                System.out.println("Friendship deleted successfully!");
            } else {
                if (serv.deleteFriendship(id2, id1)) {
                    System.out.println("Friendship deleted successfully!");
                } else {
                    System.out.println("Couldn't delete friendship!");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void PrintAllFriendships() {
        if (serv.getAllFriendships() == null) {
            System.out.println("There are no friendships!");
        } else {
            serv.getAllFriendships().forEach(System.out::println);
        }
    }

    public void FriendsOfUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("The ID of the user is: ");
        var id = scanner.nextLong();

        try {
            if (serv.getFriendshipsOfUser(id) == null) {
                System.out.println("The user has no friends!");
            } else {
                serv.getFriendshipsOfUser(id).forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void PrintNumberOfCommunities() {
        int nr = serv.Communities();
        if (nr == 0) {
            System.out.println("There are no communities!");
        } else {
            System.out.printf("The number of communities is: %d\n", nr);
        }
    }

    public void PrintMostSociable() {
        List lst = serv.Sociable();
        if (lst.isEmpty()) {
            System.out.println("There are no communities!");
        } else {
            lst.forEach(System.out::println);
        }
    }

    public void PrintFriendshipMonth() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("User ID: ");
        var id1 = scanner.nextLong();
        System.out.println("Month of friendship: ");
        var month = scanner.next();
        List<Friendship> all = serv.FriendshipMonth(id1, month);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            if (all.isEmpty()) {
                System.out.println("The user didn't make any friends in that specific month!");
            } else {
                System.out.println("The friends of the user with this id: " + id1 + " are:");
                all.forEach(p -> {
                    if (p.getUser1().getId().equals(id1)) {
                        System.out.println(p.getUser2().getFirstName() + " | "
                                + p.getUser2().getLastName() + " | " + formatter.format(p.getDate()));
                    } else {
                        System.out.println(p.getUser1().getFirstName() + " | "
                                + p.getUser1().getLastName() + " | " + formatter.format(p.getDate()));
                    }
                });
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void execute() {

        while (true) {
            System.out.println("\n\nMenu:");
            System.out.print("adduser - Adds an user\n");
            System.out.print("deleteuser - Deletes an user and all of his friendships\n");
            System.out.print("allusers - Shows all users\n");
            System.out.print("addfriendship - Adds a friendship between users\n");
            System.out.print("deletefriendship - Deletes a friendship between users\n");
            System.out.print("allfriendships - Shows all friendships between users\n");
            System.out.print("friendsof - Shows every friendship a specific user has\n");
            System.out.print("numberofcoms - Shows the number of communities between users\n");
            System.out.print("mostsociable - Shows the most sociable community between users\n");
            System.out.print("friendshipmonth - Shows the friends that a user made in a specific month\n");
            System.out.print("exit - Closes the application\n");
            System.out.print("Command: ");

            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();

            switch (choice) {
                case "adduser":
                    AddUser();
                    break;
                case "deleteuser":
                    DeleteUser();
                    break;
                case "allusers":
                    PrintAllUsers();
                    break;
                case "addfriendship":
                    AddFriendship();
                    break;
                case "deletefriendship":
                    DeleteFriendship();
                    break;
                case "allfriendships":
                    PrintAllFriendships();
                    break;
                case "friendsof":
                    FriendsOfUser();
                    break;
                case "numberofcoms":
                    PrintNumberOfCommunities();
                    break;
                case "mostsociable":
                    PrintMostSociable();
                    break;
                case "friendshipmonth":
                    PrintFriendshipMonth();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("You need to choose between the available options!");
            }
        }
    }
}

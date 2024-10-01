package ui;

import domain.User;
import service.Service;

import java.util.List;
import java.util.Scanner;

public class Console implements ConsoleInterface {

    private Service serv;

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
            User user = new User(lastName, firstName);
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
            if (!serv.deleteUser(id)) {
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
            for (var u : serv.getAllUsers()) {
                System.out.println(u);
            }
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
            if (!serv.deleteFriendship(id1, id2)) {
                System.out.println("Friendship deleted successfully!");
            } else {
                if (!serv.deleteFriendship(id2, id1)) {
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
            for (var u : serv.getAllFriendships()) {
                System.out.println(u);
            }
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
                for (var ut : serv.getFriendshipsOfUser(id)) {
                    System.out.println(ut);
                }
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
            for (var ut : lst) {
                System.out.println(ut);
            }
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
                case "exit":
                    return;
                default:
                    System.out.println("You need to choose between the available options!");
            }
        }
    }
}

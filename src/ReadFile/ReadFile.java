package ReadFile;

import java.sql.*;
import java.io.*;
import java.util.Scanner;

public class ReadFile {

    public static void main(String[] args) throws IOException, SQLException, InterruptedException {

        DBManager dbManager = new DBManager();
        dbManager.initDataBase();

        Scanner text = new Scanner(System.in);

        boolean exit = false;

        while (!exit) {

            System.out.println("Press [1] to add new contact.");
            System.out.println("Press [2] to delete contact.");
            System.out.println("Press [3] to find contact by name.");
            System.out.println("Press [4] to find contact by surname.");
            System.out.println("Press [5] to find contact by phone number.");
            System.out.println("Press [6] to update contact.");
            System.out.println("Press [7] to show all contacts.");
            System.out.println("Press 'q' for quit.");
            System.out.print("Take your choice: ");

            char chr = text.next(".").charAt(0);

            switch (chr) {
                case '1':
                {
                    text.nextLine();
                    System.out.println();
                    System.out.print("Enter a name: ");
                    String name = text.nextLine();
                    name = name.substring(0, 1).toUpperCase() + name.substring(1);
                    System.out.print("Enter a surname: ");
                    String surname = text.nextLine();
                    surname = surname.substring(0, 1).toUpperCase() + surname.substring(1);
                    System.out.print("Enter a phone: ");
                    String phone = text.nextLine();
                    dbManager.addContact(name, surname, phone);
                    System.out.println();
                    break;
                }
                case '2':
                {
                    text.nextLine();
                    System.out.println();
                    System.out.print("Enter ID: ");
                    String str = text.nextLine();
                    int id = Integer.parseInt(str);
                    dbManager.deleteRowByID(id);
                    System.out.println();
                    break;
                }
                case '3':
                {
                    text.nextLine();
                    System.out.println();
                    System.out.print("Enter a name: ");
                    String name = text.nextLine();
                    name = name.substring(0, 1).toUpperCase() + name.substring(1);
                    dbManager.findByName(name);
                    System.out.println();
                    break;
                }
                case '4':
                {
                    text.nextLine();
                    System.out.println();
                    System.out.print("Enter a surname: ");
                    String surname = text.nextLine();
                    surname = surname.substring(0, 1).toUpperCase() + surname.substring(1);
                    dbManager.findBySurname(surname);
                    System.out.println();

                    break;
                }
                case '5':
                {
                    text.nextLine();
                    System.out.println();
                    System.out.print("Enter a phone number: ");
                    String phone = text.nextLine();
                    dbManager.findByPhone(phone);
                    System.out.println();
                    break;
                }
                case '6':
                {
                    text.nextLine();
                    System.out.println();
                    System.out.print("Enter ID number: ");
                    String idString = text.nextLine();
                    int id = Integer.parseInt(idString);
                    System.out.print("Enter a name: ");
                    String name = text.nextLine();
                    name = name.substring(0, 1).toUpperCase() + name.substring(1);
                    System.out.print("Enter a surname: ");
                    String surname = text.nextLine();
                    surname = surname.substring(0, 1).toUpperCase() + surname.substring(1);
                    System.out.print("Enter a phone: ");
                    String phone = text.nextLine();
                    dbManager.updateDB(id, name, surname, phone);
                    System.out.println();
                    break;
                }
                case '7':
                {
                    text.nextLine();
                    System.out.println();
                    dbManager.tableContent();
                    System.out.println();
                    break;
                }
                case 'q':
                {
                    System.out.println();
                    System.out.println("Excellent choice.");
                    exit = true;
                    break;
                }
                default:
                {
                    System.out.println();
                    System.out.println("Choose wisely.");
                    System.out.println();
                    break;
                }
            }
        }
        text.close();
    }
}
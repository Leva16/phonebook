package ReadFile;

import java.sql.*;
import java.io.*;
import java.util.Scanner;

public class ReadFile {

    public static void main(String[] args) throws IOException, SQLException, InterruptedException {

        DBManager dbManager = new DBManager();
        InputAndCheck inputAndCheck = new InputAndCheck();

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

            String str1 = "";

            while (str1.length() == 0) {
                System.out.print("Take your choice: ");
                str1 = text.nextLine();
            }

            char chr = str1.charAt(0);

            switch (chr) {
                case '1':
                {
                    String name = inputAndCheck.checkInputName();
                    String surname = inputAndCheck.checkInputSurname();
                    String phone = inputAndCheck.checkInputPhone();
                    dbManager.addContact(name, surname, phone);
                    break;
                }
                case '2':
                {
                    dbManager.deleteRowByID(inputAndCheck.checkInputID());
                    System.out.println();
                    break;
                }
                case '3':
                {
                    dbManager.findByName(inputAndCheck.checkInputName());
                    break;
                }
                case '4':
                {
                    dbManager.findBySurname(inputAndCheck.checkInputSurname());
                    break;
                }
                case '5':
                {
                    dbManager.findByPhone(inputAndCheck.checkInputPhone());
                    break;
                }
                case '6':
                {
                    int id = inputAndCheck.checkInputID();
                    String name = inputAndCheck.checkInputName();
                    String surname = inputAndCheck.checkInputSurname();
                    String phone = inputAndCheck.checkInputPhone();
                    dbManager.updateDB(id, name, surname, phone);
                    break;
                }
                case '7':
                {
                    dbManager.tableContent();
                    break;
                }
                case 'q':
                {
                    System.out.println("\nExcellent choice.");
                    exit = true;
                    break;
                }
                default:
                {
                    System.out.println("\nChoose wisely.");
                    System.out.println();
                    break;
                }
            }
        }
        text.close();
    }
}
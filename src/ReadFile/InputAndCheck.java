package ReadFile;

import java.util.Scanner;

public class InputAndCheck {

    private Scanner text = new Scanner(System.in);

    public String insideCheck(String ins) {
        String insUP = ins.substring(0, 1).toUpperCase() + ins.substring(1);
        String input = null;
        String match = (ins.equals("phone")) ? "[0-9\\-]+" : "[a-zA-Z\\-]+";
        boolean check = false;
        while (!check) {
            System.out.print("\nEnter a " + ins+ ": ");
            input = text.nextLine().replaceAll("^\\s+","");
            if (input.length() == 0 || !(input.matches(match)) || input.matches("\\s+")) {
                System.out.println(insUP + " field is empty or contain illegal character, " +
                        "please write correct " + ins + ".");
            } else {
                input = input.substring(0, 1).toUpperCase() + input.substring(1);
                check = true;
            }
        }
        return input;
    }

    public String checkInputName() {
        return insideCheck("name");
    }

    public String checkInputSurname() {
        return insideCheck("surname");
    }

    public String checkInputPhone() {
        return insideCheck("phone");
    }

    public int checkInputID() {
        String input;
        boolean check = false;
        int id = 0;
        while (!check) {
            System.out.print("\nEnter ID number: ");
            input = text.nextLine();
            if (input.length() == 0 || !(input.matches("[0-9]+")) || input.matches("\\s-")) {
                System.out.println("ID field is empty or contain chars, please write correct ID.");
            } else {
                id = Integer.parseInt(input);
                check = true;
            }
        }
        return id;
    }
}
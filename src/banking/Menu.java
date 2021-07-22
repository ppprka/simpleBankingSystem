package banking;
import java.util.Scanner;
public class Menu {
    static Scanner sc = new Scanner(System.in);
    public static void exit(){
        System.out.println();
        System.out.println("Bye");
        System.out.println();
    }

    public static void menu(String fileName){
        boolean design = true;
        while (design) {
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
            int x = sc.nextInt();
            switch (x){
                case (1):
                    LoginHandler.createAccount(fileName);
                    break;
                case (2):
                    LoginHandler.logAccount(fileName);
                    design = false;
                    break;
                case (0):
                    exit();
                    design = false;
                    break;
            }
        }
    }
}

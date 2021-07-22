package banking;
import java.util.Scanner;
public class LoginHandler {

    static Scanner sc = new Scanner(System.in);

    public static void createAccount(String fileName){
        boolean design = true;
        while(design) {
            StringBuilder strBuilder = new StringBuilder();
            StringBuilder strBuilder1 = new StringBuilder();
            strBuilder.append(400000);
            for (int i = 0; i < 10; i++) {
                strBuilder.append((int) (Math.random() * 9));
            }
            String newCardNum = strBuilder.toString();
            if(algorithmLuhn(newCardNum)){
                for (int i = 0; i < 4; i++) {
                    strBuilder1.append((int) (Math.random() * 9));
                }
                String newPassword = strBuilder1.toString();
                System.out.println();
                InsertGetData data = new InsertGetData();
                data.insert(fileName,newCardNum,newPassword);
                System.out.println("Your card has been created");
                System.out.println("Your card number:");
                System.out.println(newCardNum);
                System.out.println("Your card PIN:");
                System.out.println(newPassword);
                System.out.println();
                design = false;
            }
        }

    }

    public static void logAccount(String fileName){
        System.out.println();
        System.out.println("Enter your card number:");
        String cardNum = sc.next();
        System.out.println("Enter your PIN:");
        String password = sc.next();
        InsertGetData examination = new InsertGetData();
        if(examination.get(fileName, cardNum,password)>=1){
            System.out.println();
            System.out.println("You have successfully logged in!");
            loggedIn(fileName, cardNum,password);
        }
        else{
            System.out.println("Wrong card number or PIN!");
            Menu.menu(fileName);
        }
    }

    public static void loggedIn(String fileName,String cardNum,String password){
        System.out.println();
        boolean design = true;
        InsertGetData data = new InsertGetData();
        while (design) {
            System.out.println("1. Balance");
            System.out.println("2. Add income");
            System.out.println("3. Do transfer");
            System.out.println("4. Close account");
            System.out.println("5. Log out");
            System.out.println("0. Exit");
            int x = sc.nextInt();
            switch (x) {
                case (1):
                    System.out.println();
                    System.out.println("Balance: "+data.balance(fileName,cardNum,password));
                    System.out.println();
                    break;
                case (2):
                    System.out.println();
                    System.out.println("Enter income:");
                    int balance = sc.nextInt();
                    data.addIncome(fileName, balance,cardNum);
                    System.out.println("Income was added!");
                    System.out.println();
                    break;
                case (3):
                    System.out.println();
                    System.out.println("Transfer");
                    System.out.println("Enter card number");
                    String toCard = sc.next();
                    if(algorithmLuhn(toCard)) {
                        if (data.existCard(fileName, toCard).equals(toCard)) {
                            if(!toCard.equals(cardNum)){
                                System.out.println("Enter how much money you want to transfer:");
                                int transferBalance = sc.nextInt();
                                if(data.balance(fileName,cardNum,password)>=transferBalance){
                                    data.doTransfer(fileName,transferBalance,cardNum,toCard);
                                    System.out.println("Success!");
                                }
                                else{
                                    System.out.println("Not enough money!");
                                }
                            }
                            else{
                                System.out.println("You can't transfer money to the same account!");
                            }
                        }
                        else{
                            System.out.println("Such a card does not exist.");
                        }
                    }
                    else{
                        System.out.println("Probably you made a mistake in the card number. Please try again!");
                    }
                    System.out.println();
                    break;
                case (4):
                    System.out.println();
                    data.closeAccount(fileName,cardNum);
                    System.out.println("The account has been closed!");
                    System.out.println();
                    Menu.menu(fileName);
                    design=false;
                    break;

                case (5):
                    System.out.println();
                    System.out.println("You have successfully logged out!");
                    System.out.println();
                    Menu.menu(fileName);
                    design=false;
                    break;
                case (0):
                    Menu.exit();
                    design=false;
                    break;
            }
        }
    }

    public static boolean algorithmLuhn(String newCardNum){
        int sum = 0;
        boolean result = false;
        for (int i = 1; i < 17; i++) {
            int z;
            char x = newCardNum.charAt(i-1);
            if(i%2!=0){
                z = Character.getNumericValue(x)*2;
                if(z>9){
                    z=z-9;
                }
            }
            else{
                z = Character.getNumericValue(x);
            }
            sum=sum+z;
        }
        if (sum%10==0){
            result = true;
        }
        return result;
    }
}

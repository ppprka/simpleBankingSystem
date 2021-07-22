package banking;
public class Main {
    public static void main(String [] args){
        CreateNewDatabase.createNewDatabase(args[1]);
        Menu.menu(args[1]);
    }
}
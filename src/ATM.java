import javax.script.ScriptContext;
import java.awt.image.PackedColorModel;
import java.sql.Statement;
import java.util.Scanner;

public class ATM {



    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        //init bank
        Bank bank= new Bank("IICICI");

        User aUser= bank.addUser("acrahan","navale","1234");
//add acc
        Account account = new Account("Checking ", aUser,bank);
        aUser.addAccount(account);
        bank.addAccount(account);

        User currentUser;
        while (true){
            //stay in loging until succesfull loging
            currentUser= ATM.manMenuPromt(bank,sc);
//            stay in loop untill main menu quit
            ATM.printUserMenu(currentUser,sc);

        }


    }

    /**
     * Authenticate user
     * @param bank
     * @param sc
     * @return
     */

    public static  User manMenuPromt(Bank bank,Scanner sc){

        String userID;
        String pin ;
        User authUser;

        do {
            System.out.println("Wlcome to "+bank.getName());
            System.out.println("Enter User ID");
            userID= sc.nextLine();
            System.out.println("Enter PIN :");
            pin=sc.nextLine();

            //authenticate user
            authUser= bank.userLogIn(userID,pin);
            if(authUser==null) {
                System.out.println("Incorrect User&PIN Combination !!! \n  please try againn");
            }

            }while (authUser==null); //continue looping until scusccful login
        return authUser;

    }

    /**'
     * display menu and handle choices
     * @param theUser
     * @param sc
     */


    public static void printUserMenu(User theUser, Scanner sc) {
        //print summary of user acc
        theUser.printAccountSummary();
        int choice;
        do{
            //user menu
            System.out.println("Welcome !! "+theUser.getFirstName()+" what would you like to do ");
            System.out.println(" 1. Show account transaction history ");
            System.out.println(" 2. Withdrow amount");
            System.out.println(" 3. Deposit");
            System.out.println(" 4. Transfer");
            System.out.println( " 5. quit");

            System.out.println("Enter Choice:");
            choice= sc.nextInt();
            if(choice<1 || choice >5){
                System.out.println("Please Enter Correct choice between 1-5");
            }


        }while (choice<1 || choice >5);

        //process choice
        switch (choice){
            case 1:
                ATM.showTransactionHistory(theUser,sc);
                break;
            case 2:
                ATM.withrowFunds(theUser,sc);
                break;
            case 3:
                ATM.depositFunds(theUser,sc);
                break;
            case 4:
                ATM.TransferFunds(theUser,sc);
                break;
        }
        //Redisplay untill user quits
        if(choice !=5){
            ATM.printUserMenu(theUser,sc); //recurrsion if user dosnt quit then show again menu
        }
    }

    /**
     * show transaction from multiplr acc of user , At once one ac
     * @param theUser
     * @param sc
     */
    public static void showTransactionHistory(User theUser,Scanner sc){
        int theAcIndex;
        do {
            System.out.println("Enter The number of account you want to see "+ theUser.numAccounts());
            theAcIndex=sc.nextInt();
            if(theAcIndex<0 || theAcIndex>=theUser.numAccounts()){
                System.out.println("invalid choice");
            }
        }while(theAcIndex<0 || theAcIndex>=theUser.numAccounts());

        theUser.printAccountTransactionHistory(theAcIndex);

    }

    /**
     * Transaction
     * @param theUser
     * @param sc
     */
    public static void TransferFunds(User theUser, Scanner sc){
        int fromAcc;
        int toAcc;
        double accBalance;
        double amount;
        do {
            System.out.println("Enter account from transfer :");
            fromAcc=sc.nextInt()-1;
            if(fromAcc<0 || fromAcc >= theUser.numAccounts()){
                System.out.println("invalid acc");
            }
        }while (fromAcc<0 || fromAcc >= theUser.numAccounts());

        accBalance= theUser.getAccountBalance(fromAcc);
        //get account transfer to
        do {
            System.out.println("Enter account  transfer to :");
            toAcc=sc.nextInt()-1;
            if(toAcc<0 || toAcc >= theUser.numAccounts()){
                System.out.println("invalid acc");
            }
        }while (toAcc<0 || toAcc >= theUser.numAccounts());
//get amount to transfer
        do{
            System.out.println("enter amount to transfer: max "+accBalance);
            amount= sc.nextDouble();
            if(amount<0){
                System.out.println("ammount must be greatetr than 0");
            }else if(amount> accBalance){
                System.out.println("Amount must be less than "+accBalance);
            }
        }while (amount<0 || amount>accBalance);
        //do the transfer
        theUser.addAccountTransaction(fromAcc,-1*amount,
                String.format("Transfert to acc ",theUser.getAccUUID(fromAcc)));

    }

    /**
     * withrowal memu
     * @param theUser
     * @param sc
     */

    public static void withrowFunds(User theUser, Scanner sc){
        int fromAcc;
        double accBalance;
        double amount;
        String memo;

        do {
            System.out.println("Enter account from transfer :");
            fromAcc=sc.nextInt()-1;
            if(fromAcc<0 || fromAcc >= theUser.numAccounts()){
                System.out.println("invalid acc");
            }
        }while (fromAcc<0 || fromAcc >= theUser.numAccounts());

        accBalance= theUser.getAccountBalance(fromAcc);

//get amount to transfer
        do{
            System.out.println("enter amount to transfer: max "+accBalance);
            amount= sc.nextDouble();
            if(amount<0){
                System.out.println("ammount must be greater than 0");
            }else if(amount> accBalance){
                System.out.println("Amount must be less than "+accBalance);
            }
        }while (amount<0 || amount>accBalance);

        //get the memo
        System.out.println("\n Enter memo :");
        memo= sc.nextLine();

//        do the withrowal

        theUser.addAccountTransaction(fromAcc,-1*amount,memo);
    }

    public static void depositFunds(User theUser,Scanner sc){
        int toAcc;
        double accBalance = 0;
        double amount;
        String memo;
        do {
            System.out.println("Enter account  transfer to :");
            toAcc=sc.nextInt()-1;
            if(toAcc<0 || toAcc >= theUser.numAccounts()){
                System.out.println("invalid acc");
            }
        }while (toAcc<0 || toAcc >= theUser.numAccounts());
//get amount to transfer
        accBalance= theUser.getAccountBalance(toAcc);
        do{
            System.out.println("enter amount to deposite ");
            amount= sc.nextDouble();
            if(amount<0){
                System.out.println("ammount must be greatetr than 0");
            }
        }while (amount<0 );

        //get the memo
        System.out.println("Enter memo :");
        memo= sc.nextLine();

//        do the deposit

        theUser.addAccountTransaction(toAcc,amount,memo);
    }
}




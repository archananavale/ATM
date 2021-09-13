import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private String firstName;
    private String lastName;
    private String uuid; //unique id
    private  byte pinHash[];// Pin as hash //MD5 hashing algoritham
    /**
     * user linked to each account he has
     */
    private ArrayList<Account> accounts; //user can have account

    /**
     * constrcutor
     */

    public User(String firstName, String lastName, String pin , Bank theBank ){
        this.firstName=firstName;
        this.lastName=lastName;
        /**
         * stored the pic MD5 hash , rather than original value
         * for this using messageDigest Class
         */
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash= md.digest(pin.getBytes());
        }catch (NoSuchAlgorithmException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        /**
         * get Uuid : library in java to get uuid
         * at instance we will generate our own
         * get new unique universal id
         */
        this.uuid= theBank.getNewUserUuid();

        /**
         * emplty lsist of acc
         */
        this.accounts=new ArrayList<Account>();

        System.out.println("New USer "+this.firstName+" "+this.lastName+ "with ID :"+this.uuid +" created successfully! !");
    }

    /**
     * Add account for user
     * @param account
     */

    public void addAccount (Account account){
        this.accounts.add(account);
    }
    /**
     * getter method
     */
    public String getUUID(){
        return this.uuid;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void printAccountSummary(){
        System.out.println("\n\n account summory of : "+ this.firstName);
        for (int a=0;a<this.accounts.size();a++){
            System.out.println((a+1) + " :  " +this.accounts.get(a).getSummaryLine());
        }
    }
/**
 * transaction when amount transfer
 */
    public void addAccountTransaction(int accIndex,double amount , String memo){
        this.accounts.get(accIndex).addTransaction (amount,memo);
    }
    /**
     * get account uuid
     * @param accIndex
     * @return
     */
    public String getAccUUID(int accIndex){
        return this.accounts.get(accIndex).getUUID();
    }
    /**
     *
     * @param accIndex
     * @return
     */
    public double getAccountBalance(int accIndex){
        return this.accounts.get(accIndex).getBalance();
    }

    /**
     * print transaction fro acc at index
     * @param accIndex
     */
    public void  printAccountTransactionHistory(int accIndex){
        this.accounts.get(accIndex).printTransactionHistory();
    }
    /**
     * number of accc
     * @return
     */
    public  int numAccounts(){
        return accounts.size();
    }
    /**
     * Check wether given pin matched to user pin
     * @param pin
     * @return
     */

    public boolean validatePin(String pin){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()),this.pinHash);
        }catch (NoSuchAlgorithmException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        return  false;
    }

}

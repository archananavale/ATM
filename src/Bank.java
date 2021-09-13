import java.util.ArrayList;
import java.util.Random;

/**
 * Class name : Bank
 *
 */
public class Bank {
    private String name ;
    /**
     * Customer list
     */
    private ArrayList<User> users;
    /**
     * bank  have lists of account
     */
    private ArrayList<Account> accounts;


    public  Bank(String name){
        this.name= name;
        this.users= new ArrayList<User>();
        this.accounts= new ArrayList<Account>();
    }

    public String getName(){
        return this.name;
    }

    /**
     * get UUID for user
     * @return userUUID : random string which is not exist in both list of user and list of account
     */
    public String getNewUserUuid(){
        String uuid;
        Random randomNumberGenrator= new Random();
        int len=6;
        boolean nonUnique =false;
        // continue looping until u have uniq uuid
        do {
            //generate number
            uuid="";
            for (int i=0;i<len;i++){
                uuid+=((Integer)randomNumberGenrator.nextInt(10)).toString();
            }
            //check for unique key generated //ittrate user array
            for(User u : this.users){
                if((uuid.compareTo(u.getUUID())==0)){
                    nonUnique=true;
                    break;
                }
            }
        }while (nonUnique);

        return uuid;
    }

    /**
     * get UUUID for Accoung\t
     * @return Accound UUIS
     */
    public  String getNewAccountUuid(){
             String uuid;
            Random randomNumberGenrator= new Random();
            int len=6;
            boolean nonUnique =false;
            // continue looping until u have uniq uuid
            do {
                //generate number
                uuid="";
                for (int i=0;i<len;i++){
                    uuid+=((Integer)randomNumberGenrator.nextInt(10)).toString();
                }
                //check for unique key generated //ittrate user array
                for(Account a : this.accounts){
                    if((uuid.compareTo(a.getUUID())==0)){
                        nonUnique=true;
                        break;
                    }
                }
            }while (nonUnique);

            return uuid;
    }

    /**
     * Add account for Bank
     * @param account
     */
    public void addAccount(Account account){
        this.accounts.add(account);
    }

    /**
     * Create new User to  bank
     * @param firstName
     * @param lastName
     * @param pin
     * @return
     */

    public User addUser(String firstName, String lastName, String pin){
        User newUser = new User(firstName,lastName,pin,this);
        this.users.add(newUser);
        //creat Acc : saving
        Account newAccount = new Account("Saving ", newUser , this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);
        return newUser;
    }

    /**
     *
     * @param userID
     * @param pin
     * @return user if corect id and pin else null
     */

    public  User userLogIn(String userID, String pin ){
        //search throw user for correct id
        for (User u : this.users){
            //check user id
            if(u.getUUID().compareTo(userID)==0 && u.validatePin(pin)){
                return u;
            }
        }
        //if not found
        return  null;

    }


}

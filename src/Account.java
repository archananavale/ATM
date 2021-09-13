import java.util.ArrayList;

public class Account {
    private String name; // savoing ac, current ac
    private String uuid; //diffrent from user
    private User holder; //account holder
    private ArrayList<Transaction> transaction ; //no of transaction

    public Account(String name, User holder , Bank theBank){
        this.name=name;
        this.holder=holder;
        /**
         * get new uuid
         */
        this.uuid=theBank.getNewAccountUuid();
        /**
         *
         */
        this.transaction= new ArrayList<Transaction>();


    }
/**
 * get the summary of account transaction
 */
public  String getSummaryLine(){
    double balance = this.getBalance();
    //formate summary line : depending on balance is negative
    if(balance>=0){
        return String.format(this.uuid, " $ %0.02f ",balance," ",this.name);
    }else
    {

        return String.format(this.uuid, " $( %0.02f)",balance," ",this.name);
    }

}

    /**
     * when new transactions fone
     * @param amoiunt
     * @param memo
     */
    public void addTransaction(double amoiunt , String memo){
    Transaction newTransaction = new Transaction(amoiunt,memo,this);
    this.transaction.add(newTransaction);
}

    /**
     * print account transactionn
     */
    public void printTransactionHistory(){
    System.out.println("Transaction history for acc "+this.uuid);
    for (int i= this.transaction.size()-1;i>=0;i--){
        System.out.println(this.transaction.get(i).getSummaryLine());
    }
    System.out.println();
}

    /**
     * looping through all transaction and adding ammount
     * @return
     */
    public double getBalance(){
    double balance=0;
    for (Transaction t: this.transaction){
        balance += t.getAmount();
    }
    return  balance;
}
    /**
     *
     * @return
     */
    public  String getUUID(){
        return this.uuid;
    }

}

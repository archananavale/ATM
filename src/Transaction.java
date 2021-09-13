import java.util.ArrayList;
import java.util.Date;

public class Transaction {
    private double amount;
    private Date timeStamp;
    private String memo;// credit card stmt or draw money // optional
    private Account inAccount ; //which account transaction happend

    /**
     * create new Transaction
     * @param amount
     * @param inAccount
     */
    public Transaction(double amount,Account inAccount){
        this.amount=amount;
        this.inAccount=inAccount;
        this.timeStamp=new Date();
        this.memo="";
    }

    public Transaction(double amount, String memo, Account inAccount){
        this(amount,inAccount);
        this.memo=memo;
    }
/**
 * Print the summary of transaction
 */
    public String getSummaryLine(){
        if(this.amount>=0){
//            return String.format(".... %s :  %0.02f : %s",this.timeStamp.toString(),
//                    this.amount,this.memo);
            return String.format(this.timeStamp.toString()+"  : Amount :"+this.amount + "  :memo : "+this.memo);
        }else{ //for withrow
            return String.format("... %s :  (%0.02f) : %s",this.timeStamp.toString(),
                    this.amount,this.memo);
        }

    }
    /**
     * get amount for transaction
     * @return
     */
    public double getAmount(){
        return this.amount;
    }
}

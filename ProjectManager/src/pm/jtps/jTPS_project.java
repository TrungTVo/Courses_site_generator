package pm.jtps;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author McKillaGorilla
 */
public class jTPS_project {
    private List<jTPS_project_transaction> transactions;
    private int mostRecentTransaction;
    
    public jTPS_project() {
        transactions = new ArrayList<>();
        mostRecentTransaction = -1;
    }
    
    public List<jTPS_project_transaction> getTransactions(){
        return transactions;
    }
    
    public int getMostRecentTransaction(){
        return mostRecentTransaction;
    }
    
    public void setTransactions(List<jTPS_project_transaction> transactions){
        this.transactions = transactions;
    }
    
    public void setMostRecentTransaction(int mostRecentTransaction){
        this.mostRecentTransaction = mostRecentTransaction;
    }
    
    public void reset(){
        transactions.clear();
        mostRecentTransaction = -1;
    }
    
    public void addTransaction(jTPS_project_transaction transaction) {
        // IS THIS THE FIRST TRANSACTION?
        if (mostRecentTransaction < 0) {
            // DO WE HAVE TO CHOP THE LIST?
            if (transactions.size() > 0) {
                transactions = new ArrayList();
            }
            transactions.add(transaction);
        }
        // ARE WE ERASING ALL THE REDO TRANSACTIONS?
        else if (mostRecentTransaction < (transactions.size()-1)) {
            transactions.set(mostRecentTransaction+1, transaction);
            transactions = new ArrayList(transactions.subList(0, mostRecentTransaction+2));
        }
        // IS IT JUST A TRANSACTION TO APPEND TO THE END?
        else {
            transactions.add(transaction);
        }
        doTransaction();
    }
    
    public void doTransaction() {
        if (mostRecentTransaction < (transactions.size()-1)) {
            jTPS_project_transaction transaction = transactions.get(mostRecentTransaction+1);
            transaction.doTransaction();
            mostRecentTransaction++;
        }
    }
    
    public void undoTransaction() {
        if (mostRecentTransaction >= 0) {
            jTPS_project_transaction transaction = transactions.get(mostRecentTransaction);
            transaction.undoTransaction();
            mostRecentTransaction--;
        }
    }
    
    public String toString() {
        String text = "--Number of Transactions: " + transactions.size() + "\n";
        text += "--Current Index on Stack: " + mostRecentTransaction + "\n";
        text += "--Current Transaction Stack:\n";
        for (int i = 0; i <= mostRecentTransaction; i++) {
            jTPS_project_transaction jT = transactions.get(i);
            text += "----" + jT.toString() + "\n";
        }
        return text;
    }
}
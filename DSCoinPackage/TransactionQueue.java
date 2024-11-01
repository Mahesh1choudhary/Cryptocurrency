package DSCoinPackage;

public class TransactionQueue {

  public Transaction firstTransaction;
 
  public Transaction lastTransaction;
  public int numTransactions;

  public void AddTransactions (Transaction transaction) {
	  
	  if(firstTransaction==null && lastTransaction==null) {
		  firstTransaction=transaction;
		  firstTransaction.next=null;
		  lastTransaction=transaction;
		  lastTransaction.next=null;
		  
	  }
	  else if (firstTransaction==lastTransaction) {
		 
		  lastTransaction=transaction;
		  firstTransaction.next=lastTransaction;
		  lastTransaction.next=null;
		  
	  }
	  else {
		  lastTransaction.next=transaction;
		  lastTransaction=transaction;
	  }
	  numTransactions+=1;

  }
  
  public Transaction RemoveTransaction () throws EmptyQueueException {
	  if(firstTransaction==null && lastTransaction==null) {
		  throw new EmptyQueueException();
	  }
	  else if(firstTransaction==lastTransaction) {
		  Transaction tr=firstTransaction;
		  firstTransaction=null;
		  lastTransaction=null;
		  numTransactions-=1;
		  return tr;
	  }
	  else {
		  Transaction tr=firstTransaction;
		  firstTransaction=firstTransaction.next;
		  numTransactions-=1;
		  return tr;
	  }
	  
  }

  public int size() {
    return numTransactions;
  }
 
}

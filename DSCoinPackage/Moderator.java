package DSCoinPackage;

public class Moderator
 {
	
	

  public void initializeDSCoin(DSCoin_Honest DSObj, int coinCount) {
	  Members mem=new Members();
	  mem.UID="Moderator";
	  
	  int n= coinCount;
	  
	 
	  int m=100000;
	  int i=0; // index of memberlist;
	  int noftrans=0; // no of transactions happened;
	  Transaction[] tr= new Transaction[DSObj.bChain.tr_count];
	  while(n!=0) {
		  if(i>=DSObj.memberlist.length) {
			  i=0;
		  }
		  if(noftrans==DSObj.bChain.tr_count) {
			  TransactionBlock tB= new TransactionBlock(tr);
			  DSObj.bChain.InsertBlock_Honest(tB);
			  noftrans=0;
			  tr= new Transaction[DSObj.bChain.tr_count];
		  }
		  Transaction t= new Transaction();
		  t.coinID=String.valueOf(m);
		  t.Source=mem;
		  t.coinsrc_block=null;
		  t.Destination=DSObj.memberlist[i];
		  DSObj.pendingTransactions.AddTransactions(t);
		  m+=1;
		  i++;
		  n--;
		  tr[noftrans]=t;
		  noftrans++;
		  
		  
		  
	  }
	  DSObj.latestCoinID=String.valueOf(m);
	  
	  

  }
    
  public void initializeDSCoin(DSCoin_Malicious DSObj, int coinCount) {

  }
}

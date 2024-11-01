package DSCoinPackage;

import HelperClasses.MerkleTree;
import HelperClasses.CRF;

public class TransactionBlock {

  public Transaction[] trarray;
  public TransactionBlock previous;
  public MerkleTree Tree;
  public String trsummary;
  public String nonce;
  public String dgst;

  TransactionBlock(Transaction[] t) {
	  trarray=t;
	  previous=null;
	  trsummary=Tree.Build(trarray);
	  dgst=null;
    
  }

  public boolean checkTransaction (Transaction t) {
	  
	  BlockChain_Honest bk=new BlockChain_Honest();
	  TransactionBlock tk=bk.lastBlock;
	  if(t.coinsrc_block==null) {
		  return true;
	  }
	  while(tk!=t.coinsrc_block) {
		  for(int i=0;i<tk.trarray.length;i++) {
			  Transaction ch=tk.trarray[i];
			  if(ch.coinID==t.coinID && ch.Destination==t.Source) {
				  return false;
			  }}
		  tk=tk.previous;
		  
	  }
	 
	  for(int i=0;i<tk.trarray.length;i++) {
		  Transaction ch=tk.trarray[i];
		  if(ch.coinID==t.coinID && ch.Destination==t.Source) {
			  return true;
		  }}
	  return false;
  }
	  
	  
    
  
}

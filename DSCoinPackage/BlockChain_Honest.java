package DSCoinPackage;

import HelperClasses.CRF;

public class BlockChain_Honest {

  public int tr_count;
  public static final String start_string = "DSCoin";
  public TransactionBlock lastBlock;

  public void InsertBlock_Honest (TransactionBlock newBlock) {
	  
	  if(lastBlock==null) {
		  CRF obj=new CRF(64);
		  int n=1000000001;
		  newBlock.dgst=obj.Fn(start_string+"#"+newBlock.trsummary+"#"+String.valueOf(n));
		  
		  
		  while(!newBlock.dgst.substring(0, 4).equals("0000")) {
			  n++;
			  newBlock.dgst=obj.Fn(start_string+"#"+newBlock.trsummary+"#"+String.valueOf(n));
			  
		  }
		  newBlock.nonce=String.valueOf(n);
		
		  newBlock.previous=lastBlock;
		  lastBlock=newBlock;
		  
		  
	  }
	  
	  else {
		  CRF obj=new CRF(64);
		  int n=1000000001;
		  newBlock.dgst=obj.Fn(lastBlock.dgst+"#"+newBlock.trsummary+"#"+String.valueOf(n));
		  
		  while(!newBlock.dgst.substring(0, 4).equals("0000")) {
			  n++;
			  newBlock.dgst=obj.Fn(lastBlock.dgst+"#"+newBlock.trsummary+"#"+String.valueOf(n));
			  
		  }
		  newBlock.nonce=String.valueOf(n);
		  
		  newBlock.previous=lastBlock;
		  lastBlock=newBlock;
	  }
	  
	   
	  

  }
}

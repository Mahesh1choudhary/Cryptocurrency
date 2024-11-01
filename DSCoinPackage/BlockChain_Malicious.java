package DSCoinPackage;

import HelperClasses.CRF;

public class BlockChain_Malicious {

  public int tr_count;
  public static final String start_string = "DSCoin";
  public TransactionBlock[] lastBlocksList;
  
  int ind; // index of TransactionBlock lastb in the lastBlockList;

  public static boolean checkTransactionBlock (TransactionBlock tB) {
    boolean ans=false;
    if(tB.previous==null) {
    	CRF obj= new CRF(64);
    	if(tB.dgst.substring(0,4)=="0000" && tB.dgst==obj.Fn(start_string+"#"+tB.trsummary+"#"+tB.nonce)) {
    		String sum=tB.Tree.Build(tB.trarray); 
    		if(tB.trsummary==sum) {
    			boolean b=true; // denotes each transaction t is valid;
    			int q=0;
    			outl:for(q=0;q<tB.trarray.length;q++) {
    				TransactionBlock tk= tB;
    				while(tk!=tB.trarray[q].coinsrc_block) {
    					for(int i=0;i<tk.trarray.length;i++) {
    						Transaction ch=tk.trarray[i];
    						if(ch.coinID==tB.trarray[i].coinID && ch.Destination==tB.trarray[i].Source) {
    							b=false;
    							break outl;
    						}
    					}
    					tk=tk.previous;
    				}
    				for(int i=0;i<tk.trarray.length;i++) {
    					Transaction ch=tk.trarray[i];
    					if(ch.coinID==tB.trarray[i].coinID && ch.Destination==tB.trarray[i].Source) {
    						b=true;
    					}
    					else {
    						b=false;
    					}
    						
    				}
    				
    				
    			}
    			if(b==true) {
    				ans=true;
    			}
    			  
    			 
    		}
    	}
    }
    
    return ans;
  }

  public TransactionBlock FindLongestValidChain () {
	  int lengthc=0;
	  TransactionBlock lastb=null; // lastblock of the longest chain
	  TransactionBlock lastbofcurrchain=null; // last block of the current chain that we are looking into if it is longest or not 
	  TransactionBlock curr=null; // current block of the chain that we are looking into if it is the longest or not
	  
	 
	  for(int i=0;i<lastBlocksList.length;i++) {
		  int len=0; // length of current chain
		  ind=i;
		  
		  lastbofcurrchain=lastBlocksList[i];
		  curr=lastBlocksList[i];
		  while(curr.previous!=null) {
			  if(checkTransactionBlock(curr)) {
				  len++;
				  curr=curr.previous;
			  }
			  else {
				  if(len>lengthc) {
					  lengthc=len;
					  lastb=lastbofcurrchain;
				  }
				  len=0;
				  
				  curr=curr.previous;
				  lastbofcurrchain=curr;
			  }
		  }  
	  }
	  
	  
    return lastb;
  }
  
  // later, also try to check if newBlock is a valid block or its transactions are valid;
  public void InsertBlock_Malicious (TransactionBlock newBlock) {
	  TransactionBlock lastb=FindLongestValidChain ();
	  
	  if(lastb==null) {
		  CRF obj=new CRF(64);
		  int n=1000000001;
		  newBlock.dgst=obj.Fn(start_string+"#"+newBlock.trsummary+"#"+String.valueOf(n));
		  
		  
		  while(!newBlock.dgst.substring(0, 4).equals("0000")) {
			  n++;
			  newBlock.dgst=obj.Fn(start_string+"#"+newBlock.trsummary+"#"+String.valueOf(n));
			  
		  }
		  newBlock.nonce=String.valueOf(n);
		
		  newBlock.previous=lastb;
		  lastBlocksList[ind]=newBlock;
		  
		  
	  }
	  
	  else {
		  CRF obj=new CRF(64);
		  int n=1000000001;
		  newBlock.dgst=obj.Fn(lastb.dgst+"#"+newBlock.trsummary+"#"+String.valueOf(n));
		  
		  while(!newBlock.dgst.substring(0, 4).equals("0000")) {
			  n++;
			  newBlock.dgst=obj.Fn(lastb.dgst+"#"+newBlock.trsummary+"#"+String.valueOf(n));
			  
		  }
		  newBlock.nonce=String.valueOf(n);
		  
		  newBlock.previous=lastb;
		  lastBlocksList[ind]=newBlock;
	  }
	  
	  
  }
}

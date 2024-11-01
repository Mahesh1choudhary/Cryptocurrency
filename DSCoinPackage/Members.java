package DSCoinPackage;
import java.util.*;

import HelperClasses.CRF;
import HelperClasses.Pair;
import HelperClasses.*;

public class Members
 {

  public String UID;
  public List<Pair<String, TransactionBlock>> mycoins;
  public Transaction[] in_process_trans;

  public void initiateCoinsend(String destUID, DSCoin_Honest DSobj) {
	  
	  Pair<String, TransactionBlock> pr=mycoins.remove(0);
	  Transaction tobj=new Transaction();
	  tobj.coinID=pr.first;
	  tobj.coinsrc_block=pr.second;
	  int b=0;
	  while(DSobj.memberlist[b].UID!=UID) {
		  b+=1;
	  }
	 
	  tobj.Source= DSobj.memberlist[b];
	  
	  int a=0;
	  while(DSobj.memberlist[a].UID!=destUID) {
		  a+=1;
	  }
	  tobj.Destination=DSobj.memberlist[a];
	  
	  
	  Transaction[] ar=new Transaction[in_process_trans.length+1];
	  for(int i=0;i<in_process_trans.length;i++) {
		  ar[i]=in_process_trans[i];
	  }
	  ar[in_process_trans.length]=tobj;
	  in_process_trans=ar;
	  
	  
	  DSobj.pendingTransactions.AddTransactions(tobj);
	  

  }


  public Pair<List<Pair<String, String>>, List<Pair<String, String>>> finalizeCoinsend (Transaction tobj, DSCoin_Honest DSObj) throws MissingTransactionException {
	 
	  TransactionBlock tB=DSObj.bChain.lastBlock;
	  boolean br=false;
	  
	  for(int l=0;l<tB.trarray.length;l++) {
			 if(tB.trarray[l]==tobj) {
				 br=true;
				 break;}
	  }
	  int nofblocks=1;
	  while(!br && tB!=null) {
		  tB=tB.previous;
		  nofblocks+=1; // includes tB also
		  for(int j=0;j<tB.trarray.length;j++) {
			 if(tB.trarray[j]==tobj) {
				 br=true;
				 break;}
			 
		  
		  }  
	  }
	  
	  if(br==false) {
		  throw new MissingTransactionException();
	  }
	  else {
		  List<Pair<String, String>> slist=new ArrayList<Pair<String, String>>();
		  List<Pair<String, String>> scptr=tB.Tree.siblingpath(tobj, tB.trarray);
		  TransactionBlock tbl=DSObj.bChain.lastBlock;
		  
		  
		   
		  for(int m=nofblocks;m>0;m--) {
			  if(m==1 && tbl.previous==null) {
				  Pair<String, String> ps= new Pair<String,String>(tbl.dgst,"DSCoin"+"#"+tbl.trsummary+"#"+tbl.nonce);
				  slist.add(m,ps);
				  tbl=tbl.previous;
			  }
			  else {
				  Pair<String, String> ps= new Pair<String,String>(tbl.dgst,tbl.previous.dgst+"#"+tbl.trsummary+"#"+tbl.nonce);
				  slist.add(m,ps);
				  tbl=tbl.previous;
			  }
			  
		  }
		  if(tbl==null) {
			  Pair<String, String> ps= new Pair<String,String>("DSCoin",null);
			  slist.add(0, ps);
		  }
		  else {
			  Pair<String, String> ps= new Pair<String,String>(tbl.dgst,null);
			  slist.add(0, ps);
		  }
		  
		  
		  
		  Transaction[] ar=new Transaction[in_process_trans.length-1];
		  for(int i=0;i<in_process_trans.length-1;i++) {
			  ar[i]=in_process_trans[i];
		  }
		  
		  in_process_trans=ar;
		  int a=0;
		  while(DSObj.memberlist[a].UID!=tobj.Destination.UID) {
			  a+=1;
		  }
		  Pair<String, TransactionBlock> pt=new Pair<String, TransactionBlock>(tobj.coinID,tbl);
		  
		  DSObj.memberlist[a].mycoins.add(pt);
		  Pair<List<Pair<String, String>>, List<Pair<String, String>>> result=new Pair<List<Pair<String, String>>, List<Pair<String, String>>>(scptr,slist);
		  return result;
		  
		  
		  
	  }
		   
  
  }

  public void MineCoin(DSCoin_Honest DSObj) {
	  
	  
	  int m=0;
	  Transaction[] t=new Transaction[DSObj.bChain.tr_count];
	  while(m!=DSObj.bChain.tr_count-1) {
		  try {
			  
			  // checkif each transaction is valid or not
			Transaction tn=DSObj.pendingTransactions.RemoveTransaction();
			boolean bl=false;
			for(int i=0;i<t.length;i++) {
				if(tn.coinID==t[i].coinID) {
					bl=true;
				}
			}
			if(bl==false) {
				t[m]=tn;
				m+=1;
			}
			
			
		} catch (EmptyQueueException e) {
			e.printStackTrace();
		}  
	  }
	  Transaction minerRewardTransaction =new Transaction();
	  minerRewardTransaction.coinID=DSObj.latestCoinID;
	  minerRewardTransaction.Source=null;
	  minerRewardTransaction.coinsrc_block=null;
	  int b=0;
	  while(DSObj.memberlist[b].UID!=UID) {
		  b+=1;
	  }
	 
	  minerRewardTransaction.Destination= DSObj.memberlist[b];
	  
	  TransactionBlock tB=new TransactionBlock(t);
	  DSObj.bChain.InsertBlock_Honest(tB);
	  
	  Pair<String, TransactionBlock> pt=new Pair<String, TransactionBlock>(minerRewardTransaction.coinID,minerRewardTransaction.coinsrc_block);
	  DSObj.memberlist[b].mycoins.add(pt);
	  
	  

  }  

  public void MineCoin(DSCoin_Malicious DSObj) {

  }  
}

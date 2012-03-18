package datarecorders.btgRecorder;

public class IntQueue
 {
  private IntQueueNode pFirst;
  private IntQueueNode pLast;
	
  public IntQueue()
   {pFirst=null; pLast=null;}
  
  public boolean isEmpty()
   {
	if(pFirst==null)
	 return true;
	else
	 return false;	
   }//Fine isEmpty
  
   
   
  public void put(int val)
   {
	IntQueueNode newNode=new IntQueueNode(val);   
    
	if(pFirst==null)
	 {	
	  pFirst=newNode;
	  pLast=newNode;
	 }
    else
     {
	  pLast.pSucc=newNode;
	  pLast=newNode;
     }   
    }//Fine put 
   
  public int get()
   {
	if(pFirst==null)
	 return -1;
	else
	 {
	  IntQueueNode node=pFirst;
	  pFirst=node.pSucc;
	  return node.val;
	 }	
	}//Fine  get
   
 }//Fine intQueue



class IntQueueNode
 {
  public int val;
  public IntQueueNode pSucc;
  
  public IntQueueNode(int val)
   {this.val=val; pSucc=null;}
 }//Fine classe BFSNode

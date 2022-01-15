/*

Famous Producer and Consumer Example


*/


class CommonObj{

    private int data;
    private boolean access;
    synchronized public void setData(int data){
        try{
        //If access is true,then wait till consumer consumes the data
        if(access)wait();
        
        //If access is false,then produce the data,Sleep for some time and notify other threads waiting on it
        if(!access){
            this.data=data;
            access=true;
            System.out.println("Producer producing "+data);
            Thread.sleep(3000);
            notify();
        } 
    }catch(Exception e){}
    }

    synchronized  public void getData(){
       try{
        
        //If access is false,then wait till producer produces the data
        if(!access){    
            wait();
        }    
        
        //If access is true,then consumer the data ,sleep for arbitary value and notify other threads
        if(access){
            System.out.println("Consumer consuimg "+this.data);
            access=false;
            Thread.sleep(3000);
            notify();
        } 
    }catch(Exception e){}  
    }

}

//Consumer 
class Consumer implements Runnable{
    private CommonObj commonObj;
    
    Consumer(CommonObj commonObj){
       this.commonObj = commonObj;
    }   
    public void run(){

        for(int i=0;i<10;i++){
            commonObj.getData();
        }


    }

}

//Producer 
class Producer implements Runnable{
    private CommonObj commonObj;
    
    Producer(CommonObj commonObj){
       this.commonObj = commonObj;
    }   
    public void run(){
        for(int i=0;i<10;i++){
            commonObj.setData(i);
        }     
    }
}

class psp{
    public static void main(String a[]){
     
        CommonObj commonObj=new CommonObj();
     
        Producer p=new Producer(commonObj);
        Consumer c=new Consumer(commonObj);

        Thread t1=new Thread(p);
        Thread t2=new Thread(c);
        t2.start();
       
        t1.start();
        

    }
}

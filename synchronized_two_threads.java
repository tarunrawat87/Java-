/**
 * Example demonstating use of synchronized word
 * If a thread is executing a synchonized method
 * it would aquire lock on that object and would release it after it is done executing it
 * and other threads would have to wait for it release lock to execute it
 */


class CommonObj{

    synchronized public void method1(){
        try{
           
            Thread.sleep(1000);
            System.out.println("method 1 executed");
        }catch(Exception e){

        }
    }
    synchronized public void method2(){
        System.out.println("method 2 executed");
    }
}

class MyThread extends Thread{
    private CommonObj commonObj;
    private String methodName;
    MyThread(CommonObj commonObj,String methodName){
        this.methodName=methodName;
         this.commonObj=commonObj;
    }
    public void run(){
        if(this.methodName=="method1"){
            commonObj.method1();
        }else{
            commonObj.method2();
        }
    }
}

class psp{


public static void main(String a[]){
    CommonObj c=new CommonObj();
    MyThread thread1=new MyThread(c,"method1");
    MyThread thread2=new MyThread(c,"method2");
    thread1.start();
    //Thread 2 would be blocked till thread 1 complete the work 
    //PS This would be case if thread ran first
    thread2.start();
    

}
}

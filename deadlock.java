/**
 * Example of deadlock
 * 
 */
interface SomeInterface {
    public void run();

    public void setCommonObject(SomeInterface someInterface);
}

class CommonObj1 implements SomeInterface {
    private SomeInterface commonObj2;

    public synchronized void run() {
        // System.out.println("Deadlocking..");
        System.out.println(Thread.currentThread().getName());
        this.commonObj2.run();
        System.out.println("Deadlocked..");
    }

    public void setCommonObject(SomeInterface commonObj1) {
        this.commonObj2 = commonObj1;
    }
}

/**
 * created a commonObj2 class which hold reference of commonObj1 Each other
 * objects calls each others methods Two threads own these objects and aquire
 * locks of these objects Each thread waits for another thread to leave lock and
 * no one leaves it :// hence deadlocks occurs
 * 
 */
class CommonObj2 implements SomeInterface {
    private SomeInterface commonObj1;

    public void setCommonObject(SomeInterface commonObj1) {
        this.commonObj1 = commonObj1;
    }

    public synchronized void run() {
        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " Deadlock..");
            this.commonObj1.run();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}

class MyThread extends Thread {
    private SomeInterface someInterface;

    MyThread(SomeInterface someInterface) {
        this.someInterface = someInterface;
    }

    public void run() {
        someInterface.run();
    }
}

class psp {

    public static void main(String a[]) {
        CommonObj1 c1 = new CommonObj1();
        CommonObj2 c2 = new CommonObj2();
        /**
         * Both objects keeping refernce of each other
         */
        c1.setCommonObject(c2);
        c2.setCommonObject(c1);

        MyThread thread1 = new MyThread(c1);
        MyThread thread2 = new MyThread(c2);
        thread1.setName("Thread 1");
        thread2.setName("Thread 2");
        /**
         * Started the thread
         */
        thread1.start();
        thread2.start();

    }
}

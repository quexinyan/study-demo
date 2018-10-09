package demo.thread.create;

/**
 * Obj.wait()，与Obj.notify()必须要与synchronized(Obj)一起使用，也就是wait,与notify是针对已经获取了Obj锁进行操作，从语法角度来说就是Obj.wait(),Obj.notify必须在synchronized(Obj){...}语句块内。
 * 从功能上来说wait就是说线程在获取对象锁后，主动释放对象锁，同时本线程休眠。直到有其它线程调用对象的notify()唤醒该线程，才能继续获取对象锁，并继续执行。相应的notify()就是对对象锁的唤醒操作。
 * 但有一点需要注意的是notify()调用后，并不是马上就释放对象锁的，而是在相应的synchronized(){}语句块执行结束，自动释放锁后，JVM会在wait()对象锁的线程中随机选取一线程，赋予其对象锁，唤醒线程，继续执行。
 * 这样就提供了在线程间同步、唤醒的操作。Thread.sleep()与Object.wait()二者都可以暂停当前线程，释放CPU控制权，主要的区别在于Object.wait()在释放CPU同时，释放了对象锁的控制。
 */
public class WaitAndNotify implements Runnable{

    private String name;
    private Object prev;
    private Object self;

    public WaitAndNotify(String name, Object prev, Object self) {
        this.name = name;
        this.prev = prev;
        this.self = self;
    }

    /**
     * A线程先启动，repv是c对象，self是a对象，此时释放a对象等待队列中的线程，而A线程进入c对象的等待队列，此时A线程阻塞（需要其他线程调用c对象的notify方法，才能唤醒）
     * B线程启动，prev是a对象，self是b对象，此时释放b对象等待队列中的线程，而B线程进入a对象的等待队列，此时B线程阻塞
     * 其他两个线程都阻塞了，此时C线程启动，prev是b对象，self是c对象，此时释放c对象等待队列中的线程（也就是唤醒了A线程），而C线程进入b对象的等待队列，此时C线程阻塞
     * 其实A线程被唤醒，B、C线程阻塞在等待队列中。所以继续上述业务的循环。
     */
    @Override
    public void run() {
        int count = 10;
        while(count > 0){
            synchronized (prev){
                synchronized (self){
                    System.out.print(name);
                    count--;

                    self.notify();
                }

                try {
                    prev.wait();    // 释放当前线程的锁标记，并且当前线程进入prev对象的等待队列中，需要其他线程调用prev对象的notify方法才能唤醒该线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 输出结果：ABCABCABCABCABCABCABCABCABCABC
     * 先来解释一下其整体思路，从大的方向上来讲，该问题为三线程间的同步唤醒操作，主要的目的就是ThreadA->ThreadB->ThreadC->ThreadA循环执行三个线程。
     * 为了控制线程执行的顺序，那么就必须要确定唤醒、等待的顺序，所以每一个线程必须同时持有两个对象锁，才能继续执行。一个对象锁是prev，就是前一个线程所持有的对象锁。
     * 还有一个就是自身对象锁。主要的思想就是，为了控制执行的顺序，必须要先持有prev锁，也就前一个线程要释放自身对象锁，再去申请自身对象锁，两者兼备时打印，
     * 之后首先调用self.notify()释放自身对象锁，唤醒下一个等待线程，再调用prev.wait()释放prev对象锁，终止当前线程，等待循环结束后再次被唤醒。运行上述代码，可以发现三个线程循环打印ABC，共10次。
     * 程序运行的主要过程就是A线程最先运行，持有C,A对象锁，后释放A,C锁，唤醒B。线程B等待A锁，再申请B锁，后打印B，再释放B，A锁，唤醒C，线程C等待B锁，再申请C锁，后打印C，再释放C,B锁，唤醒A。
     * 看起来似乎没什么问题，但如果你仔细想一下，就会发现有问题，就是初始条件，三个线程按照A,B,C的顺序来启动，按照前面的思考，A唤醒B，B唤醒C，C再唤醒A。但是这种假设依赖于JVM中线程调度、执行的顺序
     */
    public static void main(String[] args) throws InterruptedException {

        Object a = new Object();
        Object b = new Object();
        Object c = new Object();

        WaitAndNotify a1 = new WaitAndNotify("A", c, a);
        WaitAndNotify b1 = new WaitAndNotify("B", a, b);
        WaitAndNotify c1 = new WaitAndNotify("C", b, c);

        new Thread(a1).start();
        Thread.sleep(10);   // 确保按顺序A,B,C执行
        new Thread(b1).start();
        Thread.sleep(10);
        new Thread(c1).start();
        Thread.sleep(10);
    }
}

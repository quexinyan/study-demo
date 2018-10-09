package demo.thread.create;

/**
 * 为什么要使用join(当前线程中，其他线程调用了join方法，则当前线程阻塞，其他线程运行完之后继续运行当前线程)
 * 在很多情况下，主线程生成并起动了子线程，如果子线程里要进行大量的耗时的运算，主线程往往将于子线程之前结束，
 * 但是如果主线程处理完其他的事务后，需要用到子线程的处理结果，也就是主线程需要等待子线程执行完成之后再结束，这个时候就要用到join()方法了。
 */
public class ThreadApi_join {

    /**
     * 没用join
     * 发现主线程比子线程早结束
     */
    public static void main1(String[] args){

        System.out.println(Thread.currentThread().getName()+"主线程运行开始!");

        MyThreadJoin myThreadJoinA = new MyThreadJoin("A");
        MyThreadJoin myThreadJoinB = new MyThreadJoin("B");
        myThreadJoinA.start();
        myThreadJoinB.start();

        System.out.println(Thread.currentThread().getName()+"主线程运行结束!");
    }

    /**
     * 用join
     * 主线程一定会等子线程都结束了才结束
     */
    public static void main(String[] args){

        System.out.println(Thread.currentThread().getName()+"主线程运行开始!");

        MyThreadJoin myThreadJoinA = new MyThreadJoin("A");
        MyThreadJoin myThreadJoinB = new MyThreadJoin("B");
        myThreadJoinA.start();
        myThreadJoinB.start();

        try {
            myThreadJoinA.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            myThreadJoinB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName()+"主线程运行结束!");
    }
}

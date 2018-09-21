package demo.thread.create;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 3种创建线程的方式
 */
public class createDemo {

    class MyThread extends Thread{
        @Override
        public void run() {
            System.out.println("集成Thread创建线程");
        }
    }

    @Test
    public void extendsThread(){
        Thread myThread = new MyThread();
        myThread.start();
    }

    @Test
    public void implementsRunnable(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("实现Runnable接口创建线程");
            }
        }).start();
    }

    class MyCallable implements Callable<Integer>{

        @Override
        public Integer call() {
            System.out.println("实现Callable接口通过FutureTask包装器来创建Thread线程");
            return 1;
        }
    }

    @Test
    public void callableAndFutureTask() throws ExecutionException, InterruptedException {

        // 创建MyCallable对象
        Callable<Integer> myCallable = new MyCallable();
        // 使用FutureTask来包装MyCallable对象
        FutureTask<Integer> integerFutureTask = new FutureTask<>(myCallable);
        // FutureTask对象作为Thread对象的target创建新的线程
        new Thread(integerFutureTask).start();
        // 取得新创建的新线程中的call()方法返回的结果
        System.out.println("call()方法返回的结果:"+integerFutureTask.get());
    }
}

package demo.thread.create;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * 线程池
 * 
 * @author Administrator
 *
 */
public class ThreadPool {

	public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

	@Test
	public void test01() throws InterruptedException {

		final long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			final int num = i;
			fixedThreadPool.execute(new Runnable() {

				public void run() {
					System.out.println(num);
				}
			});
		}
		// 当调用ExecutorService.shutdown方法的时候，线程池不再接收任何新任务，但此时线程池并不会立刻退出，直到添加到线程池中的任务都已经处理完成，才会退出
		fixedThreadPool.shutdown();
		// 当前运行的线程数
		System.out.println(Thread.currentThread().getThreadGroup().activeCount() + "=========================");
		// awaitTermination()是一个阻塞方法。它必须等线程池退出后才会结束自身。可以设置阻塞时间。
		fixedThreadPool.awaitTermination(1, TimeUnit.HOURS);

		final long end = System.currentTimeMillis();
		System.out.println("用时：" + (end - start));
	}
}

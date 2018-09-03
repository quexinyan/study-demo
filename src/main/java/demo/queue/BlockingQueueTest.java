package demo.queue;/*
 * Time : 2018/7/9 11:04
 * Author : gaox
 * Description :
 */

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @Time           2018/7/9 11:04
 * @Author          gaox
 * @Description     展示了如何使用阻塞队列来控制线程集
 * 程序在一个目录及它的所有子目录下搜索所有文件，打印出包含指定关键字的文件列表。
 * 从下面实例可以看出，使用阻塞队列两个显著的好处就是：
 * 多线程操作共同的队列时不需要额外的同步，另外就是队列会自动平衡负载，即那边（生产与消费两边）处理快了就会被阻塞掉，从而减少两边的处理速度差距。
*/
public class BlockingQueueTest {

    public static void main(String[] args){

        Scanner in = new Scanner(System.in);
        System.out.print("Enter base directory (e.g. /usr/local/jdk5.0/src): ");
        String directory = in.nextLine();
        System.out.print("Enter keyword (e.g. volatile): ");
        String keyword = in.nextLine();

        // 阻塞队列大小
        final int FILE_QUEUE_SIZE = 10;
        // 关键字搜索线程个数
        final int SEARCH_THREADS = 100;

        // 基于ArrayBlockingQueue的阻塞队列
        BlockingQueue<File> queue = new ArrayBlockingQueue<File>(FILE_QUEUE_SIZE);

        // 只启动一个线程来搜索目录
        new Thread(new FileEnumerationTask(queue, new File(directory))).start();

        // 启动100个线程用来在文件中搜索指定的关键字
        for(int i=0; i<SEARCH_THREADS; i++){
            new Thread(new SearchTask(queue, keyword)).start();
        }
    }
}

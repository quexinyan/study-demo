package demo.queue;/*
 * Time : 2018/7/9 11:28
 * Author : gaox
 * Description :
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

/**
 * @Time           2018/7/9 15:03
 * @Author          gaox
 * @Description     搜索所有文件关键字
*/
public class SearchTask implements Runnable{

    private BlockingQueue<File> queue;
    private String keyword;

    public SearchTask(BlockingQueue<File> queue, String keyword) {
        this.queue = queue;
        this.keyword = keyword;
    }

    public void search(File file) throws FileNotFoundException {

        Scanner in = new Scanner(new FileInputStream(file));
        int lineNumber = 0;
        while (in.hasNextLine()){
            lineNumber ++;
            String line = in.nextLine();
            if(line.contains(keyword)){
                System.out.printf("%s:%d:%s%n", file.getPath(), lineNumber, line);
            }
        }
        in.close();
    }

    @Override
    public void run() {

        try {
            boolean done = false;
            while (!done){
                // 取出队首元素，如果队列为空，则阻塞
                File file = queue.take();
                if(file == FileEnumerationTask.DUMMY){
                    queue.put(file);
                    done = true;
                }else{
                    search(file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

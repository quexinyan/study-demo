package demo.queue;/*
 * Time : 2018/7/6 16:17
 * Author : gaox
 * Description :
 */

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Time           2018/7/6 16:17
 * @Author          gaox
 * @Description     队列是一种特殊的线性表，它只允许在表的前端进行删除操作，而在表的后端进行插入操作.
 *
 * add        增加一个元索                     如果队列已满，则抛出一个IIIegaISlabEepeplian异常
　　remove   移除并返回队列头部的元素    如果队列为空，则抛出一个NoSuchElementException异常
　　element  返回队列头部的元素             如果队列为空，则抛出一个NoSuchElementException异常
　　offer       添加一个元素并返回true       如果队列已满，则返回false
　　poll         移除并返问队列头部的元素    如果队列为空，则返回null
　　peek       返回队列头部的元素             如果队列为空，则返回null
　　put         添加一个元素                      如果队列满，则阻塞
　　take        移除并返回队列头部的元素     如果队列为空，则阻塞
 *
*/
public class DemoTest {

    /**
    * LinkedList类实现了Queue接口，因此我们可以把LinkedList当成Queue来用。
    * @author      gaox
    * @date        2018/7/6 16:18
    */
    @Test
    public void linkedList(){

        // add()和remove()方法在失败的时候会抛出异常(不推荐)
        Queue<String> queue = new LinkedList<String>();

        // 添加元素
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        queue.offer("d");
        queue.offer("e");

        for(String q : queue){
            System.out.print(q+" ");
        }

        System.out.println("");

        // 返回第一个元素，并在队列中删除
        System.out.println("poll="+queue.poll());
        for(String q : queue){
            System.out.print(q+" ");
        }

        System.out.println("");

        // 返回第一个元素
        System.out.println("peek="+queue.peek());
        for(String q : queue){
            System.out.print(q+" ");
        }
    }

    /**
    * 阻塞队列
    * @author      gaox
    * @date        2018/7/6 16:33
    */
    @Test
    public void blockQueue(){

    }
}

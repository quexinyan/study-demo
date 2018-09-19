package demo.activemq.spring.consumer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 自定义mq监听
 */
public class MyMessageListener implements MessageListener{

    public void onMessage(Message message) {

        System.out.println("监听==================监听");
        try {
            System.out.println(message);
            TextMessage tm = (TextMessage)(message);
            System.out.println("监听到的消息："+tm.getText());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}

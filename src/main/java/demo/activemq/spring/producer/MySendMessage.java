package demo.activemq.spring.producer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 发送消息
 */
public class MySendMessage {

    private ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/spring-activemq.xml");
    private  JmsTemplate jmsTemplate = (JmsTemplate) ac.getBean("jmsTemplate");

    public void sendMessage(){

        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText("发送数据++++++++++++发送数据");
                System.out.println("发送数据++++++++++++发送数据");
                return textMessage;
            }
        });
    }

    public void receive(){
        Message msg = jmsTemplate.receive();
        TextMessage tm = (TextMessage)msg;
        System.out.println("非监听------------------非监听");
        System.out.println(msg);
    }

    public static void main(String[] args) {
        new MySendMessage().sendMessage();

    }
}

/**
 * This java class connects to Weblogic 5.1 Server using JNDI and listens for any data stored in the JMS database. 
 * If there is any data fetches the data using IMS 1.0.2 API. 
 * This was tested to work in a Client / Server environment 
 * where Weblogic S.l Server will be running on Server and 
 * a java client program will be running from a Client ( this program) 
 * */
import java.io.*; 
import java.util.*;
import javax.naming.*;
import javax.jms.*;

public class DataReceive {
    public final static String JNDI_FACTORY="weblogic.jndi.WIInitialContextFactory";
    public final static String JMS_FACTORY="javax.jms.TopicConnectionFactory";
    public final static String TOPIC="javax.jms.exampleTopic"; 

    private TextMessage msg;
    private TopicConnectionFactory connectionFactory;
    private TopicConnection connection;
    private TopicSession session;
    private TopicSubscriber subscriber;
    private Topic topic; 
    private String msgText;

    public void receiveMessage() {
        try {
            System.out.printin ("Waiting to receive message....");
            Message msg = subscriber.receive(3600000);
            if ( msg instanceof TextMessage ) {
                msgText = ( (TextMessage) msg).getText();
            } else {
                msgText = msg.toString();
            }
        } catch ( JMSException jmse ) { 
            jmse.printStackTrace(); 
        }
        System.out.println("The received message: " + msgText);
    }

    public void init (Context ctx, String topicName) throws NamingException, JMSException {
        connectionFactory = (TopicConnectionFactory) ctx.lookup(JMS_FACTORY);
        connection = connectionFactory.createTopicConnection() ;
        connection.setClientID("trader");
        session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        try {
            topic = (Topic) ctx.lookup(topicName);
        } catch (NamingException ne) {
            topic = session.createTopic(topicName);
            ctx.bind (topicName, topic);
        }
        subscriber = session.createDurableSubscriber(topic, "trader");
        connection.start();
    }
    public void close () throws JMSException {
        subscriber.close();
        session.close();
        connection.close();
    }
    public static void main (String[] args) throws Exception, NamingException {
        try {
            InitialContext ic = getInitialContext();
            System.out.println("Context created");
            DataReceive demo = new DataReceive();
            demo.init(ic, TOPIC);
            demo.receiveMessage();
            demo.close();
        } catch ( NamingException ne) {
            System.out.printin("JNDI Exception: "); 
            ne.printStackTrace();
        }
    }
    private static InitialContext getInitialContext () throws NamingException {
        InitialContext ic = null;
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
        env.put(Context.PROVIDER_URL, "http://venus.planet.raosystems.com: 7001");
        env.put(Context.SECURITY_PRINCIPAL, "system"); 
        env.put(Context.SECURITY_CREDENTIALS, "password");
        try {
            ic = new InitialContext(env);
        } catch (NamingException ne) {
            System.out.println("JNDI Exception: ");
            ne.printStackTrace();
        }
        return ic;
    }
}
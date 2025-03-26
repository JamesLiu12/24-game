package service.jms;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public abstract class BaseJMS {
    protected String host;
    protected Context jndiContext;
    protected Connection connection;
    protected Session session;
    protected ConnectionFactory connectionFactory;
    protected Destination destinationResource;

    public BaseJMS(String host, String connectionFactoryName, String destinationResourceName) throws NamingException {
        this.host = host;
        createJNDIContext();
        connectionFactory = (ConnectionFactory) jndiContext.lookup(connectionFactoryName);
        destinationResource = (Destination) jndiContext.lookup(destinationResourceName);
    }

    private void createJNDIContext() throws NamingException {
        System.setProperty("org.omg.CORBA.ORBInitialHost", host);
        System.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
        jndiContext = new InitialContext();
    }

    protected abstract void setup() throws NamingException, JMSException;

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}

package jmeterxmpp.plugin;

import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testelement.TestListener;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;

public class XmppController extends GenericController implements TestBean, TestListener {

    private String xmppServerAddress;

    private String destinationUser;

    public String getDestinationUser() {
        return destinationUser;
    }

    public void setDestinationUser(String destinationUser) {
        this.destinationUser = destinationUser;
    }

    public String getXmppServerAddress() {
        return xmppServerAddress;
    }

    public void setXmppServerAddress(String xmppServerAddress) {
        this.xmppServerAddress = xmppServerAddress;
    }

    public void testStarted() {
        System.out.println("Test started!!");
        XMPPConnection connection = new XMPPConnection(xmppServerAddress);
        try {
            connection.connect();
//            SASLAuthentication.supportSASLMechanism("PLAIN", 0);
//            connection.getAccountManager().createAccount("jmeter", "jmeter");
            connection.login("jmeter@jalewis.thoughtworks.com", "jmeter");
            ChatManager chatmanager = connection.getChatManager();
            Chat newChat = chatmanager.createChat(destinationUser, new MessageListener() {
                public void processMessage(Chat chat, Message message) {
                    System.out.println("Received message: " + message);
                }
            });
            newChat.sendMessage("Hello from jMeter");
            connection.disconnect();
        } catch (XMPPException e) {
            throw new RuntimeException(e);
        }
    }

    public void testStarted(String host) {
        System.out.println("Test started!! host = " + host);
    }

    public void testEnded() {
        System.out.println("Test ended!!");
    }

    public void testEnded(String host) {
        System.out.println("Test ended!! host = " + host);
    }

    public void testIterationStart(LoopIterationEvent event) {
    }
}
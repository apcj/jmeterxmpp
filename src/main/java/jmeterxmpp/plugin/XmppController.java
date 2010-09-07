package jmeterxmpp.plugin;

import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleListener;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testelement.TestListener;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;

public class XmppController extends GenericController implements TestBean, TestListener, SampleListener {

    private String xmppServerAddress;
    private String destinationUser;

    private static XMPPConnection connection;
    private static Chat chat;

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
        connect();
        sendMessage("Test Started");
    }

    public void testStarted(String host) {
        System.out.println("Test started!! host = " + host);
    }

    public void testEnded() {
        sendMessage("Test ended");
        disconnect();
    }

    public void testEnded(String host) {
        System.out.println("Test ended!! host = " + host);
    }

    public void testIterationStart(LoopIterationEvent event) {
    }

    public void sampleOccurred(SampleEvent sampleEvent) {
        long sampleTime = sampleEvent.getResult().getEndTime() - sampleEvent.getResult().getStartTime();
        sendMessage("sample took - " + sampleTime + " ms");
    }

    public void sampleStarted(SampleEvent sampleEvent) {
        sendMessage("sample started");
    }

    public void sampleStopped(SampleEvent sampleEvent) {
        sendMessage("sample stopped");
    }

    private void connect() {
        this.connection = new XMPPConnection(xmppServerAddress);
        try {
            connection.connect();
            connection.login("jmeter", "jmeter");
            ChatManager chatmanager = connection.getChatManager();
            chat = chatmanager.createChat(destinationUser, new MessageListener() {
                public void processMessage(Chat chat, Message message) {
                    System.out.println("Received message: " + message);
                }
            });
        } catch (XMPPException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(String message) {
        try {
            chat.sendMessage(message);
        } catch (XMPPException e) {
            throw new RuntimeException(e);
        }
    }

    private void disconnect() {
        connection.disconnect();
    }
}
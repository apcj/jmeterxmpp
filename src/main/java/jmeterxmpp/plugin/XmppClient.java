package jmeterxmpp.plugin;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;

public class XmppClient {
    private XMPPConnection connection;
    private Chat chat;

    public void connect(String xmppServerAddress, String destinationUser) {
        connection = new XMPPConnection(xmppServerAddress);
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

    public void sendMessage(String message) {
        try {
            chat.sendMessage(message);
        } catch (XMPPException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        connection.disconnect();
    }
}

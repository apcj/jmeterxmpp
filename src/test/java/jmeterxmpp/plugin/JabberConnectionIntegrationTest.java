package jmeterxmpp.plugin;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.junit.Test;


public class JabberConnectionIntegrationTest {

    @Test
    public void shouldConnectToJabberServer() {

        System.out.println("Test started!!");
        XMPPConnection connection = new XMPPConnection("jalewis.thoughtworks.com");
        try {
            connection.connect();
//            SASLAuthentication.supportSASLMechanism("PLAIN", 0);
//            connection.getAccountManager().createAccount("jmeter", "jmeter");
            connection.login("jmeter", "jmeter");
            System.out.println("logged in");
            ChatManager chatmanager = connection.getChatManager();
            Chat newChat = chatmanager.createChat("boicy@jalewis.thoughtworks.com", new MessageListener() {
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

}

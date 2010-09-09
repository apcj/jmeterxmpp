package jmeterxmpp.plugin;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.MultiUserChat;

public class XmppClient {
    private XMPPConnection connection;
    private MultiUserChat multiUserChat;

    public void connect(String xmppServerAddress, String chatroomName) {
        connection = new XMPPConnection(xmppServerAddress);
        try {
            connection.connect();
            connection.login("jmeter", "jmeter");
            
            multiUserChat = new MultiUserChat(connection, chatroomName);
            multiUserChat.join("jmeter@" + xmppServerAddress, "jmeter");

        } catch (XMPPException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message) {
        try {
            multiUserChat.sendMessage(message);
        } catch (XMPPException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        multiUserChat.leave();
        connection.disconnect();
    }
}

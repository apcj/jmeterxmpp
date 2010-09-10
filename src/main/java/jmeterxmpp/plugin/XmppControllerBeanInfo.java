package jmeterxmpp.plugin;

import org.apache.jmeter.testbeans.BeanInfoSupport;

import java.beans.PropertyDescriptor;
import java.util.ResourceBundle;

public class XmppControllerBeanInfo extends BeanInfoSupport {

    // These names must agree case-wise with the variable and property names
    private static final String XMPP_SERVER_ADDRESS = "xmppServerAddress";
    private static final String CHATROOM_NAME = "chatroomName";
    private static final String JABBER_USERNAME = "jabberUsername";
    private static final String JABBER_PASSWORD = "jabberPassword";

    public XmppControllerBeanInfo() {
        super(XmppController.class);

        ResourceBundle rb = (ResourceBundle) getBeanDescriptor().getValue(RESOURCE_BUNDLE);

        createPropertyGroup("xmpp_controller", new String[] {XMPP_SERVER_ADDRESS});
        createPropertyGroup("xmpp_controller", new String[] {CHATROOM_NAME});
        createPropertyGroup("xmpp_controller", new String[] {JABBER_USERNAME});
        createPropertyGroup("xmpp_controller", new String[] {JABBER_PASSWORD});

        setPropertyDefaults(XMPP_SERVER_ADDRESS);
        setPropertyDefaults(CHATROOM_NAME);
        setPropertyDefaults(JABBER_USERNAME);
        setPropertyDefaults(JABBER_PASSWORD);
    }

    private void setPropertyDefaults(String propertyIdentifier) {
        PropertyDescriptor p = property(propertyIdentifier);
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, "");
        p.setValue(NOT_EXPRESSION, Boolean.TRUE);
    }

}
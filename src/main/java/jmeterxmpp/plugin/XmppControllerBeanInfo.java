package jmeterxmpp.plugin;

import org.apache.jmeter.testbeans.BeanInfoSupport;

import java.beans.PropertyDescriptor;
import java.util.ResourceBundle;

public class XmppControllerBeanInfo extends BeanInfoSupport {

    // These names must agree case-wise with the variable and property names
    private static final String XMPP_SERVER_ADDRESS = "xmppServerAddress";
    private static final String CHATROOM_NAME = "chatroomName";

    public XmppControllerBeanInfo() {
        super(XmppController.class);

        ResourceBundle rb = (ResourceBundle) getBeanDescriptor().getValue(RESOURCE_BUNDLE);

        createPropertyGroup("xmpp_controller", new String[] {XMPP_SERVER_ADDRESS});
        createPropertyGroup("xmpp_controller", new String[] {CHATROOM_NAME});

        setPropertyDefaults(XMPP_SERVER_ADDRESS);
        setPropertyDefaults(CHATROOM_NAME);
    }

    private void setPropertyDefaults(String propertyIdentifier) {
        PropertyDescriptor p = property(propertyIdentifier);
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, "");
        p.setValue(NOT_EXPRESSION, Boolean.TRUE);
    }

}
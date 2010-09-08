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

    private long lastReportingTime;

    private static XmppClient xmppClient = new XmppClient();

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
        xmppClient.connect(xmppServerAddress, destinationUser);
        xmppClient.sendMessage("Test Started");
    }

    public void testStarted(String host) {
        System.out.println("Test started!! host = " + host);
    }

    public void testEnded() {
        xmppClient.sendMessage("Test ended");
        xmppClient.disconnect();
    }

    public void testEnded(String host) {
        System.out.println("Test ended!! host = " + host);
    }

    public void testIterationStart(LoopIterationEvent event) {
    }

    public void sampleOccurred(SampleEvent sampleEvent) {
        long sampleTime = sampleEvent.getResult().getEndTime() - sampleEvent.getResult().getStartTime();
        xmppClient.sendMessage(String.valueOf(sampleTime));
    }

    public void sampleStarted(SampleEvent sampleEvent) {
        xmppClient.sendMessage("sample started");
    }

    public void sampleStopped(SampleEvent sampleEvent) {
        xmppClient.sendMessage("sample stopped");
    }


}
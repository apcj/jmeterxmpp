package jmeterxmpp.plugin;

import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleListener;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testelement.TestListener;
import org.apache.jmeter.visualizers.SamplingStatCalculator;

import java.util.concurrent.*;

public class XmppController extends GenericController implements TestBean, TestListener, SampleListener {

    private static final int REPORTING_INTERVAL = 1000;

    private SamplingStatCalculator calculator;

    public static XmppClient xmppClient = new XmppClient();
    public static ConcurrentLinkedQueue<SampleResult> samplesQueue = new ConcurrentLinkedQueue<SampleResult>();

    private String xmppServerAddress;
    private String chatroomName;
    private String jabberUsername;
    private String jabberPassword;
    private static ScheduledExecutorService scheduler;

    public String getJabberUsername() {
        return jabberUsername;
    }

    public void setJabberUsername(String jabberUsername) {
        this.jabberUsername = jabberUsername;
    }

    public String getJabberPassword() {
        return jabberPassword;
    }

    public void setJabberPassword(String jabberPassword) {
        this.jabberPassword = jabberPassword;
    }

    public String getChatroomName() {
        return chatroomName;
    }

    public void setChatroomName(String chatroomName) {
        this.chatroomName = chatroomName;
    }

    public String getXmppServerAddress() {
        return xmppServerAddress;
    }

    public void setXmppServerAddress(String xmppServerAddress) {
        this.xmppServerAddress = xmppServerAddress;
    }

    public void testStarted() {
        xmppClient.connect(xmppServerAddress, chatroomName, jabberUsername, jabberPassword);
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new SendResultsJob(), REPORTING_INTERVAL, REPORTING_INTERVAL, TimeUnit.MILLISECONDS);
        xmppClient.sendMessage("Test Started");
    }

    public void testStarted(String host) {
        System.out.println("Test started!! host = " + host);
    }

    public void testEnded() {
        scheduler.shutdown();
        xmppClient.sendMessage("Test ended");
        xmppClient.disconnect();
    }

    public void testEnded(String host) {
        System.out.println("Test ended!! host = " + host);
    }

    public void testIterationStart(LoopIterationEvent event) {
    }

    public void sampleOccurred(SampleEvent sampleEvent) {
        samplesQueue.add(sampleEvent.getResult());
    }

    public void sampleStarted(SampleEvent sampleEvent) {
        xmppClient.sendMessage("sample started");
    }

    public void sampleStopped(SampleEvent sampleEvent) {
        xmppClient.sendMessage("sample stopped");
    }
}
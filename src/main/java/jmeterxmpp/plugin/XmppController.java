package jmeterxmpp.plugin;

import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleListener;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testelement.TestListener;
import org.apache.jmeter.visualizers.SamplingStatCalculator;

public class XmppController extends GenericController implements TestBean, TestListener, SampleListener {

    private static final int REPORTING_INTERVAL = 1000;
    private static final String STATS_LABEL = "STATS";

    private String xmppServerAddress;
    private String chatroomName;

    private SamplingStatCalculator samplingStatCalculator;

    private static XmppClient xmppClient = new XmppClient();

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
        xmppClient.connect(xmppServerAddress, chatroomName);
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
        if (samplingStatCalculator == null) {
            samplingStatCalculator = new SamplingStatCalculator(STATS_LABEL);
        } else if (samplingStatCalculator.getElapsed() > REPORTING_INTERVAL) {
            xmppClient.sendMessage(String.valueOf(samplingStatCalculator.getRate()));
            samplingStatCalculator = new SamplingStatCalculator(STATS_LABEL);
        }
        samplingStatCalculator.addSample(sampleEvent.getResult());
    }

    public void sampleStarted(SampleEvent sampleEvent) {
        xmppClient.sendMessage("sample started");
    }

    public void sampleStopped(SampleEvent sampleEvent) {
        xmppClient.sendMessage("sample stopped");
    }


}
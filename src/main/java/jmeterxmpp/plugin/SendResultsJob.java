package jmeterxmpp.plugin;

import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.visualizers.SamplingStatCalculator;

import java.util.concurrent.ConcurrentLinkedQueue;

import static jmeterxmpp.plugin.MeasurementField.field;
import static jmeterxmpp.plugin.MeasurementRecord.record;

class SendResultsJob implements Runnable {

    private static final String STATS_LABEL = "STATS";

    public void run() {
        SamplingStatCalculator calculator = new SamplingStatCalculator(STATS_LABEL);
        ConcurrentLinkedQueue<SampleResult> oldQueue = XmppController.samplesQueue;
        XmppController.samplesQueue = new ConcurrentLinkedQueue<SampleResult>();
        SampleResult sampleResult;
        while ((sampleResult = oldQueue.poll()) != null) {
            calculator.addSample(sampleResult);
        }
        try {
            XmppController.xmppClient.sendMessage(record(
                    field("throughput", calculator.getRate()),
                    field("latency", calculator.getMean())).toJson());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

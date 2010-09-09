package jmeterxmpp.plugin;

import org.junit.Test;
import static jmeterxmpp.plugin.MeasurementRecord.record;
import static jmeterxmpp.plugin.MeasurementField.field;
import static junit.framework.Assert.assertEquals;

public class MeasurementRecordTest {

    @Test
    public void shouldSerialiseSingleFieldToJson() {
        String json = record(field("latency", 2.4)).toJson();
        assertEquals("{ latency: 2.4 }", json);
    }

    @Test
    public void shouldSerialiseMultipleFieldsToJson() {
        String json = record(field("latency", 2.4), field("throughput", 3)).toJson();
        assertEquals("{ latency: 2.4, throughput: 3 }", json);
    }
}

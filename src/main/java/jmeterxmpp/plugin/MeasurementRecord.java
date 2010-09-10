package jmeterxmpp.plugin;

public class MeasurementRecord {

    private MeasurementField[] fields;

    public MeasurementRecord(MeasurementField[] fields) {
        this.fields = fields;
    }

    public static MeasurementRecord record(MeasurementField... fields) {
        return new MeasurementRecord(fields);
    }

    public String toJson() {
        StringBuffer output = new StringBuffer();
        output.append("{ ");
        for (int i = 0; i < fields.length; i++) {
            output.append("\"").append(fields[i].getName()).append("\": ").append(fields[i].getValue());
            if (i < fields.length - 1) output.append(", ");
        }
        output.append(" }");
        return output.toString();
    }
}

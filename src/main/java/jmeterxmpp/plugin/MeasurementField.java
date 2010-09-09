package jmeterxmpp.plugin;

public class MeasurementField {
    private String name;
    private Number value;

    public MeasurementField(String name, Number value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Number getValue() {
        return value;
    }

    public static MeasurementField field(String name, Number value) {
        return new MeasurementField(name, value);
    }
}

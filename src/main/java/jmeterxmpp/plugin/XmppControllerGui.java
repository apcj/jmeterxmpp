package jmeterxmpp.plugin;

import org.apache.jmeter.control.gui.AbstractControllerGui;
import org.apache.jmeter.extractor.RegexExtractor;
import org.apache.jmeter.testelement.TestElement;

public class XmppControllerGui extends AbstractControllerGui {
    public String getLabelResource() {
        return "XMPP Controller";
    }

    public TestElement createTestElement() {
        RegexExtractor extractor = new RegexExtractor();
        modifyTestElement(extractor);
        return extractor;
    }

    public void modifyTestElement(TestElement testElement) {
        super.configureTestElement(testElement);
    }
}

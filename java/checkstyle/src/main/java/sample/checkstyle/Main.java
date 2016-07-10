package sample.checkstyle;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Main {

    @XmlElement
    @XmlAttribute
    int a;

    @XmlElement int b;

    @SuppressWarnings("xxx") int c;

    @XmlElement @XmlAttribute int d;
}

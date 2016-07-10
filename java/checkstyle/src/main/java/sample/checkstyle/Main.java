package sample.checkstyle;

import javax.xml.bind.annotation.XmlElement;

public class Main {

    @XmlElement
    int a;

    @XmlElement() // アノテーションは閉じ括弧を持つことはできません。
    int b;

    @SuppressWarnings("xxx")
    int c;

    @SuppressWarnings(value="xxx")
    int d;

    @SuppressWarnings({"xxx"}) // アノテーションの書式は 'COMPACT_NO_ARRAY' でなければなりません 。
    int e;

    @SuppressWarnings({"xxx", "yyy"})
    int f;

    @SuppressWarnings({"xxx", "yyy", }) // アノテーションの配列値の末尾をカンマにすることはできません。
    int g;
}
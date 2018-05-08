package uk.org.cse.nhm.language.adapt.impl;

import java.lang.reflect.Method;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class NameUtil {

    public static final String DEFAULT = "##default";

    public static String getAttributeName(Method m, XmlAttribute att) {
        if (att.name().equals(DEFAULT)) {
            return getGetterName(m);
        } else {
            return att.name();
        }
    }

    public static String getElementName(Method m, XmlElement e) {
        if (e.name().equals(DEFAULT)) {
            return getGetterName(m);
        } else {
            return e.name();
        }
    }

    public static String getGetterName(Method m) {
        final String mname = m.getName();
        final String result;
        if (mname.startsWith("get")) {
            final String subName = mname.substring(3);
            result = subName.substring(0, 1).toLowerCase() + subName.substring(1);
        } else if (mname.startsWith("is")) {
            final String subName = mname.substring(2);
            result = subName.substring(0, 1).toLowerCase() + subName.substring(1);
        } else {
            result = mname;
        }

        return result;
    }
}

package uk.ac.ucl.hideem;

import java.util.Map;

/**
 * Everything HIDEEM needs to know about a person.
 */
public class Person {
    public enum Sex {
        MALE, FEMALE
    }

    public final int age;
    public final Sex sex;
    public final boolean smokes;

    public Person(final int age, final Sex sex, final boolean smokes) {
        this.age = age;
        this.sex = sex;
        this.smokes = smokes;
    }
    
    public static Person readPerson(final Map<String, String> stuff) {
        return new Person(
            Integer.parseInt(stuff.get("age")),
            Person.Sex.valueOf(stuff.get("sex")),
            Boolean.valueOf(stuff.get("smokes")));
    }
    
}

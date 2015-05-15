package uk.ac.ucl.hideem;

import java.util.Map;

/**
 * Everything HIDEEM needs to know about a person.
 */
public class Person {
    public enum Sex {
        MALE, FEMALE
    }

    public int age;
    public final Sex sex;

    public Person(final int age, final Sex sex) {
        this.age = age;
        this.sex = sex;
    }
    
    public static Person readPerson(final Map<String, String> stuff) {
        return new Person(
            Integer.parseInt(stuff.get("age")),
            Person.Sex.valueOf(stuff.get("sex")));
    }
    
}

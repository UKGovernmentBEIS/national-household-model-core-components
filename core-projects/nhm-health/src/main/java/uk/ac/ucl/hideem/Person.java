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
    public boolean smokes;
    public int samplesize;

    public Person(final int age, final Sex sex, final boolean smokes, final int samplesize) {
        this.age = age;
        this.sex = sex;
        this.smokes = smokes;
        this.samplesize = samplesize;
    }

    public Person(final int age, final Sex sex, final boolean smokes) {
        this(age, sex, smokes, 1);
    }

    public static Person readPerson(final Map<String, String> stuff) {
        return new Person(
                Integer.parseInt(stuff.get("age")),
                Person.Sex.valueOf(stuff.get("sex")),
                Boolean.valueOf(stuff.get("smokes")),
                Integer.parseInt(stuff.get("samplesize")));
    }

}

package cse4006;

/**
 * Person Class
 *
 * @author Prev (0soo.2@prev.kr)
 */
public class Person {
    private String name;

    /**
     * Constructor of Person
     * @param name
     */
    public Person(String name) {
        this.name = name;
    }

    /**
     * Get name of person
     * @return String name
     */
    public String getName() {
        return this.name;
    }
}

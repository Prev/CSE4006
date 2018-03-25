import cse4006.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * FriendGraph Test
 *
 * @author Prev (0soo.2@prev.kr)
 */
public class FriendGraphTest {

    /**
     * Test friend graph with checking distances
     */
    @Test
    public void testFriendGraph() {

        FriendGraph graph = new FriendGraph(20);
        Person john = new Person("John");
        Person tom = new Person("Tom");
        Person jane = new Person("Jane");
        Person marry = new Person("Marry");

        graph.addPerson(john);
        graph.addPerson(tom);

        graph.addPerson(jane);
        graph.addPerson(marry);
        graph.addFriendship("John", "Tom");
        graph.addFriendship("Tom", "Jane");

        assertEquals(1, graph.getDistance("John", "Tom"));
        assertEquals(2, graph.getDistance("John", "Jane"));
        assertEquals(0, graph.getDistance("John", "John"));
        assertEquals(-1, graph.getDistance("Marry", "Marry"));
    }

}

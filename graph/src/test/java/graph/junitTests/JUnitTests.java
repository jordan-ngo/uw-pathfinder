package graph.junitTests;
import graph.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * This was created to test methods implemented in graph.java
 */


public class JUnitTests {
    /**
     * Test utilizes .contains to test that my graph has a singular node
     */
    @Test
    public void containsSingleNode() {
        Graph<String,String> graph = new Graph<String,String>();
        graph.addNode("hello");
        assertEquals(true, graph.containsNode("hello"));

    }

}

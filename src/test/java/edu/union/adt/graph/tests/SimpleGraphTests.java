package edu.union.adt.graph.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import edu.union.adt.graph.Graph;
import edu.union.adt.graph.GraphImplementation;
import edu.union.adt.graph.GraphFactory;

import java.util.List;
import java.util.ArrayList;

@RunWith(JUnit4.class)
public class SimpleGraphTests
{
    private Graph<String> g;
    
    @Before
    public void setUp()
    {
        g = new GraphFactory<String>().createGraph();
    }

    @After
    public void tearDown()
    {
        g = null;
    }
    
    @Test
    public void construct()
    {
	assertEquals("New graph has no vertices", 0, g.numVertices());
	assertEquals("New graph has no edges", 0, g.numEdges());
    }

    @Test
    public void addVertex()
    {
        g.addVertex("Foo");

        assertTrue("Adding a vertex causes the vertex to be in the graph",
                   g.contains("Foo"));

        

        assertEquals("Adding a vertex increases vertex count",
                     1, g.numVertices());

        assertEquals("Adding a vertex does not increase edge count",
                     0, g.numEdges());

        g.addVertex("Foo");

        assertEquals("Adding the vertex that already exits in the graph won't increase vertex count", 
                    1, g.numVertices());

        g.addVertex("haha");

        assertTrue("Adding a vertex causes the vertex to be in the graph",
                   g.contains("haha"));
        assertEquals("Adding the new vertex will increase the vertex count",
                     2, g.numVertices());
        assertEquals("Adding a vertex does not increase edge count",
                     0, g.numEdges());
    }

    @Test
    public void addEdge()
    {
        g.addVertex("Foo");
        g.addVertex("Bar");


        assertFalse("Before we add an edge, it's not there",
                    g.hasEdge("Foo", "Bar"));

        g.addEdge("Foo", "Bar");

        assertEquals("Adding the new vertex will increase the edge count",
                     1, g.numEdges());

        assertTrue("Adding an edge causes it to exist in the graph",
                   g.hasEdge("Foo", "Bar"));

        assertFalse("Adding edge Foo -> Bar does not add Bar -> Foo",
                    g.hasEdge("Bar", "Foo"));

        assertEquals("Adding an edge increases the degree of the source",
                     1, g.degree("Foo"));

        assertEquals("Adding an edge does not increase the degree of the destination",
                     0, g.degree("Bar"));

        g.addVertex("haha");
        g.addEdge("Foo", "haha");
        assertEquals("Adding the new vertex will increase the edge count",
                     2, g.numEdges());

        assertTrue("Adding an edge causes it to exist in the graph",
                   g.hasEdge("Foo", "haha"));
        g.addEdge("Foo", "haha");
        assertEquals("Adding the already existed vertex will not increase the edge count",
                     2, g.numEdges());

        g.addEdge("Bar","Foo");
        assertEquals("Adding the new vertex will increase the edge count",
                     3, g.numEdges());

        assertTrue("Adding an edge causes it to exist in the graph",
                   g.hasEdge("Bar", "Foo"));




    }

    @Test
    public void createNodesAndConnect()
    {
        g.addEdge("Foo", "Bar");

        assertTrue("Adding an edge creates the source node if it didn't exist",
                   g.contains("Foo"));
        assertTrue("Adding an edge creates the destinationt node if it didn't exist",
                   g.contains("Bar"));

        assertTrue("Adding an edge creates endpoints and connects them",
                   g.hasEdge("Foo", "Bar"));

        g.addEdge("Foo", "haha");

        assertTrue("Adding an edge creates the destinationt node if it didn't exist",
                   g.contains("haha"));
        assertTrue("Adding an edge creates endpoints and connects them",
                   g.hasEdge("Foo", "haha"));

        g.addEdge("hoho", "haha");

        assertTrue("Adding an edge creates the destinationt node if it didn't exist",
                   g.contains("hoho"));
        assertTrue("Adding an edge creates endpoints and connects them",
                   g.hasEdge("hoho", "haha"));

    }

    @Test
    public void adjacent()
    {
        g.addVertex("Foo");
        g.addVertex("Bar");
        g.addVertex("haha");
        g.addVertex("random");

        g.addEdge("Foo", "Bar");
        assertTrue("Adding an edge makes the destination adjacent to the source",
                   iteratorContains(g.adjacentTo("Foo"), "Bar"));

        g.addEdge("Foo", "haha");
        g.addEdge("random", "Foo");
        assertTrue("Adding an edge makes the destination adjacent to the source",
                   iteratorContains(g.adjacentTo("Foo"), "haha"));
        assertFalse("Adding an edge does not make the source adjacent to the destination",
                   iteratorContains(g.adjacentTo("Foo"), "random"));

        List<String> test = new ArrayList<String>();
        assertEquals("Null is not in the group so there will be an empty list",
                   iteratorToList(g.adjacentTo(null)), test);


        
    }

    private boolean iteratorContains(Iterable<String> container, String x)
    {
        for (String s: container) {
            if (s.equals(x)) {
                return true;
            }
        }

        return false;
    }

    private List<String> iteratorToList(Iterable<String> container) 
    {
        List<String> initial = new ArrayList<String>();
        for (String s: container) {
            initial.add(s);
        }
        return initial;
    }

    @Test
    public void getVertices() {
        g.addVertex("Foo");

        assertTrue("Adding Foo vertex makes the iterator has Foo",
                     iteratorContains(g.getVertices(), "Foo"));

        g.addVertex("haha");
        g.addVertex("random");
        assertTrue("Adding Foo vertex makes the iterator has haha",
             iteratorContains(g.getVertices(), "haha"));
    }
    
    @Test 
    public void degree()
    {
        g.addEdge("Foo", "Bar");
        g.addEdge("Foo", "Cat");
        g.addEdge("Cat", "Bar");
        g.addEdge("Bar", "Foo");

        assertEquals("Adding two edges from one source makes degree = 2",
                     2, g.degree("Foo"));

        assertEquals("Adding two edges to one destination does not make degree = 2",
                     1, g.degree("Bar"));
        
    }

    @Test
    public void string()
    {
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addVertex("D");
        g.addVertex("F");

        
        g.addEdge("A", "A");
        g.addEdge("A", "B");
        g.addEdge("C", "A");
        g.addEdge("C", "B");
        g.addEdge("D", "F");

        String graphString = g.toString();

        // I cannot just specify a single string, because the order we list the nodes
        // is not specified, and doesn't matter.
        
        verifyNoExtraCommas(graphString);
        verifyNoExtraSpaces(graphString);
        verifyNoExtraColons(graphString);

        verifyEdge(graphString, "A", "A");
        verifyEdge(graphString, "A", "B");
        verifyEdge(graphString, "C", "A");
        verifyEdge(graphString, "C", "B");
        verifyEdge(graphString, "D", "F");
    }

    private void verifyNoExtraCommas(String graphString)
    {
        String[] lines = graphString.split("\n");

        for (String line : lines) {
            assertFalse("Lines in string output should not end in commas",
                        line.matches(",\\p{Space}*$"));
        }
    }

    private void verifyNoExtraSpaces(String graphString)
    {
        String[] lines = graphString.split("\n");

        for (String line : lines) {
            assertFalse("Spaces should only be used in singles",
                        line.matches(" \\p{Space}+"));
            assertFalse("There should be no spaces at the end of the line",
                        line.matches("\\p{Space}$"));
        }
    }

    private void verifyNoExtraColons(String graphString)
    {
        String[] lines = graphString.split("\n");

        for (String line : lines) {
            assertFalse("There should be only one colon per line",
                        line.matches(".*:.*:"));
        }
    }

    private void verifyEdge(String graphString, String from , String to)
    {
        String[] lines = graphString.split("\n");

        for (String line : lines) {
            if (line.startsWith(from)) {
                String[] parts = line.split(":");
                if (parts[1].contains(to)) {
                    return;
                } else {
                    fail("No edge for " + from + "->" + to + " in string output");
                }
            }
        }

        fail("No vertex for " + from + " in string output");
            
    }
}

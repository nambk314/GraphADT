package edu.union.adt.graph.tests.buin;

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
import edu.union.adt.graph.GraphFactory;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


@RunWith(JUnit4.class)
public class GraphTestBuin
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
    public void isEmpty()
    {
    	assertTrue("A newly create graph should be an empty one", g.isEmpty());

    	g.addVertex("haha");
    	assertFalse("Adding a vertex to the graph make it not empty", g.isEmpty());

    	g.removeVertex("haha");
    	assertTrue("removing the only vertex in the graph make it empty", g.isEmpty());
    }


    @Test
    public void removeVertex() {
    	g.addVertex("Foo");
    	g.addVertex("haha");

    	g.removeVertex("Foo");

    	assertFalse("Removing vertex Foo from the graph so the graph no longer contains vertex Foo", 
    		g.contains("Foo"));
        assertTrue("Removing vertex Cat that is not in the graph will not change the graph",
            g.contains("haha"));
    	assertTrue("Removing only Foo so there is still vertex haha in the graph",
    		g.contains("haha"));

    	g.addVertex("hoho");

    	g.addVertex("dope");

    	g.addEdge("haha", "hoho");

    	g.addEdge("hoho", "dope");

    	g.addEdge("dope", "haha");

    	g.removeVertex("hoho");

    	assertFalse("Removing vertex hoho from the graph will make all the adjacent vertex no longer have egde lead to hoho"
    		, g.hasEdge("haha", "hoho"));

    	assertTrue("Removing vertex hoho will not affect other edge in the graph", 
    		g.hasEdge("dope", "haha"));
    }

    @Test
    public void removeEdge() {
    	g.addVertex("Foo");
    	g.addVertex("haha");

    	g.addEdge("Foo", "haha");

    	g.removeEdge("Foo", "haha");
    	assertFalse("Remove edge from Foo to haha will result in no edge from Foo to Haha",
    		g.hasEdge("Foo", "haha"));
    	assertTrue("Remove edge from Foo to haha will not effect the existance of vertex Foo",
    		g.contains("Foo"));

    	g.addVertex("hoho");
    	g.addEdge("haha", "hoho");
    	g.addEdge("Foo", "haha");
    	g.removeEdge("haha", "hoho");

    	assertTrue("Remove edge from haha to hoho will not effect the edge from Foo to haha",
    		g.hasEdge("Foo", "haha"));
    }

    @Test
    public void hasPath() {
    	g.addVertex("Ant");

    	assertTrue("A vertex does have path to itself", 
    		g.hasPath("Ant", "Ant"));

    	g.addVertex("Buu");
    	g.addVertex("Cow");
    	g.addVertex("Dog");
 
    	g.addEdge("Ant", "Buu");

    	assertTrue("There is a path from Ant to Buu",
    		g.hasPath("Ant", "Buu"));
    	assertFalse("But there is no path from Buu to Ant",
    		g.hasPath("Buu", "Ant"));

    	g.addEdge("Buu", "Cow");
    	assertTrue("There is a path from Buu to Cow",
    		g.hasPath("Buu", "Cow"));
    	assertTrue("There is a path from Ant to Cow",
    		g.hasPath("Ant", "Cow"));

    	g.addEdge("Ant", "Dog");
    	assertFalse("There is no path from Cow to Dog",
    		g.hasPath("Cow", "Dog"));

        assertFalse("There is no path from Cow to Dragon cause there is no vertex dragon",
            g.hasPath("Cow", "Dragon"));


    }

    @Test
    public void pathLength() {
    	g.addVertex("Ant");

    	assertEquals("Path length of a Vertex to itself is 0", g.pathLength("Ant", "Ant"), 0);

    	g.addVertex("Buu");
    	g.addVertex("Cow");
    	g.addVertex("Dog");

    	g.addEdge("Ant", "Buu");
    	assertEquals("There is a path length equal 1 from Ant to Buu",
    		g.pathLength("Ant", "Buu"), 1);
    	assertEquals("There is not a path from Buu to Ant so the value is Integer.MAX_VALUE",
    		g.pathLength("Buu", "Ant"), Integer.MAX_VALUE);
    	g.addEdge("Buu", "Cow");

    	assertEquals("Path length from Ant to Cow equal 2", g.pathLength("Ant", "Cow"), 2);

    	g.addEdge("Cow", "Dog");
    	assertEquals("Path length from Ant to Dog is 3", g.pathLength("Ant", "Dog"), 3);

        g.addEdge("Ant", "Cow");
        assertEquals("Path length from Ant to Dog is now 2", g.pathLength("Ant", "Dog"), 2);

        g.addVertex("Egg");

        g.addEdge("Buu", "Egg");
        g.addEdge("Dog", "Egg");

        assertEquals("Path length from Ant to Dog is now 2", g.pathLength("Ant", "Egg"), 2);



    }

    @Test
    public void getPath(){
    	g.addVertex("Ant");
    	g.addVertex("Buu");
    	g.addVertex("Cow");
    	g.addVertex("Dog");

    	g.addEdge("Ant", "Buu");
    	assertTrue("The path from Ant to Buu contains Ant",
    		iteratorContains(g.getPath("Ant", "Buu"), "Ant"));


    	g.addEdge("Buu", "Cow");
    	assertTrue("The path from Ant to Cow contains Buu",
    		iteratorContains(g.getPath("Ant", "Cow"), "Buu"));
        assertTrue("The path from Ant to Cow contains Cow",
            iteratorContains(g.getPath("Ant", "Cow"), "Cow"));
        ArrayList<String> pathOne = new ArrayList<String>(Arrays.asList("Ant", "Buu", "Cow"));
        assertEquals("The path from Ant to Cow will be ['Ant', 'Buu', 'Cow']",
                    iteratorToList(g.getPath("Ant", "Cow")), pathOne);


    	g.addEdge("Ant", "Cow");
    	assertFalse("The path from Ant to Cow no longer has Buu since there is a shorter path",
    		iteratorContains(g.getPath("Ant", "Cow"), "Buu"));
        ArrayList<String> pathTwo = new ArrayList<String>(Arrays.asList("Ant", "Cow"));
        assertEquals("The path from Ant to Cow will be ['Ant', 'Cow']",
                    iteratorToList(g.getPath("Ant", "Cow")), pathTwo);

    	g.addEdge("Cow", "Dog");
    	assertFalse("The path from Buu to Dog will not contain Ant",
    		iteratorContains(g.getPath("Buu", "Dog"), "Ant"));


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


}


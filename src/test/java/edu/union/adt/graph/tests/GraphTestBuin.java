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

    	assertFalse("A vertex does not have path to itself", 
    		g.hasPath("Ant", "Ant"));

    	g.addVertex("Buu");
    	g.addVertex("Cow");
    	g.addVertex("Dog");
 
    	g.addEdge("Ant", "Boo");

    	assertTrue("There is a path from Ant to Boo",
    		g.hasPath("Ant", "Buu"));
    	assertFalse("But there is no path from Boo to Ant",
    		g.hasPath("Boo", "Ant"));

    	g.addEdge("Buu", "Cow");
    	assertTrue("There is a path from Buu to Cow",
    		g.hasPath("Buu", "Cow"));
    	assertTrue("There is a path from Ant to Cow",
    		g.hasPath("Ant", "Cow"));

    	g.addEdge("Ant", "Dog");
    	assertFalse("There is no path from Cow to Dog",
    		g.hasPath("Cow", "Dog"));

    	g.addEdge("Foo", "Foo");

    	assertFalse("A vertex does not have path to itself even if it has edge to itself",
    		g.hasPath("Foo", "Foo"));


    }


}


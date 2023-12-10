package diver;
import game.*;
import datastructures.PQueue;
import datastructures.SlowPQueue;
import graph.ShortestPaths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


/** This is the place for your implementation of the {@code SewerDiver}.
 */
public class McDiver implements SewerDiver {

    /** See {@code SewerDriver} for specification. */
    @Override
    public void seek(SeekState state) {
        seeker(state);
    }

    /**
     * See {@code SewerDriver} for specification.
     */
    @Override
    public void scram(ScramState state) {
        LinkedList<Node> coinNodes = new LinkedList<>();
        Collection<Node> c = state.allNodes();
        Set<Node> vertices = new HashSet<>(c);
        Maze graph = new Maze(vertices);
        ShortestPaths<Node, Edge> s = new ShortestPaths<>(graph);
        s.singleSourceDistances(state.currentNode());
        for (Node vertex : vertices) {
            if (vertex.getTile().coins() != 0) {
                coinNodes.add(vertex);
            }
        }
        helperScram(state, coinNodes);
    }

    protected ArrayList<Long> past = new ArrayList<>();

    /**
     * Compares known locations to possible unexplored locations, and continuously moves McDiver
     * to the unexplored location closest to the ring until he reaches the ring.
     * @param state <- nonempty Seekstate
     */
    protected boolean seeker(SeekState state) {
        long loc = state.currentLocation();
        PQueue<Long> future = new SlowPQueue<>();
        if (state.distanceToRing() == 0) {
            past.clear();
            return true;
        }
        past.add(loc);
        for (NodeStatus status : state.neighbors()) {
            long ID = status.getId();
            if (!past.contains(ID)) {
                future.add(ID, status.getDistanceToRing());
            }
        }
        //inv: 'ID' is the next unvisited location closest to the ring
        while (!future.isEmpty()) {
            long ID = future.extractMin();
            state.moveTo(ID);
            if (seeker(state)) {
                return true;
            }
            state.moveTo(loc);
        }
        return false;
    }

    /**
     * Helps McDiver collect coins within the prescribed number of steps while ensuring that enough
     * steps remain to reach the exit. Method ends when McDiver reaches the exit either because all coins
     * were collected or because there are not enough remaining steps to collect additional coins.
     * @param state <- ScramState
     * @param coins <- LinkedList<Node> containing all the vertices that contain coins present on the map
     */
    protected void helperScram(ScramState state, LinkedList<Node> coins) {
        Collection<Node> c = state.allNodes();
        Set<Node> vertices = new HashSet<>(c);
        Maze graph = new Maze(vertices);
        ShortestPaths<Node, Edge> s = new ShortestPaths<>(graph);
        s.singleSourceDistances(state.currentNode());
        if (coins.isEmpty()) {
            for (Edge edge : s.bestPath(state.exit())) {
                state.moveTo(graph.dest(edge));
            }
            return;
        }
        Comparator<Node> compare = (left, right) -> (int) (s.getDistance(left) - s.getDistance(right));
        Collections.sort(coins, compare);
        Node closest = null;
        ShortestPaths<Node, Edge> s2 = new ShortestPaths<>(graph);
        int counter = 0;
        s2.singleSourceDistances(coins.get(0));
        counter++;
        //inv: 'counter' references the index of the next closest coin node in 'coins'. Required that distance to coin node plus distance
        // to exit is greater than remaining steps and that 'counter' is less than size of 'coins'.
        while ((s.getDistance(coins.get(counter - 1)) + s2.getDistance(state.exit()) > state.stepsToGo()) && counter < coins.size()) {
            s2.singleSourceDistances(coins.get(counter));
            counter++;
        }
        if (s.getDistance(coins.get(counter - 1)) + s2.getDistance(state.exit()) <= state.stepsToGo()) {
            closest = coins.get(counter - 1);
        }
        if (closest == null) {
            for (Edge edge : s.bestPath(state.exit())) {
                state.moveTo(graph.dest(edge));
            }
            return;
        } else {
            for (Edge edge : s.bestPath(closest)) {
                state.moveTo(graph.dest(edge));
            }
        }
        coins.remove(closest);
        helperScram(state, coins);
    }
}

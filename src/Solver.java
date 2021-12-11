import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Our solution uses a hashset, a queue, and a class that contained both the state and the parent to solve the Pouring Problem
 * @author Thomas Kwashnak
 * @author Emily Balboni
 * @author Priscilla Esteves
 */
public class Solver {

// We created the HashSet and the Queue which created the LinkedList for the int cup and target sizes
    static final HashSet<State> VISITED_STATES = new HashSet<>();
    static final Queue<State> UNVISITED_STATES = new LinkedList<>();

    static int[] CUP_SIZES;
    static int TARGET_SIZE;

    public static void main(String[] args) {
        solve(new File("resources/input6.txt"));
    }

    public static void solve(File file) {
        try {
            Scanner trialReader = new Scanner(file);
            String values = trialReader.nextLine();
            String[] val = values.split(" ");
            CUP_SIZES = new int[val.length];
            for (int i = 0; i < CUP_SIZES.length; i++) {
                CUP_SIZES[i] = Integer.parseInt(val[i]);
            }
            TARGET_SIZE = trialReader.nextInt();
        } catch (FileNotFoundException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        Stack<State> solutionStack = new Stack<>();

        State solution = findSolution();
        if (solution != null) {
            while (solution.parent != null) {
                solutionStack.add(solution);
                solution = solution.parent;
            }

            while (!solutionStack.isEmpty()) {
                State i = solutionStack.pop();
                System.out.println(i.from + " " + i.to);
            }
        } else {
            System.out.println("NOT POSSIBLE");
        }
    }
// Creates the original state and adds it to the Queue. While the Queue is not empty it wil take the next item from the queue and check if it's a valid solution. If it's not then it will create the children
    public static State findSolution() {
        UNVISITED_STATES.add(new State());
        while (!UNVISITED_STATES.isEmpty()) {
            State item = UNVISITED_STATES.remove();
            VISITED_STATES.add(item);
            for (int i : item.cups) {
                if (i == TARGET_SIZE) {
                    return item;
                }
            }
            createChildren(item);
        }
        return null;
    }

    /**
     * Basically we just create children by testing all possible pairs of cups (that are not a cup and itself), and emulate a pour from one to
     * another. We check if the HashSet contains that state. If it does not, then it will add that state to the unvisited queue
     * @param parent Parent to create children from
     */
    public static void createChildren(State parent) {
        for(int i = 0; i < CUP_SIZES.length; i++) {
            for(int j = 0; j < CUP_SIZES.length; j++) {
                if(i != j) {
                    State state = new State(parent,i,j);
                    if(!VISITED_STATES.contains(state)) {
                        UNVISITED_STATES.add(state);
                    }
                }
            }
        }
    }

    public static class State {

        final State parent;
        final int[] cups;
        final int from, to;

        public State(State parent, int from, int to) {
            this.parent = parent;
            cups = new int[parent.cups.length];
            this.from = from;
            this.to = to;

            System.arraycopy(parent.cups, 0, cups, 0, cups.length);

            int amountTransferred = Math.min(CUP_SIZES[to] - cups[to], cups[from]);
            cups[from] -= amountTransferred;
            cups[to] += amountTransferred;
        }

        public State() {
            cups = new int[CUP_SIZES.length];
            cups[0] = CUP_SIZES[0];
            parent = null;
            from = 0;
            to = 0;
        }

        /**
         * Overrides hash function to ignore the parent class from the hash function
         * @return hash code based on the state array
         */
        @Override
        public int hashCode() {
            return Arrays.hashCode(cups);
        }

        /**
         * Overrides the equals method (used in hashSet functions) such that the parent value is not checked to be equal
         * @param o Object to check if equals
         * @return True if the state is the same state, regardless of parent
         */
        @Override
        public boolean equals(Object o) {
            return o instanceof State && Arrays.equals(cups, ((State) o).cups);
        }
    }
}

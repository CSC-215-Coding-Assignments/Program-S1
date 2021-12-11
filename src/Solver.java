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

    public static void createChildren(State parent) {
        for (int i = 0; i < CUP_SIZES.length - 1; i++) {
            for (int j = i + 1; j < CUP_SIZES.length; j++) {
                State a = new State(parent, i, j), b = new State(parent, j, i);
                if (!VISITED_STATES.contains(a)) {
                    UNVISITED_STATES.add(a);
                }
                if (!VISITED_STATES.contains(b)) {
                    UNVISITED_STATES.add(b);
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

        @Override
        public int hashCode() {
            return Arrays.hashCode(cups);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            State state = (State) o;

            return Arrays.equals(cups, state.cups);
        }
    }
}

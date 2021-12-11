import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Solver {

    static final HashSet<State> A = new HashSet<>();
    static final Queue<State> queue = new LinkedList<>();

    static int[] sizes;
    static int varSize;

    public static void main(String[] args) {
        solve(new File("resources/input6.txt"));
    }

    public static void solve(File file) {
        try {
            Scanner trialReader = new Scanner(file);
            String values = trialReader.nextLine();
            String[] val = values.split(" ");
            sizes = new int[val.length];
            for (int i = 0; i < sizes.length; i++) {
                sizes[i] = Integer.parseInt(val[i]);
            }
            varSize = trialReader.nextInt();
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
        queue.add(new State());
        while (!queue.isEmpty()) {
            State item = queue.remove();
            A.add(item);
            for (int i : item.cups) {
                if (i == varSize) {
                    return item;
                }
            }
            createChildren(item);
        }
        return null;
    }

    public static void createChildren(State parent) {
        for (int i = 0; i < sizes.length - 1; i++) {
            for (int j = i + 1; j < sizes.length; j++) {
                State a = new State(parent, i, j), b = new State(parent, j, i);
                if (!A.contains(a)) {
                    queue.add(a);
                }
                if (!A.contains(b)) {
                    queue.add(b);
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

            int amountTransferred = Math.min(sizes[to] - cups[to], cups[from]);
            cups[from] -= amountTransferred;
            cups[to] += amountTransferred;
        }

        public State() {
            cups = new int[sizes.length];
            cups[0] = sizes[0];
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

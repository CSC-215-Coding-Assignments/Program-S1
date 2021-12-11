import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Solver {

    HashSet<State> A = new HashSet<>();
    Queue<State> queue = new LinkedList<>();
    //Queue of States (use as List<State>)

    static int[] sizes;


    public Solver(File file) {
        //set up sizes
    }

    public void findSolution(PrintStream out) {

        //While queue is not empty, add to hashset, check if it is a solution, if it is, then go to solutionFound(), otherwise create children
    }

    public void createChildren(State parent) {
        //Systematically creating children, checking if they are in hashset, if not, then add to queue
        for(int i = 0; i < sizes.length - 1; i++) {
            for(int j = i + 1; j < sizes.length; j++) {
                State a = new State(parent,i,j), b = new State(parent,j,i);
                if(!A.contains(a)) {
                    queue.add(a);
                }
                if(!A.contains(b)) {
                    queue.add(b);
                }
            }
        }
    }

    public void solutionFound(State state) {

    }


    public class State {
        State parent;
        int[] cups;
        int from,to;

        public State(State parent, int from, int to) {
            this.parent = parent;
            cups = new int[sizes.length];
            this.from = from;
            this.to = to;
            //Create a state that pours from parent, from form and to to.


        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            State state = (State) o;

            return Arrays.equals(cups, state.cups);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(cups);
        }
    }
}

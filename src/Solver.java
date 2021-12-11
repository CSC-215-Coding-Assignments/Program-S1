import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.io.File;
import java.util.Scanner;

public class Solver {

    static HashSet<State> A = new HashSet<>();
    static Queue<State> queue = new LinkedList<>();


    static int[] sizes;
    static int varSize;

    public Solver(File file) {
        try {
            Scanner trialReader = new Scanner(file);
            while(trialReader.hasNextLine()){
               // for(int i = 0; i <


            }
        }catch (FileNotFoundException e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public static void findSolution() {
        queue.add(new State());
        while(!queue.isEmpty()){
            State item = queue.remove();
            A.add(item);
            for(int i: item.cups) {
                if(i == varSize ){
                    solutionFound(item);
                    return;
                }
            }
            createChildren(item);
        }

        //While queue is not empty, add to hashset, check if it is a solution, if it is, then go to solutionFound(), otherwise create children
    }

    public static void createChildren(State parent) {
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

    public static void solutionFound(State state) {

    }


    public static class State {
        State parent;
        int[] cups;
        int from,to;

        public State(State parent, int from, int to) {
            this.parent = parent;
            cups = new int[parent.cups.length];
            this.from = from;
            this.to = to;

            System.arraycopy(parent.cups, 0, cups, 0, cups.length);

            int amountTransferred = Math.min(sizes[to] - cups[to],cups[from]);
            cups[from] -= amountTransferred;
            cups[to] += amountTransferred;
        }

        public State() {
            cups = new int[sizes.length];
            cups[0] = sizes[0];
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

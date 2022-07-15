import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String matcher = "";
        double index = 1.0;
        while(!StdIn.isEmpty()){
            String word = StdIn.readString();
            if (StdRandom.bernoulli(1 / index)){
                matcher = word;
            }
            index += 1.0;
        }
        StdOut.println(matcher);
    }
}

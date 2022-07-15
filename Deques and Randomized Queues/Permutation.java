import edu.princeton.cs.algs4.StdIn;

public class Permutation
{
    public static void main(String[] args)
    {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        for (int t = 1; t <= k; t++)
        {
            String string = StdIn.readString();
            randomizedQueue.enqueue(string);
        }
        for (int t = 1; t <= k; t++)
        {
            System.out.println(randomizedQueue.dequeue());
        }
    }
}

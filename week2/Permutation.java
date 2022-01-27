/* *****************************************************************************
 *  Name: Anuj Upadhyay
 *  Date: 26 Jan, 2022
 *  Description: a client program that takes an integer k as a command-line
 * argument; reads a sequence of strings from standard input
 * using StdIn.readString(); and prints exactly k of them, uniformly at random.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException();
        }

        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String i = StdIn.readString();
            queue.enqueue(i);
        }
        
        for (int i = 0; i < k; i++) {
            StdOut.println(queue.sample());
        }
    }
}

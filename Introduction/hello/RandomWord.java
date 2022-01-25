/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champ = "";
        int count = 1;

        while (!StdIn.isEmpty()) {
            String member = StdIn.readString();

            if (StdRandom.bernoulli((double) 1 / count)) {
                champ = member;
            }

            count++;
        }

        StdOut.println(champ);
    }
}

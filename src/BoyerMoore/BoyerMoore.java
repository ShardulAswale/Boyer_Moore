package BoyerMoore;

import java.io.BufferedReader;
import java.io.FileReader;
import static java.lang.Integer.min;
import java.util.Hashtable;
import java.util.Scanner;

public class BoyerMoore {

    static Hashtable<Character, Integer> bct = new Hashtable<Character, Integer>();

    public static void main(String[] args) {        
        Scanner in = new Scanner(System.in);
        int ch = 1;
        while (ch != 3) {
            System.out.println("1. ENTER TEXT MANUALLY");
            System.out.println("2. ENTER A FILE");
            System.out.println("3. EXIT");
            System.out.println("ENTER YOUR CHOICE :");
            ch = in.nextInt();
            if (ch == 1) {
                System.out.println("ENTER THE TEXT IN WHICH PATTERN IS TO BE SEARCHED FOR :");
                String text = in.next();
                System.out.println("ENTER THE PATTERN");
                String pat = in.next();
                text = text.toLowerCase();
                pat = pat.toLowerCase();
                BCTable(pat);
                int count = BMMatcher(text, pat);
                System.out.println(count + " results");
            } else if (ch == 2) {
                String text = fileReader();
                System.out.println("ENTER THE PATTERN");
                String pat = in.next();
                long start = System.currentTimeMillis();
                text = text.toLowerCase();
                pat = pat.toLowerCase();
                BCTable(pat);
                int count = BMMatcher(text, pat);
                long end = System.currentTimeMillis();
                System.out.println("Resut in : " + (end - start) + "ms");
                System.out.println(count + " results");
            }
        }
    }

    public static String fileReader() {
        String line, text = "";
        try {
            FileReader f = new FileReader("ss.txt");
            BufferedReader br = new BufferedReader(f);
            while ((line = br.readLine()) != null) {
                line.trim();
                text = text + line;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return text;
    }

    static void BCTable(String pat) {
        int i, j, dom = 128;
        for (i = 0; i < 128; i++) {
            bct.put((char) i, -1);
            for (j = pat.length() - 1; j >= 0; j--) {
                if ((char) i == pat.charAt(j)) {
                    bct.put((char) i, j);
                    break;
                }
            }
        }
    }

    static int BMMatcher(String text, String pat) {
        int i, j, count = 0;
        i = pat.length() - 1;
        j = pat.length() - 1;
        int m = pat.length();
        while (i <= text.length() - 1) {
            if (pat.charAt(j) == text.charAt(i)) {
                if (j == 0) {
                    System.out.println("Pattern found at index " + i);
                    i = i + m;
                    j = m - 1;
                    count++;
                } else {
                    j = j - 1;
                    i = i - 1;
                }
            } else {
                i = i + m - Math.min(j, 1 + bct.get(text.charAt(i)));
                j = m - 1;
            }
        }
        return count;
    }
}

package us.mattgreen;

import java.util.Scanner;

public class Main {

    private final static FileInput cardAccts = new FileInput("movie_cards.csv");
    private final static FileInput cardPurchases = new FileInput("movie_purchases.csv");
    private final static FileInput movRating = new FileInput("movie_rating.csv");
    private static String line = "";
    private static String rating_line = "";

    public static void main(String[] args) {
        String line;
        String[] fields;
        int[] nums = new int[3];
        boolean first = true;
        boolean first_rating = true;
        System.out.format("%8s  %-18s %6s %6s %6s\n","Account","Name", "Movies", "Points", "Avg Rating");
        while ((line = cardAccts.fileReadLine()) != null) {
            fields = line.split(",");
            findPurchases(first, fields[0], nums);
            first = false;
            findRating(first_rating, fields[0], nums);
            first_rating = false;
            System.out.format("00%6s  %-18s  %2d   %4d  %6d\n",fields[0],fields[1], nums[0], nums[1], nums[2]);
        }
    }

    public static void findPurchases(boolean first, String acct, int[] nums) {
        nums[0] = 0;
        nums[1] = 0;
        String[] fields;
        boolean done = false;
        if (first) {
            line = cardPurchases.fileReadLine();
        }
        while ((line != null) && !(done)) {
            fields = line.split(",");
            if (fields[0].compareTo(acct) > 0) {
                done = true;
            }
            else if (fields[0].equals(acct)) {
                nums[0]++;
                nums[1] += Integer.parseInt(fields[2]);
                line = cardPurchases.fileReadLine();
            }

        }
    }

    public static void findRating(boolean first, String acct, int[] nums) {
        nums[2] = 0;
        int count = 0;
        String[] fields;
        boolean done = false;
        if (first) {
            rating_line = movRating.fileReadLine();
        }
        while ((rating_line != null) && !(done)) {
            fields = rating_line.split(",");
            if (fields[0].compareTo(acct) > 0) {
                done = true;
            }
            else if (fields[0].equals(acct)) {
                //count++;
                nums[2] += Integer.parseInt(fields[1]);
                rating_line = movRating.fileReadLine();
            }
        }

        if (count > 0){
            nums[2] /= count;
        }
    }
}
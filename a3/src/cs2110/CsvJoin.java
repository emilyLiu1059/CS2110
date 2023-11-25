package cs2110;

import java.io.IOException;
import java.util.Scanner;
import java.io.FileReader;

public class CsvJoin {

    /**
     * Load a table from a Simplified CSV file and return a row-major list-of-lists representation.
     * The CSV file is assumed to be in the platform's default encoding. Throws an IOException if
     * there is a problem reading the file.
     */
    public static Seq<Seq<String>> csvToList(String file) throws IOException {
            FileReader input = new FileReader(file);
            Scanner scnr = new Scanner(input);
            LinkedSeq<Seq<String>> part2 = new LinkedSeq<>();
            if(!scnr.hasNextLine()) {
                return part2;
            }
            while (scnr.hasNextLine()) {
                String line = scnr.nextLine();
                LinkedSeq<String> part1 = new LinkedSeq<>();
                String[] x = line.split(",", -1);
                for(int i = 0; i < x.length; i++) {
                    part1.append(x[i]);
                }
                part2.append(part1);
            }
            return part2;
    }

    /**
     * Return a boolean on whether a table 'test' is rectangular
     */
    public static boolean isRectangular(Seq<Seq<String>> test){
        assert test != null;
        boolean firstColumnTraversed = false;
        int size = 0;
        boolean output = false;
        for(Seq<String> row : test) {
            int counter = 0;
            for(String column : row) {
                counter++;
            }
            if(!firstColumnTraversed) {
                size = counter;
                firstColumnTraversed = true;
            }
            else {
                output = (size == counter);
            }
        }
        return output;
    }

    /**
     * Return the left outer join of tables `left` and `right`, joined on their first column. Result
     * will represent a rectangular table, with empty strings filling in any columns from `right`
     * when there is no match. Requires that `left` and `right` represent rectangular tables with at
     * least 1 column.
     */
    public static Seq<Seq<String>> join(Seq<Seq<String>> left, Seq<Seq<String>> right) {
        int leftSize = 0;
        int rightSize = 0;
        for (Seq<String> leftRow : left) {
            leftSize++;
        }
        for (Seq<String> rightRow : right) {
            rightSize++;
        }
        assert leftSize >= 1 && rightSize >= 1;
        assert isRectangular(left) && isRectangular(right);
        Seq<Seq<String>> result = new LinkedSeq<>();
        for (Seq<String> leftRow : left) {
            boolean leftCopied = false;
            for (Seq<String> rightRow : right) {
                if (leftRow.get(0).equals(rightRow.get(0))) {
                    Seq<String> nextRow = new LinkedSeq<>();
                    for (String s : leftRow) {
                        nextRow.append(s);
                    }
                    for (String s : rightRow) {
                        if (!(s.equals(rightRow.get(0)))) {
                            nextRow.append(s);
                        }
                    }
                    leftCopied = true;
                    result.append(nextRow);
                }
            }
            if (!leftCopied) {
                Seq<String> nextRow = new LinkedSeq<>();
                for (String s : leftRow) {
                    nextRow.append(s);
                }
                for (int i = 0; i < right.get(0).size() - 1; i++) {
                    nextRow.append("");
                }
                result.append(nextRow);
            }
        }
        return result;
    }

    /** merges two CSV files using a left outer join, and outputs the resulting CSV.
     * If there are any problems reading the files, or if the resulting tables do not meet the preconditions for join(),
     * print an appropriate helpful message for the user and exit the program
     * @param f1 the name of the left table file
     * @param f2 the name of the right table file
     * returns the two files joined together by the left column
     */
    public static void main(String f1, String f2) {
        if(f1 == null || f2 == null || f1.isEmpty() || f2.isEmpty()) {
            System.err.println("Usage: cs2110.CsvJoin <left_table.csv> <right_table.csv>");
            System.exit(1);
        }
        try{Seq<Seq<String>> left = csvToList(f1);
            Seq<Seq<String>> right = csvToList(f2);
            if(!isRectangular(left) && !isRectangular(right)){
                System.err.println("Error: Input tables are not rectangular.");
                System.exit(1);
            }
            Seq<Seq<String>> jj = join(left, right);
            for(Seq<String> row : jj) {
                for(String col : row) {
                    System.out.print(col + ",");
                }
                System.out.println("");
            }
        }
        catch (IOException a){
            System.err.println("Error: Could not read input tables java.io.FileNotFoundException: missing.csv (No such file or directory)");
            System.exit(1);
        }
    }

    public static void main(String[]args) {
        main("input-tests/example/input1.csv", "input-tests/example/input2.csv");
    }
}

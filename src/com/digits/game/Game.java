package com.digits.game;

import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class Game {

    public static void main(String[] args) {

        System.out.println("Choose a unique 4 digit number (No digits in the number repeat)\n"
                + "Each time computer will offer 4 digit number, you must reply with NM response,\n" +
                "where N is how many digits exists and M is a number of correctly placed digits.\n");

        Set<Combination> combinations = new LinkedHashSet<>();
        Analyzer analyzer = new Analyzer(combinations);

        Scanner keyboard = new Scanner(System.in);
        Solution solution = new Solution(new Combination(Combination.generate()), false);
        while (true) {
            System.out.println(solution.getCombination());
            System.out.println("Insert matching result NM");
            String input = keyboard.nextLine();
            int present = Integer.parseInt(String.valueOf(input.charAt(0)));
            int correct = Integer.parseInt(String.valueOf(input.charAt(1)));
            solution = analyzer.evaluate(solution.getCombination(), present, correct);
            if (solution.isReady()) {
                System.out.println("Solution is: " + solution.getCombination());
                break;
            }
        }
    }

}

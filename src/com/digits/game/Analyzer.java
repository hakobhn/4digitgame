package com.digits.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.digits.game.Combination.LENGTH;

public class Analyzer {

    private Set<Combination> combinations;
    private Map<Combination, Integer> statistics;

    public Analyzer(Set<Combination> combinations) {
        this.combinations = combinations;
        this.statistics = new HashMap<>();
        for (int i = (int)Math.pow(10, LENGTH-1); i < (int)Math.pow(10, LENGTH); i++) {
            int number = i;
            int index = 0;
            int[] digits = new int[LENGTH];
            int length = LENGTH;
            while (number > 0) {
                digits[--length] = number % 10;
                number /= 10;
            }
            Combination combination = new Combination(digits);
            if (combination.isValid()) {
                combinations.add(combination);
            }
        }

//        System.out.println("Combinations count: " + combinations.size());
//        System.out.println("Combinations: " + combinations);
    }

    public Solution evaluate(Combination combination, int present, int correct) {
        statistics = new HashMap<>();
        combinations = combinations.stream()
                .filter(comb -> comb.match(combination, present, correct))
                .collect(Collectors.toSet());

//        System.out.println("Combinations count: " + combinations.size());
//        System.out.println("Combinations: " + combinations);

        combinations.forEach(
                comb -> statistics.put(comb,
                        combinations.stream().filter(c -> c.match(comb, present, correct))
                                .collect(Collectors.toSet()).size())
        );
        if (statistics.isEmpty()) {
            throw new IllegalArgumentException("Not valid data supplied.");
        }

        int maxValueInMap = (Collections.max(statistics.values()));
        for (Map.Entry<Combination, Integer> entry :
                statistics.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
                return new Solution(entry.getKey(), statistics.size() == 1);
            }
        }
        throw new IllegalArgumentException("Not valid data supplied.");
    }
}

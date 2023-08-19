package com.digits.game;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.digits.game.Combination.LENGTH;

public class Analyzer {

    private Set<Combination> combinations;
//    private Map<Combination, Integer> statistics;
    private Map<Digit, Integer> statistics;

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

        System.out.println("Combinations count: " + combinations.size());
        System.out.println("Combinations: " + combinations);
    }

    public Solution evaluate(Combination combination, int present, int correct) {
        statistics = new HashMap<>();
        combinations = combinations.stream()
                .filter(comb -> comb.match(combination, present, correct))
                .collect(Collectors.toSet());

        System.out.println("Combinations count: " + combinations.size());
        System.out.println("Combinations: " + combinations);

        combinations.forEach(
                comb -> {
//                    for (int i = 0; i < comb.getData().length; i++) {
//                        Digit digit = new Digit(comb.getData()[i], i);
//                        System.out.println(digit);
//                        statistics.merge(digit, 1, (k, v) -> v.incrementAndGet());
//                    }
                    for (int i = 0; i < comb.getData().length; i++) {
                        Digit digit = new Digit(comb.getData()[i], i);
                        if (statistics.containsKey(digit)) {
                            statistics.put(digit, statistics.get(digit) + 1);
                        } else {
                            statistics.put(digit, 1);
                        }
                    }
                }
        );

        List<Integer> vals = statistics.values().stream().collect(Collectors.toList());
        Collections.sort(vals);
        Set<Digit> digits = new HashSet<>();
        while (digits.size() < 4 && !vals.isEmpty()) {
            for (Map.Entry<Digit, Integer> entry : statistics.entrySet()) {
                if (entry.getValue() == vals.get(0)) {
                    boolean duplicate = false;
                    boolean occupied = false;
                    for (Digit digit: digits) {
                        if (digit.getDigit() == entry.getKey().getDigit()) {
                            duplicate = true;
                        }
                        if (digit.getIndex() == entry.getKey().getIndex()) {
                            occupied = true;
                        }
                    }
                    if (!duplicate && !occupied) {
                        digits.add(entry.getKey());
                    }
                }
            }
            vals.remove(0);
        }

        System.out.println(statistics);

        int [] result = new int[4];
        for (Digit digit: digits) {
            result[digit.getIndex()] = digit.getDigit();
        }

//        combinations.forEach(
//                comb -> statistics.put(comb,
//                        combinations.stream().filter(c -> c.match(comb, present, correct))
//                                .collect(Collectors.toSet()).size())
//        );
//        if (statistics.isEmpty()) {
//            throw new IllegalArgumentException("Not valid data supplied.");
//        }
//
//        int maxValueInMap = (Collections.max(statistics.values()));
//        System.out.println("maxValueInMap: " + maxValueInMap);
//        for (Map.Entry<Combination, Integer> entry :
//                statistics.entrySet()) {
//            if (entry.getValue() == maxValueInMap) {
//                return new Solution(entry.getKey(), statistics.size() == 1);
//            }
//        }
        if (combinations.isEmpty()) {
            throw new IllegalArgumentException("Not valid data supplied.");
        }
//        return new Solution(pickRandomCombination(), combinations.size() == 1);
        return new Solution(new Combination(result), combinations.size() == 1);
    }

    private Combination pickRandomCombination() {
        int index = (int)(Math.random() * combinations.size());
        return (Combination)combinations.toArray()[index];
    }
}

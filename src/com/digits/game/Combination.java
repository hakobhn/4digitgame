package com.digits.game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Combination {

    public static final int LENGTH = 4;
    private int[] data = new int[LENGTH];

    public Combination(int[] array) {
        if (array.length < LENGTH) {
            throw new IllegalArgumentException("Invalid combination passed.");
        }
        this.data = Arrays.copyOf(array, LENGTH);
    }

    public int[] getData() {
        return data;
    }

    public boolean isValid() {
        if (data.length < LENGTH) {
            return false;
        }
        if (data[0] == 0) {
            return false;
        }
        for (int i = 0; i < LENGTH; i++ ) {
            for (int j = i+1; j < LENGTH; j++ ) {
                if (data[i] == data[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean match(Combination combination, int present, int correct) {
        int curPresent = 0;
        int curCorrect = 0;
        int[] array = combination.data;
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                if (array[i] == data[j]) {
                    curPresent++;
                    if (i == j) {
                        curCorrect++;
                    }
                }
            }
        }
        return curCorrect == correct && curPresent == present;
    }

    public static int[] generate() {

        // random number generator
        Random randy = new Random();
        Set<Integer> combination = new HashSet<>();

        while (combination.size() < LENGTH) {
            combination.add(randy.nextInt(9));
        }
        int[] result = combination.stream()
                .mapToInt(Integer::intValue)
                .toArray();

        if (new Combination(result).isValid()) {
            return result;
        } else {
            return generate();
        }
    }

    @Override
    public String toString() {
        return "[" + IntStream.of(data).boxed().map(String::valueOf)
                .collect(Collectors.joining(",")) +']';
    }
}

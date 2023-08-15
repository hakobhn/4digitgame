package com.digits.game;

public class Solution {
    private final Combination combination;
    private final boolean isReady;

    public Solution(Combination combination, boolean isReady) {
        this.combination = combination;
        this.isReady = isReady;
    }

    public boolean isReady() {
        return isReady;
    }

    public Combination getCombination() {
        return combination;
    }
}

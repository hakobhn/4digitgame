package com.digits.game;

import java.util.Objects;

public class Digit {
    private final int digit;
    private final int index;

    public Digit(int digit, int index) {
        this.digit = digit;
        this.index = index;
    }

    public int getDigit() {
        return digit;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Digit digit1 = (Digit) o;
        return digit == digit1.digit && index == digit1.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(digit, index);
    }

    @Override
    public String toString() {
        return "{" + digit + ", " + index + "}";
    }
}

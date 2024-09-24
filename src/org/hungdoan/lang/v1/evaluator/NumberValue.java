package org.hungdoan.lang.v1.evaluator;

public class NumberValue extends RuntimeValue {

    private int value;

    public NumberValue(int value) {
        super(ValueType.NumberValue);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

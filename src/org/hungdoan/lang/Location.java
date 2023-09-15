package org.hungdoan.lang;

public class Location {

    private int offset;

    private int startLine;

    private int startColumn;

    public Location(int offset, int startLine, int startColumn) {
        this.offset = offset;
        this.startLine = startLine;
        this.startColumn = startColumn;
    }

    @Override
    public String toString() {
        return "{" +
                "offset=" + offset +
                ", startLine=" + startLine +
                ", startColumn=" + startColumn +
                '}';
    }
}

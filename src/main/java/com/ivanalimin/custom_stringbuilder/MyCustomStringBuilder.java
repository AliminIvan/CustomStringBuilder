package com.ivanalimin.custom_stringbuilder;

import java.util.Objects;
import java.util.Stack;

public final class MyCustomStringBuilder implements Comparable<MyCustomStringBuilder> {
    private char[] chars;
    private int size;
    private final Stack<Snapshot> snapshotHistory = new Stack<>();

    public MyCustomStringBuilder() {
        this(16);
    }

    public MyCustomStringBuilder(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity can`t be negative");
        }
        chars = new char[capacity];
        size = 0;
    }

    public MyCustomStringBuilder(String string) {
        if (string == null) {
            throw new NullPointerException("String can`t be null");
        }
        chars = new char[string.length()];
        size = string.length();
        string.getChars(0, size, chars, 0);
    }

    public MyCustomStringBuilder append(String string) {
        if (string == null) {
            string = "null";
        }
        saveSnapshot();
        int length = string.length();
        ensureCapacity(size + length);
        string.getChars(0, length, chars, size);
        size += length;
        return this;
    }

    public MyCustomStringBuilder append(char c) {
        saveSnapshot();
        ensureCapacity(size + 1);
        chars[size++] = c;
        return this;
    }

    public MyCustomStringBuilder insert(int offset, String string) {
        if (offset < 0 || offset > size) {
            throw new IndexOutOfBoundsException("Index: " + offset + ", Size: " + size);
        }
        if (string == null) {
            string = "null";
        }
        saveSnapshot();
        int length = string.length();
        ensureCapacity(size + length);
        System.arraycopy(chars, offset, chars, offset + length, size - offset);
        string.getChars(0, length, chars, offset);
        size += length;
        return this;
    }

    public MyCustomStringBuilder delete(int start, int end) {
        if (start < 0 || end > size || start > end) {
            throw new IndexOutOfBoundsException("Start: " + start + ", End: " + end + ", Size: " + size);
        }
        saveSnapshot();
        int length = end - start;
        System.arraycopy(chars, end, chars, start, size - end);
        size -= length;
        return this;
    }

    public MyCustomStringBuilder replace(int start, int end, String string) {
        if (start < 0 || end > size || start > end) {
            throw new IndexOutOfBoundsException("Start: " + start + ", End: " + end + ", Size: " + size);
        }
        if (string == null) {
            string = "null";
        }
        saveSnapshot();
        int length = string.length();
        int newSize = size - (end - start) + length;
        ensureCapacity(newSize);
        System.arraycopy(chars, end, chars, start + length, size - end);
        string.getChars(0, length, chars, start);
        size = newSize;
        return this;
    }

    public int length() {
        return size;
    }

    public char charAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return chars[index];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyCustomStringBuilder that = (MyCustomStringBuilder) o;
        return size == that.size && Objects.deepEquals(chars, that.chars);
    }

    @Override
    public int compareTo(MyCustomStringBuilder o) {
        int minLength = Math.min(size, o.size);
        for (int i = 0; i < minLength; i++) {
            if (chars[i] != o.chars[i]) {
                return chars[i] - o.chars[i];
            }
        }
        return size - o.size;
    }

    @Override
    public String toString() {
        return new String(chars, 0, size);
    }

    public void undo() {
        if (snapshotHistory.isEmpty()) {
            throw new IllegalArgumentException("No operation to undo");
        }
        Snapshot snapshot = snapshotHistory.pop();
        chars = snapshot.getChars();
        size = snapshot.getSize();
    }

    private void saveSnapshot() {
        snapshotHistory.push(new Snapshot(chars.clone(), size));
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > chars.length) {
            int newCapacity = Math.max(chars.length * 2, minCapacity);
            char[] newChars = new char[newCapacity];
            System.arraycopy(chars, 0, newChars, 0, size);
            chars = newChars;
        }
    }

    private static class Snapshot {
        private final char[] chars;
        private final int size;

        public Snapshot(char[] chars, int size) {
            this.chars = chars;
            this.size = size;
        }

        public char[] getChars() {
            return chars;
        }

        public int getSize() {
            return size;
        }
    }
}

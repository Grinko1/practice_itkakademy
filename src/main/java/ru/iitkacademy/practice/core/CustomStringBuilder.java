package ru.iitkacademy.practice.core;

import java.util.Stack;

public final class CustomStringBuilder implements Comparable<CustomStringBuilder> {
    private char[] value;
    private int size;
    private Stack<String> history;

    public CustomStringBuilder() {
        this.value = new char[16];
        this.size = 0;
        this.history = new Stack<>();
        saveState();
    }

    public CustomStringBuilder append(char c) {
        saveState();
        ensureCapacity(size + 1);
        value[size++] = c;

        return this;
    }

    public CustomStringBuilder append(String str) {
        saveState();
        ensureCapacity(size + str.length());
        for (int i = 0; i < str.length(); i++) {
            value[size++] = str.charAt(i);
        }

        return this;
    }
    public CustomStringBuilder append(int i) {
        return append(String.valueOf(i));
    }

    public CustomStringBuilder append(boolean b) {
        return append(String.valueOf(b));
    }
    public CustomStringBuilder append(long l) {
        return append(String.valueOf(l));
    }
    public CustomStringBuilder append(float f) {
        return append(String.valueOf(f));
    }
    public CustomStringBuilder append(char[] chars) {
        saveState();
        ensureCapacity(size + chars.length);
        for (char c : chars) {
            value[size++] = c;
        }

        return this;
    }

    public CustomStringBuilder append(Object obj) {
        return append(String.valueOf(obj));
    }


    public CustomStringBuilder insert(int index, String str) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        saveState();
        ensureCapacity(size + str.length());

        System.arraycopy(value, index, value, index + str.length(), size - index);

        for (int i = 0; i < str.length(); i++) {
            value[index + i] = str.charAt(i);
        }
        size += str.length();

        return this;
    }

    public CustomStringBuilder insert(int index, char c) {
        return insert(index, String.valueOf(c));
    }

    public CustomStringBuilder insert(int index, int i) {
        return insert(index, String.valueOf(i));
    }

    public CustomStringBuilder insert(int index, boolean b) {
        return insert(index, String.valueOf(b));
    }

    public CustomStringBuilder insert(int index, long l) {
        return insert(index, String.valueOf(l));
    }

    public CustomStringBuilder insert(int index, float f) {
        return insert(index, String.valueOf(f));
    }

    public CustomStringBuilder insert(int index, char[] chars) {
        return insert(index, new String(chars));
    }

    public CustomStringBuilder insert(int index, Object obj) {
        return insert(index, String.valueOf(obj));
    }
    public CustomStringBuilder delete(int start, int end) {
        if (start < 0 || end > size || start > end) {
            throw new IndexOutOfBoundsException("Invalid range: " + start + " to " + end);
        }
        saveState();
        System.arraycopy(value, end, value, start, size - end);
        size -= (end - start);

        return this;
    }

    public CustomStringBuilder deleteCharAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        saveState();
        System.arraycopy(value, index + 1, value, index, size - index - 1);
        size--;

        return this;
    }
    public CustomStringBuilder replace(int start, int end, String str) {
        if (start < 0 || end > size || start > end) {
            throw new IndexOutOfBoundsException("Invalid range: " + start + " to " + end);
        }
        saveState();
        delete(start, end);
        insert(start, str);

        return this;
    }
    public int indexOf(char c) {
        for (int i = 0; i < size; i++) {
            if (value[i] == c) {
                return i;
            }
        }
        return -1;
    }

    public int indexOf(String str) {
        if (str == null || str.isEmpty()) {
            return -1;
        }
        for (int i = 0; i <= size - str.length(); i++) {
            boolean match = true;
            for (int j = 0; j < str.length(); j++) {
                if (value[i + j] != str.charAt(j)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return i;
            }
        }
        return -1;
    }
    public String toString() {
        return new String(value, 0, size);
    }

    public CustomStringBuilder undo() {
        if (!history.isEmpty()) {
            String previousState = history.pop();
            this.value = previousState.toCharArray();
            this.size = previousState.length();
        }
        return this;
    }
    @Override
    public int compareTo(CustomStringBuilder o) {
        return  this.toString().compareTo(o.toString());
    }
    private void ensureCapacity(int capacity) {
        if (capacity > value.length) {
            int newCapacity = value.length * 2;
            while (newCapacity < capacity) {
                newCapacity *= 2;
            }
            char[] newValue = new char[newCapacity];
            System.arraycopy(value, 0, newValue, 0, size);
            value = newValue;
        }
    }

    private void saveState() {
        history.push(new String(value, 0, size));
    }

    public static void main(String[] args) {
        CustomStringBuilder builder = new CustomStringBuilder();
        builder.append("My").append(" String").append("Builder")
                .append(123);
        System.out.println(builder.toString());
        builder.insert(16, ",");
        System.out.println(builder.toString());

        builder.insert(0, "Start - ");
        System.out.println(builder.toString());


        builder.undo();
        System.out.println(builder.toString());
        builder.undo();
        System.out.println(builder.toString());


    }


}

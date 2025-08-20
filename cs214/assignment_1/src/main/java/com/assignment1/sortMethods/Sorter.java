package com.assignment1.sortMethods;

import java.util.List;

public interface Sorter<T extends Comparable<T>> {
    void sort(List<T> list);
}


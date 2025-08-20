package com.assignment1.sortMethods;

import java.util.Collections;
import java.util.List;

public class BuiltInSort<T extends Comparable<T>> implements Sorter<T> {
    @Override
    public void sort(List<T> list) {
        Collections.sort(list);
    }
}

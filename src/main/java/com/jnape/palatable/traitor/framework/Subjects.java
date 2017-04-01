package com.jnape.palatable.traitor.framework;

import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;

public final class Subjects<T> implements Iterable<T> {
    private final List<T> testSubjects;

    private Subjects(List<T> testSubjects) {
        this.testSubjects = testSubjects;
    }

    @Override
    public Iterator<T> iterator() {
        return testSubjects.iterator();
    }

    @SafeVarargs
    public static <T> Subjects<T> subjects(T... testSubjects) {
        return new Subjects<>(asList(testSubjects));
    }
}

package com.jnape.palatable.traitor.traits;

public interface ComposedTrait<Subject> extends Trait<Subject> {

    @Override
    default void test(Subject subject) {
    }
}

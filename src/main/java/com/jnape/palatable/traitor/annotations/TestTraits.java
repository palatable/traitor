package com.jnape.palatable.traitor.annotations;

import com.jnape.palatable.traitor.traits.Trait;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
public @interface TestTraits {
    Class<? extends Trait>[] value();
}

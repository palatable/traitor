package com.jnape.palatable.traitor.framework.exception;

import com.jnape.palatable.traitor.traits.Trait;

public class TraitFrameworkMethodSynthesizationException extends RuntimeException {

    public TraitFrameworkMethodSynthesizationException(Class<? extends Trait> traitClass, Throwable cause) {
        super("", cause);
    }
}

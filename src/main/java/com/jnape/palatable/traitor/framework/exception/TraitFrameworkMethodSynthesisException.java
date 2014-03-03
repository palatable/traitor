package com.jnape.palatable.traitor.framework.exception;

import com.jnape.palatable.traitor.traits.Trait;

import static java.lang.String.format;

public class TraitFrameworkMethodSynthesisException extends RuntimeException {

    public TraitFrameworkMethodSynthesisException(Class<? extends Trait> traitClass, Throwable cause) {
        super(format("Could not synthesize TraitFrameworkMethod for Trait class %s", traitClass.getSimpleName()), cause);
    }
}

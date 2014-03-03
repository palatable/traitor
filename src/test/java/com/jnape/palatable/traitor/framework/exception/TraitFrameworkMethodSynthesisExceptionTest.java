package com.jnape.palatable.traitor.framework.exception;

import org.hamcrest.core.Is;
import org.junit.Test;
import testsupport.fixture.traits.NonEmpty;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TraitFrameworkMethodSynthesisExceptionTest {

    @Test
    public void errorMessageIsUseful() {
        TraitFrameworkMethodSynthesisException exception = new TraitFrameworkMethodSynthesisException(
                NonEmpty.class,
                new RuntimeException("Something went wrong.")
        );

        assertThat(exception.getMessage(), is("Could not synthesize TraitFrameworkMethod for Trait class NonEmpty"));
    }

    @Test
    public void remembersCause() {
        RuntimeException cause = new RuntimeException("Something went wrong.");
        TraitFrameworkMethodSynthesisException exception = new TraitFrameworkMethodSynthesisException(
                NonEmpty.class,
                cause
        );

        assertThat(exception.getCause(), Is.<Throwable>is(cause));
    }
}

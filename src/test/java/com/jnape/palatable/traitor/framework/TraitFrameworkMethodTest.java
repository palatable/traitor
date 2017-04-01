package com.jnape.palatable.traitor.framework;

import com.jnape.palatable.traitor.framework.exception.TraitFrameworkMethodSynthesisException;
import org.junit.Test;
import testsupport.fixture.traits.NonEmpty;

import java.lang.reflect.Method;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class TraitFrameworkMethodTest {

    @Test
    public void equalsOtherWithSameMethodAndTraitTestSubjectCreationMethod() throws Exception {
        Method toString = Object.class.getDeclaredMethod("toString");
        Method finalize = Object.class.getDeclaredMethod("finalize");

        Object testSubject = new Object();

        TraitFrameworkMethod nonEmpty = new TraitFrameworkMethod(toString, testSubject);

        assertThat(nonEmpty, is(new TraitFrameworkMethod(toString, testSubject)));
        assertThat(nonEmpty, is(not(new TraitFrameworkMethod(finalize, testSubject))));
    }

    @Test
    public void creatableFromTraitClassAndTraitTestSubjectCreationMethodAndTestSubject() throws Exception {
        Object testSubject = new Object();
        Method testMethod = NonEmpty.class.getDeclaredMethod("test", Object.class);

        TraitFrameworkMethod nonEmpty = new TraitFrameworkMethod(testMethod, testSubject);
        assertThat(nonEmpty, is(TraitFrameworkMethod.synthesize(NonEmpty.class, testSubject)));
    }

    @Test(expected = TraitFrameworkMethodSynthesisException.class)
    @SuppressWarnings("unchecked")
    public void failsIfTraitTestMethodRetrievalFails() throws NoSuchMethodException {
        TraitFrameworkMethod.synthesize((Class) Object.class, null);
    }
}

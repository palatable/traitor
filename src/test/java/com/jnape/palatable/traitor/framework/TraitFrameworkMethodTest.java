package com.jnape.palatable.traitor.framework;

import com.jnape.palatable.traitor.framework.exception.TraitFrameworkMethodSynthesisException;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import testsupport.fixture.traits.NonEmpty;

import java.lang.reflect.Method;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class TraitFrameworkMethodTest {

    @Test
    public void equalsOtherWithSameMethodAndTraitTestSubjectCreationMethod() throws Exception {
        Method wait = Object.class.getDeclaredMethod("wait");
        Method toString = Object.class.getDeclaredMethod("toString");
        Method finalize = Object.class.getDeclaredMethod("finalize");

        FrameworkMethod traitTestSubjectCreationMethod = new FrameworkMethod(wait);
        FrameworkMethod differentTraitTestSubjectCreationMethod = new FrameworkMethod(toString);

        Object testSubject = new Object();

        TraitFrameworkMethod nonEmpty = new TraitFrameworkMethod(traitTestSubjectCreationMethod, toString, testSubject);

        assertThat(nonEmpty, is(new TraitFrameworkMethod(traitTestSubjectCreationMethod, toString, testSubject)));
        assertThat(nonEmpty, is(not(new TraitFrameworkMethod(differentTraitTestSubjectCreationMethod, toString, testSubject))));
        assertThat(nonEmpty, is(not(new TraitFrameworkMethod(traitTestSubjectCreationMethod, finalize, testSubject))));
        assertThat(nonEmpty, is(not(new TraitFrameworkMethod(differentTraitTestSubjectCreationMethod, finalize, testSubject))));
    }

    @Test
    public void creatableFromTraitClassAndTraitTestSubjectCreationMethodAndTestSubject() throws Exception {
        Object testSubject = new Object();
        Method testMethod = NonEmpty.class.getDeclaredMethod("test", Object.class);

        Method wait = Object.class.getDeclaredMethod("wait");
        FrameworkMethod traitTestSubjectCreationMethod = new FrameworkMethod(wait);

        TraitFrameworkMethod nonEmpty = new TraitFrameworkMethod(traitTestSubjectCreationMethod, testMethod, testSubject);
        assertThat(nonEmpty, is(TraitFrameworkMethod.synthesize(NonEmpty.class, traitTestSubjectCreationMethod, testSubject)));
    }

    @Test(expected = TraitFrameworkMethodSynthesisException.class)
    @SuppressWarnings("unchecked")
    public void failsIfTraitTestMethodRetrievalFails() throws NoSuchMethodException {
        Method wait = Object.class.getDeclaredMethod("wait");
        FrameworkMethod traitTestSubjectCreationMethod = new FrameworkMethod(wait);

        TraitFrameworkMethod.synthesize((Class) Object.class, traitTestSubjectCreationMethod, null);
    }
}

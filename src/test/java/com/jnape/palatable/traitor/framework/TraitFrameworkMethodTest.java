package com.jnape.palatable.traitor.framework;

import com.jnape.palatable.traitor.framework.exception.TraitFrameworkMethodSynthesisException;
import com.jnape.palatable.traitor.traits.Trait;
import org.junit.Test;
import testsupport.fixture.traits.NonEmpty;

import java.lang.reflect.Method;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TraitFrameworkMethodTest {

    @Test
    public void equalsOtherWithSameMethodAndTraitTestSubjectCreationMethod() throws Exception {
        Method toString = Object.class.getDeclaredMethod("toString");
        Method finalize = Object.class.getDeclaredMethod("finalize");

        Object testSubject = new Object();

        TraitFrameworkMethod nonEmpty = new TraitFrameworkMethod(toString, Object.class, testSubject);

        assertThat(nonEmpty, is(new TraitFrameworkMethod(toString, Object.class, testSubject)));
        assertThat(nonEmpty, is(not(new TraitFrameworkMethod(finalize, Object.class, testSubject))));
    }

    @Test
    @SuppressWarnings("JavaReflectionMemberAccess")
    public void creatableFromTraitClassAndTraitTestSubjectCreationMethodAndTestSubject() throws Exception {
        Object testSubject = new Object();
        Method testMethod  = NonEmpty.class.getDeclaredMethod("test", Object.class);

        TraitFrameworkMethod nonEmpty = new TraitFrameworkMethod(testMethod, NonEmpty.class, testSubject);
        assertThat(nonEmpty, is(TraitFrameworkMethod.synthesize(NonEmpty.class, testSubject)));
    }

    @Test(expected = TraitFrameworkMethodSynthesisException.class)
    @SuppressWarnings("unchecked")
    public void failsIfTraitTestMethodRetrievalFails() {
        TraitFrameworkMethod.synthesize((Class) Object.class, null);
    }

    @Test
    public void worksWithInheritedTestMethod() throws Throwable {
        TraitFrameworkMethod inherited = TraitFrameworkMethod.synthesize(Bar.class, new Object());
        assertEquals("Trait: Bar", inherited.getName());
        inherited.invokeExplosively(new Object());
    }

    static abstract class Foo implements Trait<Object> {
        @Override
        public void test(Object o) {
        }
    }

    @SuppressWarnings("WeakerAccess")
    static class Bar extends Foo {
    }
}

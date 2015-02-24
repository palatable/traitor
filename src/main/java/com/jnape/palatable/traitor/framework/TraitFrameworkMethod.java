package com.jnape.palatable.traitor.framework;

import com.jnape.palatable.traitor.framework.exception.TraitFrameworkMethodSynthesisException;
import com.jnape.palatable.traitor.traits.Trait;
import org.junit.runners.model.FrameworkMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.String.format;

public class TraitFrameworkMethod extends FrameworkMethod {

    public static final String TEST_METHOD_NAME = "test";

    private final FrameworkMethod traitTestSubjectCreationMethod;
    private final Object          testSubject;

    public TraitFrameworkMethod(FrameworkMethod traitTestSubjectCreationMethod, Method method, Object testSubject) {
        super(method);
        this.traitTestSubjectCreationMethod = traitTestSubjectCreationMethod;
        this.testSubject = testSubject;
    }

    @Override
    public Object invokeExplosively(Object target, Object... params) throws Throwable {
        try {
            return getMethod().invoke(getMethod().getDeclaringClass().newInstance(), testSubject);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }

    @Override
    public String getName() {
        return format("Trait: %s", getMethod().getDeclaringClass().getSimpleName());
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof TraitFrameworkMethod) {
            TraitFrameworkMethod that = (TraitFrameworkMethod) other;
            return super.equals(other) && this.traitTestSubjectCreationMethod.equals(that.traitTestSubjectCreationMethod);
        }
        return false;
    }

    public static TraitFrameworkMethod synthesize(Class<? extends Trait> traitClass,
                                                  FrameworkMethod traitTestSubjectCreationMethod,
                                                  Object testSubject) throws TraitFrameworkMethodSynthesisException {
        try {
            return new TraitFrameworkMethod(traitTestSubjectCreationMethod, traitClass.getDeclaredMethod(TEST_METHOD_NAME, Object.class), testSubject);
        } catch (Exception e) {
            throw new TraitFrameworkMethodSynthesisException(traitClass, e);
        }
    }
}

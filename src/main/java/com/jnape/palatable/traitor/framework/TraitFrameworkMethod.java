package com.jnape.palatable.traitor.framework;

import com.jnape.palatable.traitor.framework.exception.TraitFrameworkMethodSynthesisException;
import com.jnape.palatable.traitor.traits.Trait;
import org.junit.runners.model.FrameworkMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.jnape.palatable.traitor.framework.Subjects.subjects;
import static java.lang.String.format;

public class TraitFrameworkMethod extends FrameworkMethod {

    private static final String TEST_METHOD_NAME = "test";

    private final Object testSubject;

    TraitFrameworkMethod(Method method, Object testSubject) {
        super(method);
        this.testSubject = testSubject;
    }

    @Override
    public Object invokeExplosively(Object target, Object... params) throws Throwable {
        try {
            Method method = getMethod();
            Class<?> declaringClass = method.getDeclaringClass();
            Subjects subjects = testSubject instanceof Subjects
                    ? (Subjects) testSubject
                    : subjects(testSubject);
            for (Object subject : subjects) {
                method.invoke(declaringClass.newInstance(), subject);
            }
            return null;
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
        return other instanceof TraitFrameworkMethod && super.equals(other);
    }

    public static TraitFrameworkMethod synthesize(Class<? extends Trait> traitClass,
                                                  Object testSubject) throws TraitFrameworkMethodSynthesisException {
        try {
            return new TraitFrameworkMethod(traitClass.getDeclaredMethod(TEST_METHOD_NAME, Object.class), testSubject);
        } catch (Exception e) {
            throw new TraitFrameworkMethodSynthesisException(traitClass, e);
        }
    }
}

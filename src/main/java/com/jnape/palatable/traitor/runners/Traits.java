package com.jnape.palatable.traitor.runners;

import com.jnape.palatable.traitor.annotations.TestTraits;
import com.jnape.palatable.traitor.framework.TraitFrameworkMethod;
import com.jnape.palatable.traitor.traits.Trait;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.util.ArrayList;
import java.util.List;

public class Traits extends BlockJUnit4ClassRunner {

    public Traits(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        List<FrameworkMethod> testMethods = new ArrayList<>(super.computeTestMethods());
        addTraitTestingMethods(testMethods);
        return testMethods;
    }

    private void addTraitTestingMethods(List<FrameworkMethod> testMethods) {
        List<FrameworkMethod> traitTestSubjectCreationMethods = getTestClass().getAnnotatedMethods(TestTraits.class);
        for (FrameworkMethod traitTestSubjectCreationMethod : traitTestSubjectCreationMethods)
            try {
                Object testSubject = traitTestSubjectCreationMethod.invokeExplosively(createTest());
                for (Class<? extends Trait> traitClass : traitTestSubjectCreationMethod.getAnnotation(TestTraits.class).value()) {
                    TraitFrameworkMethod traitFrameworkMethod = TraitFrameworkMethod.synthesize(traitClass, testSubject);
                    testMethods.remove(traitFrameworkMethod);
                    testMethods.add(traitFrameworkMethod);
                }
            } catch (Throwable t) {
                t.printStackTrace();
                throw new AssertionError("Couldn't create a test subject");
            }
    }

}

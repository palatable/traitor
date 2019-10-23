package com.jnape.palatable.traitor.runners;

import com.jnape.palatable.traitor.annotations.TestTraits;
import com.jnape.palatable.traitor.framework.TraitFrameworkMethod;
import com.jnape.palatable.traitor.traits.Trait;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

public class Traits extends BlockJUnit4ClassRunner {

    private final List<FrameworkMethod> computedTestMethods;

    public Traits(Class<?> testClass) throws InitializationError {
        super(testClass);
        this.computedTestMethods = new ArrayList<>();
    }

    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        List<FrameworkMethod> testMethods = new ArrayList<>(super.computeTestMethods());
        addTraitTestingMethods(testMethods);
        return testMethods;
    }

    private void addTraitTestingMethods(List<FrameworkMethod> testMethods) {
        List<FrameworkMethod> traitAnnotatedMethods = getTestClass().getAnnotatedMethods(TestTraits.class);
        for (FrameworkMethod traitAnnotatedMethod : traitAnnotatedMethods)
            try {
                Object                   testSubject = traitAnnotatedMethod.invokeExplosively(createTest());
                Class<? extends Trait>[] traits      = traitAnnotatedMethod.getAnnotation(TestTraits.class).value();
                testMethods.addAll(synthesizeTraitFrameworkMethods(new LinkedList<>(asList(traits)), testSubject));
            } catch (Throwable t) {
                t.printStackTrace();
                throw new AssertionError("Couldn't create a test subject");
            }
    }

    private static Set<TraitFrameworkMethod> synthesizeTraitFrameworkMethods(
            LinkedList<Class<? extends Trait>> traitClasses, Object testSubject) {
        LinkedHashSet<TraitFrameworkMethod> traitFrameworkMethods = new LinkedHashSet<>();
        while (!traitClasses.isEmpty()) {
            Class<? extends Trait> traitClass = traitClasses.poll();
            traitFrameworkMethods.add(TraitFrameworkMethod.synthesize(traitClass, testSubject));
            TestTraits testTraits = traitClass.getAnnotation(TestTraits.class);
            if (testTraits != null) {
                traitClasses.addAll(0, asList(testTraits.value()));
            }
        }
        return traitFrameworkMethods;
    }
}

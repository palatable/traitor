package com.jnape.palatable.traitor.runners;

import com.jnape.palatable.traitor.framework.TraitFrameworkMethod;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.mockito.InOrder;
import testsupport.fixture.suites.TestSuiteWithNoMethods;
import testsupport.fixture.suites.TestSuiteWithOneTestMethodAndOneTraitMethod;
import testsupport.fixture.suites.TestSuiteWithSingleTestMethod;
import testsupport.fixture.suites.TestSuiteWithSingleTraitMethod;
import testsupport.fixture.traits.Evens;
import testsupport.fixture.traits.NonEmpty;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

public class TestAgainstTraitsTest {

    @Test
    public void passesInitializationIfOneTestMethodAndNoTraitMethods() throws InitializationError {
        new Traits(TestSuiteWithSingleTestMethod.class);
    }

    @Test
    public void passesInitializationIfOneTraitMethodAndNoTestMethods() throws InitializationError {
        new Traits(TestSuiteWithSingleTraitMethod.class);
    }

    @Test
    public void passesInitializationIfOneTestMethodAndOneTraitMethod() throws InitializationError {
        new Traits(TestSuiteWithOneTestMethodAndOneTraitMethod.class);
    }

    @Test(expected = InitializationError.class)
    public void failsInitializationIfNoTestMethodsAndNoTraitMethods() throws InitializationError {
        new Traits(TestSuiteWithNoMethods.class);
    }

    @Test
    public void runsTraitMethodsAfterTestMethods() throws InitializationError {
        RunNotifier notifier = mock(RunNotifier.class);
        Class<TestSuiteWithOneTestMethodAndOneTraitMethod> testClass = TestSuiteWithOneTestMethodAndOneTraitMethod.class;

        new Traits(testClass).run(notifier);

        InOrder inOrder = inOrder(notifier);

        Description testMethodDescription = Description.createTestDescription(testClass, "justAnotherTestCase");
        inOrder.verify(notifier).fireTestStarted(testMethodDescription);
        inOrder.verify(notifier).fireTestFinished(testMethodDescription);

        Description nonEmptyTraitMethodDescription = Description.createTestDescription(testClass, "Trait: NonEmpty");
        inOrder.verify(notifier).fireTestStarted(nonEmptyTraitMethodDescription);
        inOrder.verify(notifier).fireTestFinished(nonEmptyTraitMethodDescription);

        Description evensTraitMethodDescription = Description.createTestDescription(testClass, "Trait: Evens");
        inOrder.verify(notifier).fireTestStarted(evensTraitMethodDescription);
        inOrder.verify(notifier).fireTestFinished(evensTraitMethodDescription);

        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void includesTraitMethodsInComputedTestMethods() throws Exception {
        List<Integer> testSubject = asList(2, 4, 6, 8, 10);
        List<FrameworkMethod> computedTestMethods = new Traits(TestSuiteWithOneTestMethodAndOneTraitMethod.class).computeTestMethods();

        assertThat(
                computedTestMethods,
                CoreMatchers.<FrameworkMethod>hasItems(
                        TraitFrameworkMethod.forClass(NonEmpty.class, testSubject),
                        TraitFrameworkMethod.forClass(Evens.class, testSubject)
                )
        );
    }
}

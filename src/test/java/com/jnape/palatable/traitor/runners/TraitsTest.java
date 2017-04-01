package com.jnape.palatable.traitor.runners;

import com.jnape.palatable.traitor.framework.TraitFrameworkMethod;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.mockito.InOrder;
import testsupport.fixture.suites.TestSuiteWithNoMethods;
import testsupport.fixture.suites.TestSuiteWithOneTestMethodAndOneTraitMethod;
import testsupport.fixture.suites.TestSuiteWithOneTraitMethodForMultipleSubjects;
import testsupport.fixture.suites.TestSuiteWithOneTraitMethodForReferentialEqualityTestSubject;
import testsupport.fixture.suites.TestSuiteWithSingleTestMethod;
import testsupport.fixture.suites.TestSuiteWithSingleTraitMethod;
import testsupport.fixture.traits.Evens;
import testsupport.fixture.traits.NonEmpty;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

public class TraitsTest {

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
        Traits traits = new Traits(TestSuiteWithOneTestMethodAndOneTraitMethod.class);

        List<Integer> testSubject = asList(2, 4, 6, 8, 10);

        assertThat(
                traits.computeTestMethods(),
                hasItems(TraitFrameworkMethod.synthesize(NonEmpty.class, testSubject),
                         TraitFrameworkMethod.synthesize(Evens.class, testSubject))
        );
    }

    @Test
    public void doesNotRelyOnValueEqualityOfTestSubjectToDeDuplicateTraitFrameworkMethods() throws InitializationError {
        Traits traits = new Traits(TestSuiteWithOneTraitMethodForReferentialEqualityTestSubject.class);
        traits.computeTestMethods();
        traits.computeTestMethods();

        assertThat(traits.computeTestMethods().size(), is(2));
    }

    @Test
    public void supportsMultipleTestSubjectsPerTraitMethodViaSubjects() throws InitializationError {
        Traits traits = new Traits(TestSuiteWithOneTraitMethodForMultipleSubjects.class);
        AtomicInteger testsRun = new AtomicInteger(0);
        traits.run(new RunNotifier() {{
            addListener(new RunListener() {
                @Override
                public void testFailure(Failure failure) throws Exception {
                    fail(failure.getMessage());
                }

                @Override
                public void testFinished(Description description) throws Exception {
                    testsRun.incrementAndGet();
                    super.testFinished(description);
                }
            });
        }});

        assertEquals(2, testsRun.get());
    }
}

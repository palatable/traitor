package testsupport.fixture.suites;

import com.jnape.palatable.traitor.annotations.TestTraits;
import org.junit.Test;
import testsupport.fixture.traits.Evens;
import testsupport.fixture.traits.NonEmpty;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestSuiteWithOneTestMethodAndOneTraitMethod {

    @SuppressWarnings("UnusedDeclaration")
    @TestTraits({NonEmpty.class, Evens.class})
    public List<Integer> createTestSubject() {
        return asList(2, 4, 6, 8, 10);
    }

    @Test
    public void justAnotherTestCase() {
        assertThat(true, is(true));
    }
}

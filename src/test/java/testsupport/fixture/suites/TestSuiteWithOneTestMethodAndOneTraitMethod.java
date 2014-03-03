package testsupport.fixture.suites;

import com.jnape.palatable.traitor.annotations.Traits;
import com.jnape.palatable.traitor.runners.TraitsRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import testsupport.fixture.traits.Evens;
import testsupport.fixture.traits.NonEmpty;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(TraitsRunner.class)
public class TestSuiteWithOneTestMethodAndOneTraitMethod {

    @Traits({NonEmpty.class, Evens.class})
    public List<Integer> createTestSubject() {
        return asList(2, 4, 6, 8, 10);
    }

    @Test
    public void justAnotherTestCase() {
        assertThat(true, is(true));
    }
}

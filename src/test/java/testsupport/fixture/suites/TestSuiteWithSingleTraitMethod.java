package testsupport.fixture.suites;

import com.jnape.palatable.traitor.annotations.TestTraits;
import testsupport.fixture.traits.Evens;
import testsupport.fixture.traits.NonEmpty;

import java.util.List;

import static java.util.Arrays.asList;

public class TestSuiteWithSingleTraitMethod {

    @SuppressWarnings("UnusedDeclaration")
    @TestTraits({NonEmpty.class, Evens.class})
    public List<Integer> createTestSubject() {
        return asList(2, 4, 6, 8, 10);
    }
}

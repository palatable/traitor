package testsupport.fixture.suites;

import com.jnape.palatable.traitor.annotations.TestTraits;
import testsupport.fixture.traits.Evens;
import testsupport.fixture.traits.NonEmpty;

import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

public class TestSuiteWithOneTraitMethodForReferentialEqualityTestSubject {

    @TestTraits({NonEmpty.class, Evens.class})
    public List<Integer> createTestSubject() {
        return asList(new Random().nextInt());
    }
}

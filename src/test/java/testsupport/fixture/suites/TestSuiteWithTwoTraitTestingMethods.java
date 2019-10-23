package testsupport.fixture.suites;

import com.jnape.palatable.traitor.annotations.TestTraits;
import testsupport.fixture.traits.Evens;
import testsupport.fixture.traits.NonEmpty;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@SuppressWarnings("unused")
public class TestSuiteWithTwoTraitTestingMethods {

    @TestTraits({Evens.class})
    public List<Integer> createEvensTestSubject() {
        return emptyList();
    }

    @TestTraits({NonEmpty.class})
    public List<Integer> createNonEmptyTestSubject() {
        return singletonList(1);
    }
}

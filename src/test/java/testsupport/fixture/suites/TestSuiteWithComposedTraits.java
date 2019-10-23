package testsupport.fixture.suites;

import com.jnape.palatable.traitor.annotations.TestTraits;
import com.jnape.palatable.traitor.traits.ComposedTrait;
import testsupport.fixture.traits.Evens;
import testsupport.fixture.traits.NonEmpty;

import java.util.List;

import static java.util.Collections.singletonList;

@SuppressWarnings("unused")
public class TestSuiteWithComposedTraits {

    @TestTraits({MyTraits.class, DuplicateTraits.class})
    public List<Integer> testSubject() {
        return singletonList(2);
    }

    @TestTraits({Evens.class, MoreTraits.class})
    public static final class MyTraits implements ComposedTrait<List<Integer>> {
    }

    @TestTraits(NonEmpty.class)
    public static final class MoreTraits implements ComposedTrait<List<Integer>> {
    }

    @TestTraits(NonEmpty.class)
    public static final class DuplicateTraits implements ComposedTrait<List<Integer>> {
    }
}

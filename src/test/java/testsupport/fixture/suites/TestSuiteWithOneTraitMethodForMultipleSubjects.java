package testsupport.fixture.suites;

import com.jnape.palatable.traitor.annotations.TestTraits;
import com.jnape.palatable.traitor.framework.Subjects;
import testsupport.fixture.traits.Evens;
import testsupport.fixture.traits.NonEmpty;

import java.util.List;

import static com.jnape.palatable.traitor.framework.Subjects.subjects;
import static java.util.Collections.singletonList;

public class TestSuiteWithOneTraitMethodForMultipleSubjects {

    @TestTraits({NonEmpty.class, Evens.class})
    public Subjects<List<Integer>> createTestSubjects() {
        return subjects(singletonList(4), singletonList(2));
    }
}

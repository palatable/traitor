package testsupport.fixture.suites;

import com.jnape.palatable.traitor.annotations.TestTraits;
import com.jnape.palatable.traitor.traits.Trait;

import java.util.List;

import static java.util.Collections.emptyList;

@SuppressWarnings("unused")
public class TestSuiteTwoInheritedTraitMethods {

    @TestTraits({Foo.class, Bar.class})
    public List<Integer> createTestSubject() {
        return emptyList();
    }

    interface InheritedTrait extends Trait<Object> {
        @Override
        default void test(Object o) {
        }
    }

    @SuppressWarnings("WeakerAccess")
    static class Foo implements InheritedTrait {
    }

    @SuppressWarnings("WeakerAccess")
    static class Bar implements InheritedTrait {
    }
}

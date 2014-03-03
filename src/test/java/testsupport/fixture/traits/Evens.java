package testsupport.fixture.traits;

import com.jnape.palatable.traitor.traits.Trait;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static testsupport.matchers.EvenMatcher.even;

public class Evens implements Trait<List<Integer>> {

    @Override
    public void test(List<Integer> integers) {
        for (Integer integer : integers)
            assertThat(integer, is(even()));
    }
}

package testsupport.fixture.traits;

import com.jnape.palatable.traitor.traits.Trait;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class NonEmpty implements Trait<List> {

    @Override
    public void test(List list) {
        assertThat(list.isEmpty(), is(false));
    }
}

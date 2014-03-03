package testsupport.fixture.suites;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestSuiteWithSingleTestMethod {

    @Test
    public void justAnotherTestCase() {
        assertThat(true, is(true));
    }
}

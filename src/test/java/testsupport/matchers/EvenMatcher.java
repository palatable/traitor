package testsupport.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class EvenMatcher extends BaseMatcher<Integer> {

    @Override
    public boolean matches(Object item) {
        return item instanceof Integer && ((Integer) item) % 2 == 0;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("even");
    }

    public static EvenMatcher even() {
        return new EvenMatcher();
    }
}

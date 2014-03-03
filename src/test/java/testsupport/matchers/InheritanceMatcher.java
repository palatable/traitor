package testsupport.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import static java.lang.String.format;

public class InheritanceMatcher extends BaseMatcher<Class> {

    private final Class<?> parent;

    public InheritanceMatcher(Class parent) {
        this.parent = parent;
    }

    @Override
    public boolean matches(Object subject) {
        return subject instanceof Class && parent.isAssignableFrom((Class) subject);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(format("assignable from class <%s>", parent.getName()));
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        description.appendText("was not");
    }

    public static InheritanceMatcher assignableFrom(Class parent) {
        return new InheritanceMatcher(parent);
    }
}

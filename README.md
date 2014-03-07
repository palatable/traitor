traitor [![Build Status](https://travis-ci.org/palatable/traitor.png?branch=master)](https://travis-ci.org/palatable/traitor)
=======

Trait testing with JUnit.

installation
------------

Add the following dependency to your `pom.xml` file:

```xml
<dependency>
    <groupId>com.jnape.palatable</groupId>
    <artifactId>traitor</artifactId>
    <version>1.0</version>
</dependency>
```

usage
-----

An example test suite that tests traits alongside unit tests might look like:

```Java
@RunWith(Traits.class)
public class FunctionalIterableTest {

    @Test
    public void mapsFunctionOverValues() {
        //...
    }

    @TestTraits({Laziness.class, StandardIteration.class})
    public FunctionalIterable createTestSubject() {
        return FunctionalIterable.iterable(1, 2, 3, 4, 5);
    }
}
```

```Java
public class Laziness implements Trait<FunctionalIterable> {

    @Override
    public void test(FunctionalIterable testSubject) {
        //use normal JUnit asserts here
    }

    //same goes for StandardIteration
}
```

Note that the Traits runner simply requires the existence of at least @Test method or @TestTrait method in your test suite.

running
-------

Traitor can be run the same way you run JUnit - typically in your IDE or using your build tool (Maven, Gradle, Ant, etc.). Any test suites that use the `Traits` runner will automatically work.

license
-------

_traitor_ is part of [palatable](http://www.github.com/palatable), which is distributed under [The MIT License](http://choosealicense.com/licenses/mit/).

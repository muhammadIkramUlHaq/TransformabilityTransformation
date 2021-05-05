package se.kth.processor;

import files.Test1Class;
import files.Test2Class;
import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.declaration.CtElement;

import static spoon.testing.Assert.assertThat;

class FinalClassRemovalProcessorTest {

    @Test
    void testClassContainsFinalKeywordRemovedSuccess() {
        
        final SpoonAPI spoon = new Launcher();
        spoon.addInputResource("src/test/java/files/Test1Class.java");
        spoon.run();

        final CtElement objectCtType = spoon.getFactory().Type().get(Test1Class.class);
        final CtElement parent = objectCtType.getDirectChildren().get(0).getParent();
        assertThat(parent).withProcessor(FinalClassRemovalProcessor.class).isEqualTo("public class Test1Class {}");

    }

    @Test
    void testClassNotContainsFinalKeywordNoChangesSuccess() {

        final SpoonAPI spoon = new Launcher();
        spoon.addInputResource("src/test/java/files/Test2Class.java");
        spoon.run();

        final CtElement objectCtType = spoon.getFactory().Type().get(Test2Class.class);
        final CtElement parent = objectCtType.getDirectChildren().get(0).getParent();
        assertThat(parent).withProcessor(FinalClassRemovalProcessor.class).isEqualTo("public class Test2Class {}");
    }
}
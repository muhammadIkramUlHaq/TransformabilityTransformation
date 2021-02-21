package se.kth.processor;

import files.Input;
import files.Input1;
import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.declaration.CtElement;

import static spoon.testing.Assert.assertThat;

class FinalClassRemovalProcessorTest {

    @Test
    void testClassContainsFinalKeywordRemovedSuccess() {
        
        final SpoonAPI spoon = new Launcher();
        spoon.addInputResource("src/test/java/files/Input.java");
        spoon.run();

        final CtElement objectCtType = spoon.getFactory().Type().get(Input.class);
        final CtElement parent = objectCtType.getDirectChildren().get(0).getParent();
        assertThat(parent).withProcessor(FinalClassRemovalProcessor.class).isEqualTo("public class Input {}");

    }

    @Test
    void testClassNotContainsFinalKeywordNoChangesSuccess() {

        final SpoonAPI spoon = new Launcher();
        spoon.addInputResource("src/test/java/files/Input1.java");
        spoon.run();

        final CtElement objectCtType = spoon.getFactory().Type().get(Input1.class);
        final CtElement parent = objectCtType.getDirectChildren().get(0).getParent();
        assertThat(parent).withProcessor(FinalClassRemovalProcessor.class).isEqualTo("public class Input1 {}");

    }
}
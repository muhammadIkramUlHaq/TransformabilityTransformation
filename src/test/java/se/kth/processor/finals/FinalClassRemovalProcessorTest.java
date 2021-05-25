package se.kth.processor.finals;
import files.classes.Test1Class;
import files.classes.Test2Class;
import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.PrettyPrinter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static spoon.testing.Assert.assertThat;

public class FinalClassRemovalProcessorTest {

    @Test
    public void testClassContainsFinalKeywordRemovedSuccess() {
        
        final SpoonAPI spoon = new Launcher();
        spoon.addInputResource("src/test/java/files/classes/Test1Class.java");
        spoon.run();

        final CtElement objectCtType = spoon.getFactory().Type().get(Test1Class.class);
        final CtElement parent = objectCtType.getDirectChildren().get(0).getParent();
        assertThat(parent).withProcessor(FinalClassRemovalProcessor.class).isEqualTo("public class Test1Class {}");

    }

    @Test
    public void testClassNotContainsFinalKeywordNoChangesSuccess() {

        final SpoonAPI spoon = new Launcher();
        spoon.addInputResource("src/test/java/files/classes/Test2Class.java");
        spoon.run();

        final CtElement objectCtType = spoon.getFactory().Type().get(Test2Class.class);
        final CtElement parent = objectCtType.getDirectChildren().get(0).getParent();
        assertThat(parent).withProcessor(FinalClassRemovalProcessor.class).isEqualTo("public class Test2Class {}");
    }

    @Test
    public void testClassHavingNoSubclassContainsFinalKeywordRemovedSuccess() {

        String pathResource = "src/test/java/files/classes/package1/";

        Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{"--with-imports"});
        spoon.addProcessor(new FinalClassRemovalProcessor());
        spoon.addInputResource(pathResource);
        spoon.run();
        PrettyPrinter prettyPrinter = spoon.createPrettyPrinter();

        CtType element = spoon.getFactory().Class().getAll().get(0);
        List<CtType<?>> toPrint = new ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String result = prettyPrinter.getResult();
        System.out.println(result);
        assertTrue(result.contains("public class Test3Class extends Test4Class {}"));

    }
}
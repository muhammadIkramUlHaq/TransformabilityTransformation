package se.kth.processor;

import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.PrettyPrinter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FinalMethodRemovalProcessorTest {
    
    @Test
    public void testMethodContainsFinalKeywordRemovedSuccess() {

        String pathResource = "src/test/java/files/methods/Test1Method.java";

        Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{"--with-imports"});
        spoon.addProcessor(new FinalMethodRemovalProcessor());
        spoon.addInputResource(pathResource);
        spoon.run();
        PrettyPrinter prettyPrinter = spoon.createPrettyPrinter();

        CtType element = spoon.getFactory().Class().getAll().get(0);
        List<CtType<?>> toPrint = new ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String result = prettyPrinter.getResult();
        assertTrue(result.contains("public void method1(int t)"));

    }

    @Test
    public void testMethodDoesContainsFinalKeywordRemovedRemainUnChangedSuccess() {

        String pathResource = "src/test/java/files/methods/Test2Method.java";

        Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{"--with-imports"});
        spoon.addProcessor(new FinalMethodRemovalProcessor());
        spoon.addInputResource(pathResource);
        spoon.run();
        PrettyPrinter prettyPrinter = spoon.createPrettyPrinter();

        CtType element = spoon.getFactory().Class().getAll().get(0);
        List<CtType<?>> toPrint = new ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String result = prettyPrinter.getResult();
        assertTrue(result.contains(" public void method2(int t)"));

    }

    @Test
    public void testMethodNotOverridenBySubClassContainsFinalKeywordRemovedSuccess() {

        String pathResource = "src/test/java/files/methods/package1/";

        Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{"--with-imports"});
        spoon.addProcessor(new FinalMethodRemovalProcessor());
        spoon.addInputResource(pathResource);
        spoon.run();
        PrettyPrinter prettyPrinter = spoon.createPrettyPrinter();

        CtType element = spoon.getFactory().Class().getAll().get(0);
        List<CtType<?>> toPrint = new ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String result = prettyPrinter.getResult();
        assertTrue(result.contains("public void methodTest1()"));

    }
}
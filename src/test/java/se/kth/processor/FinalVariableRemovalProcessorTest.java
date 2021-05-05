package se.kth.processor;

import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.PrettyPrinter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FinalVariableRemovalProcessorTest {
    
    @Test
    void testFieldContainsFinalKeywordRemovedSuccess() {
        String pathResource = "src/test/java/files/Test1Variable.java";

        Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{"--with-imports"});
        spoon.addProcessor(new FinalVariableRemovalProcessor());
        spoon.addInputResource(pathResource);
        spoon.run();
        PrettyPrinter prettyPrinter = spoon.createPrettyPrinter();

        CtType element = spoon.getFactory().Class().getAll().get(0);
        List<CtType<?>> toPrint = new ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String result = prettyPrinter.getResult();
        assertTrue(result.contains("int a = 16;"));
    }

    @Test
    void testVariableInLocalBlockContainsFinalKeywordRemovedSuccess() {

        String pathResource = "src/test/java/files/Test2Variable.java";

        Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{"--with-imports"});
        spoon.addProcessor(new FinalVariableRemovalProcessor());
        spoon.addInputResource(pathResource);
        spoon.run();
        PrettyPrinter prettyPrinter = spoon.createPrettyPrinter();

        CtType element = spoon.getFactory().Class().getAll().get(0);
        List<CtType<?>> toPrint = new ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String result = prettyPrinter.getResult();
        assertTrue(result.contains("int m;"));
    }

    @Test
    void testPublicFieldInContainsFinalKeywordRemovedSuccess() {

        String pathResource = "src/test/java/files/package1/";

        Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{"--with-imports"});
        spoon.addProcessor(new FinalVariableRemovalProcessor());
        spoon.addInputResource(pathResource);
        spoon.run();
        PrettyPrinter prettyPrinter = spoon.createPrettyPrinter();

        CtType element = spoon.getFactory().Class().getAll().get(1);
        List<CtType<?>> toPrint = new ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String result = prettyPrinter.getResult();
        assertTrue(result.contains("public static int a = 7"));
    }

    @Test
    void testProtectedFieldInContainsFinalKeywordRemovedSuccess() {

        String pathResource = "src/test/java/files/";

        Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{"--with-imports"});
        spoon.addProcessor(new FinalVariableRemovalProcessor());
        spoon.addInputResource(pathResource);
        spoon.run();
        PrettyPrinter prettyPrinter = spoon.createPrettyPrinter();

        CtType element = spoon.getFactory().Class().getAll().get(6);
        List<CtType<?>> toPrint = new ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String result = prettyPrinter.getResult();
        System.out.println(result);

        assertTrue(result.contains("protected static int b = 10"));
    }
    
}
package se.kth.processor;

import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.PrettyPrinter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FinalVariableRemovalProcessorTest {
    
    @Test
   public void testFieldContainsFinalKeywordRemovedSuccess() {
        String pathResource = "src/test/java/files/variables/Test1Variable.java";

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
    public void testVariableInLocalBlockContainsFinalKeywordRemovedSuccess() {

        String pathResource = "src/test/java/files/variables/Test2Variable.java";

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
    public void testVariableInLocalBlockContainsFinalKeywordDoesNotRemovedSuccess() {

        String pathResource = "src/test/resources/TestVariable7.java";

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
        assertTrue(result.indexOf("final int m;") > 0);
    }

    @Test
    public void testPublicFieldContainsFinalKeywordRemovedSuccess() {

        String pathResource = "src/test/java/files/variables/package1/";

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
    public void testProtectedFieldContainsFinalKeywordRemovedSuccess() {

        String pathResource = "src/test/java/files/variables/";

        Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{"--with-imports"});
        spoon.addProcessor(new FinalVariableRemovalProcessor());
        spoon.addInputResource(pathResource);
        spoon.run();
        PrettyPrinter prettyPrinter = spoon.createPrettyPrinter();

        CtType element = spoon.getFactory().Class().getAll().get(4);
        List<CtType<?>> toPrint = new ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String result = prettyPrinter.getResult();
        
        assertTrue(result.contains("protected static int b = 10"));
    }

    @Test
    public void testDefaultFieldContainsFinalKeywordRemovedSuccess() {

        String pathResource = "src/test/java/files/variables/";

        Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{"--with-imports"});
        spoon.addProcessor(new FinalVariableRemovalProcessor());
        spoon.addInputResource(pathResource);
        spoon.run();
        PrettyPrinter prettyPrinter = spoon.createPrettyPrinter();

        CtType element = spoon.getFactory().Class().getAll().get(5);
        List<CtType<?>> toPrint = new ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String result = prettyPrinter.getResult();

        assertTrue(result.contains("static int c = 10"));
    }

    @Test
    public void testDefaultFieldSubclassLevelWithinPackageContainsFinalKeywordRemovedSuccess() {

        String pathResource = "src/test/java/files/variables/";

        Launcher spoon = new Launcher();
        spoon.setArgs(new String[]{"--with-imports"});
        spoon.addProcessor(new FinalVariableRemovalProcessor());
        spoon.addInputResource(pathResource);
        spoon.run();
        PrettyPrinter prettyPrinter = spoon.createPrettyPrinter();

        CtType element = spoon.getFactory().Class().getAll().get(5);
        List<CtType<?>> toPrint = new ArrayList<>();
        toPrint.add(element);
        prettyPrinter.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String result = prettyPrinter.getResult();
        
        assertTrue(result.contains("static int c = 10"));
    }
    
}
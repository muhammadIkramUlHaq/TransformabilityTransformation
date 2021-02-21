package se.kth.processor;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;

import static org.junit.jupiter.api.Assertions.*;

class FinalVariableRemovalInsideMethodProcessorTest {

    FinalVariableRemovalInsideMethodProcessor finalVariableRemovalInsideMethodProcessor = new FinalVariableRemovalInsideMethodProcessor();
               
    @org.junit.jupiter.api.Test
    void process() {
        
        finalVariableRemovalInsideMethodProcessor.process();
        // how to call that method process.
//
//        final SpoonAPI spoon = new Launcher();
//        spoon.addInputResource("Foo.java");
//        spoon.run();
//
//        final CtType<FinalVariableRemovalInsideMethodProcessor> type = spoon.getFactory().Type().get(FinalVariableRemovalInsideMethodProcessor.class);
//        assertThat(type.getField("i")).withProcessor(new FinalVariableRemovalInsideMethodProcessor()).isEqualTo("public int j;");
    }
}
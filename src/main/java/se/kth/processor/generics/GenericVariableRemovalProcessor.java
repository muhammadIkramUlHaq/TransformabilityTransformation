package se.kth.processor.generics;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.*;

/**
 * Generic Variable Removal
 *
 * @author Muhammad Ikram Ul Haq
 */
public class GenericVariableRemovalProcessor extends AbstractProcessor<CtVariable<?>> {

    @Override
    public boolean isToBeProcessed(CtVariable<?> ctVariable) {
        return ctVariable.getType().isGenerics();
    }

    @Override
    public void process(CtVariable<?> variable) {
        System.out.println("variable = " + variable);


        // Find static variable inside inner class- it must be final and not removed

    }

}

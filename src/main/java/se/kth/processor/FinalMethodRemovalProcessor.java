package se.kth.processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;

/**
 * Final Method Removal
 *
 * @author Muhammad Ikram Ul Haq
 */
public class FinalMethodRemovalProcessor extends AbstractProcessor<CtMethod<?>> {

    @Override
    public boolean isToBeProcessed(CtMethod<?> method) {
        return method.isFinal();
    }

    @Override
    public void process(CtMethod<?> method) {

        if (method.isFinal()) {
            // I need to get references of this method
            // if any single final usage found dont remove
            //else remove
            method.removeModifier(ModifierKind.FINAL);
        }

    }

}

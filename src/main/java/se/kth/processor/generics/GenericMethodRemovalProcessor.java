package se.kth.processor.generics;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;

/**
 * Generic Method Removal
 *
 * @author Muhammad Ikram Ul Haq
 */
public class GenericMethodRemovalProcessor extends AbstractProcessor<CtMethod<?>> {

    @Override
    public boolean isToBeProcessed(CtMethod<?> method) {
        return method.isFinal();
    }

    @Override
    public void process(CtMethod<?> method) {

        // Always remove final keyword in case of method
        method.removeModifier(ModifierKind.FINAL);
    }

}

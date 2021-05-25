package se.kth.processor.finals;

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

        // Always remove final keyword in case of method
        method.removeModifier(ModifierKind.FINAL);
    }

}

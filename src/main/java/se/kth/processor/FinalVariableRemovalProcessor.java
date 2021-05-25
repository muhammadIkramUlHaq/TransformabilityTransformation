package se.kth.processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.declaration.ModifierKind;

/**
 * Final Variable Removal
 *
 * @author Muhammad Ikram Ul Haq
 */
public class FinalVariableRemovalProcessor extends AbstractProcessor<CtVariable<?>> {

    @Override
    public boolean isToBeProcessed(CtVariable<?> ctVariable) {
        return ctVariable.isFinal();
    }

    @Override
    public void process(CtVariable<?> variable) {
        // Find static variable inside inner class- it must be final and not removed
        final CtElement parent = variable.getParent().getParent();
        if (!variable.isStatic() || (parent instanceof CtPackage)) {
            variable.removeModifier(ModifierKind.FINAL);
        }
    }

}

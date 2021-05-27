package se.kth.processor.finals;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.*;

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
        if (!variable.isStatic() || (parent instanceof CtPackage) || variable.getParent() instanceof CtEnum) {
            variable.removeModifier(ModifierKind.FINAL);
        }
    }

}

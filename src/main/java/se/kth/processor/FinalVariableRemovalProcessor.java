package se.kth.processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.reference.CtVariableReference;

/**
 * Final Variable Removal
 *
 * @author Muhammad Ikram Ul Haq
 */
public class FinalVariableRemovalProcessor extends AbstractProcessor<CtVariable<?>> {

    @Override
    public boolean isToBeProcessed(CtVariable<?> variable) {
        return variable.isFinal();
    }

    @Override
    public void process(CtVariable<?> variable) {
  
        if (variable.isFinal()) {
            // I need to get references of this variable
            // if any single final usage found dont remove
            // else remove
            final CtVariableReference<?> reference = variable.getReference();
          //  if(reference.isParentInitialized())
            variable.removeModifier(ModifierKind.FINAL);
        }

    }

}

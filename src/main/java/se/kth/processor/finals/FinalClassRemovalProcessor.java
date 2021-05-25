package se.kth.processor.finals;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.ModifierKind;

/**
 * Final Method Removal
 *
 * @author Muhammad Ikram Ul Haq
 */
public class FinalClassRemovalProcessor extends AbstractProcessor<CtClass<?>> {

    @Override
    public boolean isToBeProcessed(CtClass<?> classDec) {
        return classDec.isFinal();
    }

    @Override
    public void process(CtClass<?> classDef) {
      // Always remove final keyword in case of class
        classDef.removeModifier(ModifierKind.FINAL);
    }
}

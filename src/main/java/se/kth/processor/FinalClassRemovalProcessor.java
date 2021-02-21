package se.kth.processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.reference.CtTypeReference;

import java.util.Set;

/**
 * Final Method Removal
 *
 * @author Muhammad Ikram Ul Haq
 */
public class  FinalClassRemovalProcessor extends AbstractProcessor<CtClass<?>> {

    @Override
    public boolean isToBeProcessed(CtClass<?> classDec) {
        return classDec.isFinal();
    }

    @Override
    public void process(CtClass<?> classDef) {

        if (classDef.isFinal()) {
            // I need to get references of this class
            // if any single final usage found dont remove
            // else remove
            classDef.removeModifier(ModifierKind.FINAL);
        }

    }

}

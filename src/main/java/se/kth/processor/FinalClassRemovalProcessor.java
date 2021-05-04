package se.kth.processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.ClassFactory;

import java.util.ArrayList;
import java.util.List;


import static se.kth.shared.Utility.*;

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

        final String currentClassName = getClassName(classDef);

        // Get all the classes in the project
        final List<CtType<?>> allClasses = getFactory().Class().getAll();

        List<CtType> subClasses = getSubclassList(currentClassName, allClasses);

        if (classDef.isFinal()) {
            // if and only if there is no known subclasses of it.
            // else remove

            if (subClasses.isEmpty())
                classDef.removeModifier(ModifierKind.FINAL);
        }

    }
}

package se.kth.processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.*;
import spoon.reflect.factory.ClassFactory;
import spoon.reflect.reference.CtPackageReference;
import spoon.reflect.reference.CtTypeReference;

import java.util.*;

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

        final String currentClassName = classDef.getSimpleName();

        // Get all the classes in the project
        final ClassFactory classFactory = getFactory().Class();
        final List<CtType<?>> allClasses = classFactory.getAll();

        List<CtType> subClasses = new ArrayList<>();

        for (CtType ctClass : allClasses
        ) {
            if (ctClass.getSuperclass() != null) {
                if (ctClass.getSuperclass().getSimpleName().equals(currentClassName))
                    subClasses.add(ctClass);
            }
        }

        if (classDef.isFinal()) {
            // if and only if there is no known subclasses of it.
            // I need to get references of this class
            // if any single final usage found dont remove
            // else remove

            if (subClasses.isEmpty())
                classDef.removeModifier(ModifierKind.FINAL);
        }

    }


}

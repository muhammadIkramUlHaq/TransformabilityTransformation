package se.kth.processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.ClassFactory;

import java.util.ArrayList;
import java.util.List;

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

        final String currentMethodName = method.getSimpleName();

        final CtClass currentClass = method.getParent(ctElement -> ctElement instanceof CtType);
        final String currentClassName = currentClass.getSimpleName();

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

        int methodOverrideCount = 0;

        // I need to get references of this method
        // if any single final usage found dont remove
        //else remove
        for (CtType ctClass : subClasses
        ) {
            final List<CtMethod> methods = ctClass.getElements(ctElement -> ctElement instanceof CtMethod);

            for (CtMethod ctMethod : methods
            ) {
                if (ctMethod.getSimpleName().equals(currentMethodName) && ctMethod.isOverriding(method))
                    methodOverrideCount++;
            }
        }

        // Considering Self method overriding
        if (methodOverrideCount == 0) {
            method.removeModifier(ModifierKind.FINAL);
        }


    }

}

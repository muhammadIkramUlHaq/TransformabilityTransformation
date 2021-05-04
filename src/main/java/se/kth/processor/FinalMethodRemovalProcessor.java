package se.kth.processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;

import java.util.List;

import static se.kth.shared.Utility.getClassName;
import static se.kth.shared.Utility.getSubclassList;

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
        final String currentClassName =  getClassName(currentClass);

        // Get all the classes in the project
        final List<CtType<?>> allClasses = getFactory().Class().getAll();

        List<CtType> subClasses = getSubclassList(currentClassName, allClasses);
        
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

package se.kth.processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.reference.CtPackageReference;
import spoon.reflect.reference.CtTypeReference;

import java.util.*;

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
            // if and only if there is no known subclasses of it.
            // I need to get references of this class
            // if any single final usage found dont remove
            // else remove
            System.out.println(classDef.getAllExecutables().size());
          
            final String qualifiedName = classDef.getQualifiedName();
            System.out.println(classDef.getReference().box().getSimpleName());
            System.out.println(classDef.getSuperclass().getSimpleName());
            classDef.removeModifier(ModifierKind.FINAL);
        }

    }

   /* static List<CtClass<?>> stagedClassExtensionChain(CtClass<?> leaf) {
        List<CtClass<?>> chain = new ArrayList<>();
        for (CtClass<?> type = leaf;
             type != null && type.getAnnotation(Staged.class) != null; ) {
            chain.add(type);
            CtTypeReference<?> superClass = type.getSuperclass();
            if (superClass == null)
                break;
            type = (CtClass<?>) superClass.getDeclaration();
        }
        return chain;
    }*/

}

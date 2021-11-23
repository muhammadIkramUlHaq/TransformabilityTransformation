package se.kth.processor.generics;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtTypeReference;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * Generic Class Removal
 *
 * @author Muhammad Ikram Ul Haq
 */
public class GenericClassRemovalProcessor extends AbstractProcessor<CtClass<?>> {

    @Override
    public boolean isToBeProcessed(CtClass<?> classDec) {
        return isNotEmpty(classDec.getFormalCtTypeParameters());
    }

    @Override
    public void process(CtClass<?> classDef) {

        final String className = classDef.getSimpleName();

        final List<CtType<?>> allClasses = getFactory().Class().getAll(true);
        final List<CtType<?>> allClassesExceptCurrent = allClasses.stream().filter(ctType -> !ctType.getSimpleName().equals(className)).collect(Collectors.toList());

        final List<CtType<?>> subClasses = allClassesExceptCurrent.stream().filter(ctType -> isNotEmpty(ctType.getSuperclass()) && ctType.getSuperclass().getSimpleName().equals(className)).collect(Collectors.toList());

        final List<CtTypeParameter> formalCtTypeParameters = classDef.getFormalCtTypeParameters();

        final List<CtVariable> variableTypes = classDef.getElements(ctElement -> ctElement instanceof CtVariable);
        final Set<CtMethod<?>> methods = classDef.getMethods();

        Iterator<CtTypeParameter> iterator = formalCtTypeParameters.iterator();

        while (iterator.hasNext()) {
            CtTypeParameter ctTypeParameter = iterator.next();
            variableTypes
                    .stream()
                    .filter(ctVariable -> ctVariable.getType().getSimpleName().equals(ctTypeParameter.getSimpleName()))
                    .map(ctVariable -> ctVariable.setType(getFactory().createReference("Object")))
                    .collect(Collectors.toList());
            methods.stream()
                    .filter(ctMethod -> ctMethod.getType().getSimpleName().equals(ctTypeParameter.getSimpleName()))
                    .map(ctMethod -> ctMethod.setType(getFactory().createReference("Object")))
                    .collect(Collectors.toList());

            iterator.remove();
            classDef.removeFormalCtTypeParameter(ctTypeParameter);
        }

        subClasses.forEach(
                ctType -> {
                    final CtTypeReference<?> superclass = ctType.getSuperclass().clone();
                    final String supperClassWithoutTypeParameter = superclass.toString().split("<")[0];

                    final List<CtElement> elements = ctType.getElements(ctElement -> ctElement instanceof CtStatement);

                    for (CtElement ctElement:  elements
                         ) {
                        System.out.println("Element = " + ctElement);
                    }

                    final List<CtVariable> variables = ctType.getElements(ctElement -> ctElement instanceof CtVariable);

                    for (CtVariable ctVariable: variables
                         ) {
                        if(ctVariable.getType().equals(superclass)) {
                            ctVariable.setType(getFactory().createReference(supperClassWithoutTypeParameter));
                        }
                    }
                
                    final Set<CtMethod<?>> methods1 = ctType.getMethods();

                    for (CtMethod ctMethod: methods1
                         ) {
                        if(ctMethod.getType().equals(superclass)) {
                            ctMethod.setType(getFactory().createReference(supperClassWithoutTypeParameter));
                        }
                    }

                    // Fix interfaces
                    ctType.setSuperclass(getFactory().createReference(supperClassWithoutTypeParameter));


                }
        );


        /*    This use case is left
                    class GenericSuperClass<T extends Number>
            {
                //Generic super class with bounded type parameter
            }

            class GenericSubClass1 extends GenericSuperClass<Number>
            {
                //type parameter replaced by upper bound
            }

            class GenericSubClass2 extends GenericSuperClass<Integer>
            {
                //type parameter replaced by sub class of upper bound
            }

            class GenericSubClass3 extends GenericSuperClass<T extends Number>
            {
                //Compile time error
            }
         */
        System.out.println("Generics Class Type Removed!!!!!");

    }
}

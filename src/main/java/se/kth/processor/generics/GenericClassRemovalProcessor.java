package se.kth.processor.generics;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtTypeReference;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
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
        final String currentClassName = classDef.getSimpleName();
        final String classNameWithoutGenericParameter = currentClassName.split("<")[0];

        final List<CtType<?>> allClasses = getFactory().Class().getAll(true);
        final List<CtType<?>> allInterfaces = getFactory().Interface().getAll(true);
        final List<CtType<?>> allClassesExceptCurrent = allClasses
                .stream()
                .filter(ctType -> !ctType.getSimpleName().equals(currentClassName))
                .collect(Collectors.toList());

        final List<CtType<?>> subClasses = allClassesExceptCurrent
                .stream()
                .filter(ctType -> isNotEmpty(ctType.getSuperclass()) && ctType.getSuperclass().getSimpleName().equals(currentClassName))
                .collect(Collectors.toList());

        final List<CtTypeParameter> formalCtTypeParameters = classDef.getFormalCtTypeParameters();

        final List<CtVariable> variableTypes = classDef.getElements(ctElement -> ctElement instanceof CtVariable);
        final Set<CtMethod<?>> methods = classDef.getMethods();

        Iterator<CtTypeParameter> iterator = formalCtTypeParameters.iterator();

        // Loop to convert each type parameter to object
        // Array type is working , Condition may be could be improved
        while (iterator.hasNext()) {
            CtTypeParameter ctTypeParameter = iterator.next();

            for (CtVariable ctVariable : variableTypes
            ) {
                replaceGenericType(ctTypeParameter.getSimpleName(), "Object", ctVariable);
            }

            for (CtMethod ctMethod : methods
            ) {
                replaceGenericType(ctTypeParameter.getSimpleName(), "Object", ctMethod);
            }

            iterator.remove();
            classDef.removeFormalCtTypeParameter(ctTypeParameter);
        }

        // Find references of generic class in all interfaces and remove generic from it
        allInterfaces.forEach(
                interfaceIns -> {
                    final List<CtElement> elements = interfaceIns.getElements(ctElement -> ctElement instanceof CtStatement);

                    final List<CtVariable> variables = interfaceIns.getElements(ctElement -> ctElement instanceof CtVariable);

                    for (CtVariable ctVariable : variables
                    ) {
                        // variable assignment used within method Parameter & Declaration
                        if (ctVariable.getType().getSimpleName().equals(currentClassName)) {
                            ctVariable.setType(getFactory().createReference(classNameWithoutGenericParameter));
                        }
                    }

                    final Set<CtMethod<?>> methods1 = interfaceIns.getMethods();

                    for (CtMethod ctMethod : methods1
                    ) {
                        if (ctMethod.getType().getSimpleName().equals(currentClassName)) {
                            ctMethod.setType(getFactory().createReference(classNameWithoutGenericParameter));
                        }
                    }

                }
        );

        // Find references of generic class from all classes and remove generic from it
        allClasses.forEach(
                ctType -> {


                    final List<CtElement> elements = ctType.getElements(ctElement -> ctElement instanceof CtStatement);

                    // To handle right side expressions or with return statements
                    for (CtElement ctElement : elements
                    ) {
                        if (ctElement instanceof CtExpression) {
                            if (((CtExpression<?>) ctElement).getType().getSimpleName().equals(currentClassName)) {
                                ((CtExpression<?>) ctElement).setType(getFactory().createReference(classNameWithoutGenericParameter));
                            }
                        }
                    }

                    final List<CtVariable> variables = ctType.getElements(ctElement -> ctElement instanceof CtVariable);

                    for (CtVariable ctVariable : variables
                    ) {
                        // variable assignment used within method Parameter & Declaration
                        replaceGenericType(currentClassName, classNameWithoutGenericParameter, ctVariable);
                    }

                    final Set<CtMethod<?>> methods1 = ctType.getMethods();

                    for (CtMethod ctMethod : methods1
                    ) {
                        replaceGenericType(currentClassName, classNameWithoutGenericParameter, ctMethod);
                    }

                }
        );
        // Unbounded Cases

      /*  classDef.getSuperInterfaces().stream().map(ctTypeReference -> {
            final CtInterface<?> superInterface = (CtInterface<?>) ctTypeReference.clone();
            final String superInterfaceNameWithoutGeneric = superInterface.toString().split("<")[0];
            System.out.println("super Interface 1 = " + superInterface.getQualifiedName());
            final CtTypeReference<Object> reference = getFactory().createReference(superInterfaceNameWithoutGeneric);
            superInterface.removeFormalCtTypeParameter(superInterface.getFormalCtTypeParameters().get(0));


            System.out.println("super Interface 0 = " + superInterface.getDeclaringType());


            return superInterface;
        }).collect(Collectors.toSet());*/


        // Unbounded Cases
        subClasses.forEach(
                ctType -> {
                    final CtTypeReference<?> superclass = ctType.getSuperclass().clone();
                    final String supperClassWithoutTypeParameter = superclass.toString().split("<")[0];

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

        // https://www.baeldung.com/java-generics

        //https://www.baeldung.com/java-type-erasure

        // https://hajsoftutorial.com/java-generic-interfaces/
        // https://turreta.com/2017/06/26/java-3-ways-to-implement-a-generic-interface/
        System.out.println("Generics Class Type Removed!!!!!");

    }

    private void replaceGenericType(String type, String replaceType, CtMethod ctMethod) {
        final String methodTypeName = ctMethod.getType().getSimpleName().split(Pattern.quote("["))[0];
        if (ctMethod.getType().isArray() && methodTypeName.equals(type)) {
            ctMethod.setType(getFactory().createArrayReference(replaceType));
        }  // This else condition for contains could be improved
        else if (methodTypeName.equals(type)) {
            ctMethod.setType(getFactory().createReference(replaceType));
        }
    }

    private void replaceGenericType(String type, String replaceType, CtVariable ctVariable) {
        final String variableTypeName = ctVariable.getType().getSimpleName().split(Pattern.quote("["))[0];
        if (ctVariable.getType().isArray() && variableTypeName.equals(type)) {
            ctVariable.setType(getFactory().createArrayReference(replaceType));
        } // This else condition could be improved
        else if (variableTypeName.equals(type)) {
            ctVariable.setType(getFactory().createReference(replaceType));
        }
    }
}

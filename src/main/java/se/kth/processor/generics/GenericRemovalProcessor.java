package se.kth.processor.generics;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtTypeReference;

import java.util.HashSet;
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
public class GenericRemovalProcessor extends AbstractProcessor<CtType<?>> {

    @Override
    public boolean isToBeProcessed(CtType<?> classDec) {
        return classDec.getFormalCtTypeParameters().size() > 0 ;
    }

    @Override
    public void process(CtType<?> classDef) {
        final String currentClassName = classDef.getSimpleName();
        final String currentClassNameWithoutGenericParameter = classDef.getQualifiedName();

        // this includes classes as well as interfaces
        final List<CtType<?>> allClasses = getFactory().Class().getAll();

        final List<CtType<?>> allClassesExceptCurrent = allClasses
                .stream()
                .filter(ctType -> !ctType.getSimpleName().equals(currentClassName))
                .collect(Collectors.toList());

        final List<CtType<?>> subClasses = allClassesExceptCurrent
                .stream()
                .filter(ctType -> isNotEmpty(ctType.getSuperclass()) && ctType.getSuperclass().getSimpleName().equals(currentClassName))
                .collect(Collectors.toList());

        final Set<CtTypeReference<?>> superInterfaces = classDef.getSuperInterfaces();

        final List<CtTypeParameter> formalCtTypeParameters = classDef.getFormalCtTypeParameters();

        final List<CtVariable> variableTypes = classDef.getElements(ctElement -> ctElement instanceof CtVariable);
        final Set<CtMethod<?>> methods = classDef.getMethods();

        Iterator<CtTypeParameter> iterator = formalCtTypeParameters.iterator();

        // Loop to convert each type parameter to object
        // Array type is working , Condition may be could be improved
        while (iterator.hasNext()) {
            CtTypeParameter ctTypeParameter = iterator.next();
            String replaceType;
            if (isNotEmpty(ctTypeParameter.getSuperclass())) {
                replaceType = ctTypeParameter.getSuperclass().getSimpleName();
            } else {
                replaceType = "Object";
            }


            for (CtVariable ctVariable : variableTypes
            ) {
                replaceGenericType(ctTypeParameter.getSimpleName(), replaceType, ctVariable);
            }

            for (CtMethod ctMethod : methods
            ) {
                replaceGenericType(ctTypeParameter.getSimpleName(), replaceType, ctMethod);
            }

            iterator.remove();
            classDef.removeFormalCtTypeParameter(ctTypeParameter);
        }

        // Find references of generic class from all classes and remove generic from it
        allClasses.forEach(
                ctType -> {
                    final List<CtElement> elements = ctType.getElements(ctElement -> ctElement instanceof CtStatement);

                    // To handle right side expressions or with return statements
                    for (CtElement ctElement : elements
                    ) {
                        if (ctElement instanceof CtExpression) {
                            final boolean notEmpty = isNotEmpty(((CtExpression<?>) ctElement).getType());
                            ((CtExpression<?>) ctElement).getType();
                            if (notEmpty && ((CtExpression<?>) ctElement).getType().getSimpleName().equals(currentClassName)) {
                                ((CtExpression<?>) ctElement).setType(getFactory().createReference(currentClassNameWithoutGenericParameter));
                            }
                        }
                    }

                    final List<CtVariable> variables = ctType.getElements(ctElement -> ctElement instanceof CtVariable);

                    for (CtVariable ctVariable : variables
                    ) {
                        // variable assignment used within method Parameter & Declaration
                        replaceGenericType(currentClassName, currentClassNameWithoutGenericParameter, ctVariable);
                    }

                    final Set<CtMethod<?>> classMethods = ctType.getMethods();

                    for (CtMethod ctMethod : classMethods
                    ) {

                        final List<CtTypeParameter> formalCtTypeParametersForMethod = ctMethod.getFormalCtTypeParameters();

                        if (isNotEmpty(formalCtTypeParametersForMethod)) {
                            Iterator<CtTypeParameter> iteratorForMethods = formalCtTypeParametersForMethod.iterator();
                            while (iteratorForMethods.hasNext()) {
                                CtTypeParameter ctTypeParameter = iteratorForMethods.next();
                                iteratorForMethods.remove();
                                ctMethod.removeFormalCtTypeParameter(ctTypeParameter);
                            }

                        }

                        replaceGenericType(currentClassName, currentClassNameWithoutGenericParameter, ctMethod);
                    }

                }
        );

        // Unbounded Cases
        Set<CtTypeReference<?>> updatedSuperInterfaceSet = new HashSet<>();

        superInterfaces.forEach(ctTypeReference -> {
            final CtTypeReference<Object> reference = getFactory().createReference(ctTypeReference.getQualifiedName());
            updatedSuperInterfaceSet.add(reference);
        });

        classDef.setSuperInterfaces(updatedSuperInterfaceSet);

        // Unbounded Cases
        subClasses.forEach(
                subClass -> {
                    final CtTypeReference<?> superclass = subClass.getSuperclass().clone();
                    final String supperClassWithoutTypeParameter = superclass.getQualifiedName();
                    subClass.setSuperclass(getFactory().createReference(supperClassWithoutTypeParameter));
                }
        );


        /*    This use case is done as well
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

        // https://www.geeksforgeeks.org/bounded-types-generics-java/

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

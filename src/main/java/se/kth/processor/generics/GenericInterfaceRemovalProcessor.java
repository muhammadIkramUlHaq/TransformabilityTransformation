package se.kth.processor.generics;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtTypeReference;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * Generic Interface Removal
 *
 * @author Muhammad Ikram Ul Haq
 */


public class GenericInterfaceRemovalProcessor extends AbstractProcessor<CtInterface<?>> {

    @Override
    public boolean isToBeProcessed(CtInterface<?> interfaceDecl) {
        return isNotEmpty(interfaceDecl.getFormalCtTypeParameters());
    }


    @Override
    public void process(CtInterface<?> interfaceDef) {
        final String currentInterfaceName = interfaceDef.getSimpleName();

        final List<CtTypeParameter> formalCtTypeParameters = interfaceDef.getFormalCtTypeParameters();

        final List<CtVariable> variableTypes = interfaceDef.getElements(ctElement -> ctElement instanceof CtVariable);
        final Set<CtMethod<?>> methods = interfaceDef.getMethods();
        final List<CtType<?>> allInterfaces = getFactory().Interface().getAll(true);
        final List<CtType<?>> allInterfacesExceptCurrent = allInterfaces.stream().filter(ctType -> !ctType.getSimpleName().equals(currentInterfaceName)).collect(Collectors.toList());
        final List<CtType<?>> allClasses = getFactory().Class().getAll(true);



        Iterator<CtTypeParameter> iterator = formalCtTypeParameters.iterator();

        // Loop to convert each type parameter to object
        while (iterator.hasNext()) {
            CtTypeParameter ctTypeParameter = iterator.next();
            
            for (CtVariable ctVariable : variableTypes
            ) {
                if (ctVariable.getType().getSimpleName().equals(ctTypeParameter.getSimpleName())) {
                    ctVariable.setType(getFactory().createReference("Object"));
                } // This else condition for contains could be improved
                else if(ctVariable.getType().isArray() &&
                        ctVariable.getType().getSimpleName().contains(ctTypeParameter.getSimpleName())){
                    ctVariable.setType(getFactory().createReference("Object[]"));
                }
            }
            for (CtMethod ctMethod : methods
            ) {
                if (ctMethod.getType().getSimpleName().equals(ctTypeParameter.getSimpleName())) {
                    ctMethod.setType(getFactory().createReference("Object"));
                }  // This else condition for contains could be improved
                else if(ctMethod.getType().isArray() &&
                        ctMethod.getType().getSimpleName().contains(ctTypeParameter.getSimpleName())){
                    ctMethod.setType(getFactory().createReference("Object[]"));
                }
            }

            iterator.remove();
            interfaceDef.removeFormalCtTypeParameter(ctTypeParameter);
        }



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
        System.out.println("Generics Interface Type Removed!!!!!");

    }
}

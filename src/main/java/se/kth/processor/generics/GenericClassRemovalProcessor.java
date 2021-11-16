package se.kth.processor.generics;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtTypeReference;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        final List<CtType<?>> collect = allClasses.stream().filter(ctType -> !ctType.getSimpleName().equals(className)).collect(Collectors.toList());

        final List<CtTypeMember> typeMembers = classDef.getTypeMembers();


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

        System.out.println("Generics Class Type Removed!!!!!");

    }
}

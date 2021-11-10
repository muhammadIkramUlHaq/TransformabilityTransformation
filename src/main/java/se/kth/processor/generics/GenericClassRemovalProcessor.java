package se.kth.processor.generics;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtTypeParameter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

        final List<CtTypeParameter> formalCtTypeParameters = classDef.getFormalCtTypeParameters();

        Iterator<CtTypeParameter> iterator = formalCtTypeParameters.iterator();

        while (iterator.hasNext()) {
            CtTypeParameter ctTypeParameter = iterator.next();
            iterator.remove();
            classDef.removeFormalCtTypeParameter(ctTypeParameter);
        }

        System.out.println("Generics Class Type Removed!!!!!");

    }
}

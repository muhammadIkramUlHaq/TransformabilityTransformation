package se.kth.processor.generics;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtTypeMember;
import spoon.reflect.declaration.CtTypeParameter;
import spoon.reflect.declaration.ModifierKind;

import java.util.Iterator;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * Generic Method Removal
 *
 * @author Muhammad Ikram Ul Haq
 */
public class GenericMethodRemovalProcessor extends AbstractProcessor<CtMethod<?>> {

    @Override
    public boolean isToBeProcessed(CtMethod<?> method) {
        return isNotEmpty(method.getFormalCtTypeParameters()) ;
    }

    @Override
    public void process(CtMethod<?> method) {

        final List<CtTypeParameter> formalCtTypeParameters = method.getFormalCtTypeParameters();

        Iterator<CtTypeParameter> iterator = formalCtTypeParameters.iterator();

        while (iterator.hasNext()) {
            CtTypeParameter ctTypeParameter = iterator.next();
            iterator.remove();
            method.removeFormalCtTypeParameter(ctTypeParameter);
        }

        
        System.out.println("Generic Method is Removed");
    }

}

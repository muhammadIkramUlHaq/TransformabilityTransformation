package se.kth.processor.generics;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.*;

import java.util.List;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * Generic Variable Removal
 *
 * @author Muhammad Ikram Ul Haq
 */
public class GenericVariableRemovalProcessor extends AbstractProcessor<CtVariable<?>> {

    @Override
    public boolean isToBeProcessed(CtVariable<?> ctVariable) {
        return isNotEmpty(ctVariable.getType().getSimpleName());
    }

    @Override
    public void process(CtVariable<?> variable) {
        System.out.println("variable = " + variable);
        final String variableTypeName = variable.getType().getSimpleName().split(Pattern.quote("["))[0];
        final List<CtType<?>> allClasses = getFactory().Class().getAll(true);

        allClasses.forEach(
                ctClass -> {
                    final String className = ctClass.getSimpleName();
                    if (variable.getType().isArray() && variableTypeName.equals(className)) {
                        final String classNameWithoutGenericParameter = className.split("<")[0];
                        variable.setType(getFactory().createArrayReference(classNameWithoutGenericParameter));
                    } // This else condition could be improved
                    else if (variableTypeName.equals(className)) {
                        final String classNameWithoutGenericParameter = className.split("<")[0];
                        variable.setType(getFactory().createReference(classNameWithoutGenericParameter));
                    }
                    
                    // To handle right side expressions 
                    final CtExpression<?> defaultExpression = variable.getDefaultExpression();
                    if (isNotEmpty(defaultExpression) && defaultExpression.getType().getSimpleName().equals(className)) {
                        final String classNameWithoutGenericParameter = className.split("<")[0];
                        defaultExpression.setType(getFactory().createReference(classNameWithoutGenericParameter));
                    }
                    
                });

    }
}

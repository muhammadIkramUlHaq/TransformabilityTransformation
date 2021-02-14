package se.kth.processor;


import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtStatementList;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtVariable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Final Variable Removal
 *
 * @author Muhammad Ikram Ul Haq
 */
public class FinalVariableRemovalInsideClassProcessor extends AbstractProcessor<CtClass<?>> {

    @Override
    public boolean isToBeProcessed(CtClass<?> element) {
        return element != null;
    }

    @Override
    public void process(CtClass<?> element) {

        // Todo: Get all variables declared inside the method.
        // Todo: Filter out only those with final keywords.
        // Todo: Replace the declaration of variables without final keywords.

        final List<CtElement> elements = element.getDirectChildren().stream().filter(ctElement -> ctElement instanceof CtVariable).collect(Collectors.toList());

        final List<CtLocalVariable> ctLocalVariables = elements.stream().map(ctVariable -> {
            CtVariable castCtVariable = (CtVariable) ctVariable;
            final CtLocalVariable localVariable = getFactory().Core().createLocalVariable();
            if (castCtVariable.isFinal()) {
                localVariable.setType(castCtVariable.getReference().getType());
                localVariable.setSimpleName(castCtVariable.getSimpleName());
                localVariable.setAssignment(castCtVariable.getDefaultExpression());

                final CtStatementList statementList = getFactory().createStatementList().addStatement(localVariable);
                element.insertAfter(statementList);
                ctVariable.delete();
            }
            return localVariable;
        }).collect(Collectors.toList());
        System.out.println(element);
    }

}

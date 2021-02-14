package se.kth.processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtStatementList;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtVariable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Final Variable Removal
 *
 * @author Muhammad Ikram Ul Haq
 */
public class FinalVariableRemovalInsideMethodProcessor extends AbstractProcessor<CtMethod<?>> {

    @Override
    public boolean isToBeProcessed(CtMethod<?> element) {
        return element.getBody() != null;
    }

    @Override
    public void process(CtMethod<?> method) {

        // Todo: Get all variables declared inside the Method.
        // Todo: Filter out only those with final keywords.
        // Todo: Replace the declaration of variables without final keywords.

        final List<CtElement> ctVariableList = method.getBody().getElements(ctElement -> ctElement instanceof CtVariable)
                .stream()
                .collect(Collectors.toList());

        final List<CtLocalVariable> ctLocalVariables = ctVariableList.stream().map(ctVariable -> {
            CtVariable castCtVariable = (CtVariable) ctVariable;
            final CtLocalVariable localVariable = getFactory().Core().createLocalVariable();
            if (castCtVariable.isFinal()) {
                localVariable.setType(castCtVariable.getReference().getType());
                localVariable.setSimpleName(castCtVariable.getSimpleName());
                localVariable.setAssignment(castCtVariable.getDefaultExpression());

                final CtStatementList statementList = getFactory().createStatementList().addStatement(localVariable);
                ctVariable.delete();
                method.getBody().insertBegin(statementList);

            }
            return localVariable;
        }).collect(Collectors.toList());
    }

}

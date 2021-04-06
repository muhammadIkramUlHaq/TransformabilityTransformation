package se.kth.processor;


import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtExpression;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.ClassFactory;

import java.util.List;

/**
 * Final Variable Removal
 *
 * @author Muhammad Ikram Ul Haq
 */
public class FinalVariableRemovalProcessor extends AbstractProcessor<CtClass<?>> {

    @Override
    public boolean isToBeProcessed(CtClass<?> classDec) {
        return classDec.isClass();
    }

    @Override
    public void process(CtClass<?> classDef) {

        // Get all the classes in the project
        final ClassFactory classFactory = getFactory().Class();
        final String currentClassName = classDef.getSimpleName();
        final List<CtType<?>> allClasses = classFactory.getAll();

        final List<CtAssignment> ctAssignments = classDef.getElements(ctElement -> ctElement instanceof CtAssignment);
        final List<CtVariable> ctVariables = classDef.getElements(ctElement -> ctElement instanceof CtVariable);

        for (CtVariable variable : ctVariables
        ) {

            int assigned = 0;
            if (variable.isFinal()) {
                // I need to get references of this variable
                // if any single final usage found dont remove
                // else remove

                // Check whether the field is private or public
                final boolean isPrivateVariable = variable.isPrivate();
                final boolean isPublicVariable = variable.isPublic();

                // Already Initialized
                final CtExpression variableDefaultExpression = variable.getDefaultExpression();
                if (isPrivateVariable && variableDefaultExpression != null) {

                    for (CtAssignment ctAssignment : ctAssignments
                    ) {
                        final String assignedVariable = ctAssignment.getAssigned().toString();
                        if (assignedVariable.equals(variable.getSimpleName())) {
                            assigned++;
                        }
                    }
                } else if (!isPrivateVariable) {
                    // Check final Block Variable
                    for (CtAssignment ctAssignment : ctAssignments
                    ) {
                        final String assignedVariable = ctAssignment.getAssigned().toString();
                        if (assignedVariable.equals(variable.getSimpleName())) {
                            assigned++;
                        }
                    }
                } else if (isPublicVariable) {
                    for (CtType ctClass : allClasses) {
                        final List<CtAssignment> variableAssignments = ctClass.getElements(ctElement -> ctElement instanceof CtAssignment);
                        for (CtAssignment ctAssignment : variableAssignments
                        ) {
                            final String assignedVariable = ctAssignment.getAssigned().toString();
                            if (assignedVariable.equals(variable.getSimpleName())) {
                                assigned++;
                            }
                        }


                    }

                }

                // apply filter here
                if (assigned == 0) {
                    variable.removeModifier(ModifierKind.FINAL);
                }

            }


        }


    }


}

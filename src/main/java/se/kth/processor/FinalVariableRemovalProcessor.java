package se.kth.processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtExpression;
import spoon.reflect.declaration.*;

import java.util.List;

import static se.kth.shared.Utility.getClassName;
import static se.kth.shared.Utility.getSubclassList;

/**
 * Final Variable Removal
 *
 * @author Muhammad Ikram Ul Haq
 */
public class FinalVariableRemovalProcessor extends AbstractProcessor<CtClass<?>> {

    @Override
    public boolean isToBeProcessed(CtClass<?> classDec) {
        return classDec != null;
    }

    @Override
    public void process(CtClass<?> classDef) {

        final String currentClassName = getClassName(classDef);
        final CtPackage currentPackage = getPackage(classDef);

        // Get all the classes in the project
        final List<CtType<?>> allClasses = getFactory().Class().getAll();
        // Get all Subclasses for Given Class
        List<CtType> subClasses = getSubclassList(currentClassName, allClasses);

        // Get all variables in this class
        final List<CtVariable> ctVariables = classDef.getElements(ctElement -> ctElement instanceof CtVariable);
        // Get all fields in this class
        final List<CtField<?>> fields = classDef.getFields();
        // Get all Assignments in this class
        final List<CtAssignment> ctAssignments = classDef.getElements(ctElement -> ctElement instanceof CtAssignment);

        for (CtVariable variable : ctVariables
        ) {

            int variableAssignmentCount = 0;
            // Check whether the field is private or public
            final boolean isClassField = fields.contains(variable);
            final boolean isPrivateVariable = isClassField && variable.isPrivate();
            final boolean isPublicVariable = isClassField && variable.isPublic();
            final boolean isProtectedVariable = isClassField && variable.isProtected();
            final boolean isDefaultVariable = isClassField && !isPrivateVariable && !isPublicVariable && !isProtectedVariable;
            final boolean isBlockVariable = !fields.contains(variable);

            // Already Initialized
            final CtExpression variableDefaultExpression = variable.getDefaultExpression();

            if (variable.isFinal()) {
                // I need to get references of this variable
                // if any single final usage found dont remove
                // else remove

                if (isPrivateVariable) {
                    variableAssignmentCount = getVariableAssignmentCount(ctAssignments, variable, variableAssignmentCount);
                } else if (isPublicVariable) {
                    for (CtType ctClass : allClasses) {
                        final List<CtAssignment> variableAssignments = ctClass.getElements(ctElement -> ctElement instanceof CtAssignment);
                        variableAssignmentCount = getVariableAssignmentCount(variableAssignments, variable, variableAssignmentCount);
                    }
                } else if (isProtectedVariable) {

                    // Check all subclasses Same Package and Different Package
                    for (CtType ctClass : subClasses) {
                        final List<CtAssignment> variableAssignments = ctClass.getElements(ctElement -> ctElement instanceof CtAssignment);
                        variableAssignmentCount = getVariableAssignmentCount(variableAssignments, variable, variableAssignmentCount);
                    }

                    // Check only Same Package classes which are not subclass
                    variableAssignmentCount = getVariableAssigmentCountForPackage(currentPackage, variable, variableAssignmentCount);

                } else if (isDefaultVariable) {
                    // I need to restrict this to only same package where class belongs
                    variableAssignmentCount = getVariableAssigmentCountForPackage(currentPackage, variable, variableAssignmentCount);

                } else if (isBlockVariable) {
                    // Check final Block Variable
                    variableAssignmentCount = getVariableAssignmentCount(ctAssignments, variable, variableAssignmentCount);
                }
                // apply filter here
                if (variableAssignmentCount == 0 || (variableDefaultExpression == null && variableAssignmentCount == 1)) {
                    variable.removeModifier(ModifierKind.FINAL);
                }
            }
        }
    }

    private static int getVariableAssigmentCountForPackage(CtPackage currentPackage, CtVariable variable, int variableAssignmentCount) {
        final List<CtClass> allClassesInsidePackage = currentPackage.getElements(ctElement -> ctElement instanceof CtClass);
        for (CtClass ctClass : allClassesInsidePackage) {
            final List<CtAssignment> variableAssignments = ctClass.getElements(ctElement -> ctElement instanceof CtAssignment);
            variableAssignmentCount = getVariableAssignmentCount(variableAssignments, variable, variableAssignmentCount);
        }
        return variableAssignmentCount;
    }

    private CtPackage getPackage(CtClass<?> classDef) {
        return classDef.getPackage();
    }

    private static int getVariableAssignmentCount(List<CtAssignment> ctAssignments, CtVariable variable, int assigned) {
        for (CtAssignment ctAssignment : ctAssignments
        ) {
            final String assignedVariable = ctAssignment.getAssigned().toString();
            if (assignedVariable.equals(variable.getSimpleName())) {
                assigned++;
            }
        }
        return assigned;
    }


}

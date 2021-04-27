package se.kth.processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtExpression;
import spoon.reflect.declaration.*;
import spoon.reflect.factory.ClassFactory;
import spoon.reflect.factory.PackageFactory;
import spoon.reflect.reference.CtTypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        final String currentClassName = classDef.getSimpleName();
      
        // Get all the classes in the project
        final ClassFactory classFactory = getFactory().Class();
        final List<CtType<?>> allClasses = classFactory.getAll();

        List<CtType> subClasses = new ArrayList<>();
        
        for (CtType ctClass : allClasses
        ) {
                  if (ctClass.getSuperclass() != null) {
                      if(ctClass.getSuperclass().getSimpleName().equals(currentClassName))
                          subClasses.add(ctClass);
                  }
        }

        // Get all the package in the project
        final PackageFactory packageFactory = getFactory().Package();
        // Skip top level packages
        final List<CtPackage> ctPackages = packageFactory.getAll()
                .stream()
                .filter(ctPackage -> ctPackage.getPackages().size() == 0)
                .collect(Collectors.toList());

        // Get all variables in this class
        final List<CtVariable> ctVariables = classDef.getElements(ctElement -> ctElement instanceof CtVariable);
        final List<CtField<?>> fields = classDef.getFields();

        final List<CtAssignment> ctAssignments = classDef.getElements(ctElement -> ctElement instanceof CtAssignment);

        for (CtVariable variable : ctVariables
        ) {

            int assigned = 0;
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
                } else if (isProtectedVariable) {
                    for (CtPackage ctPackage : ctPackages) {
                        final List<CtClass> allClassesInsidePackage = ctPackage.getElements(ctElement -> ctElement instanceof CtClass);
                        for (CtClass ctClass : allClassesInsidePackage) {
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
                    for (CtType ctClass : subClasses) {
                        final List<CtAssignment> variableAssignments = ctClass.getElements(ctElement -> ctElement instanceof CtAssignment);
                        for (CtAssignment ctAssignment : variableAssignments
                        ) {
                            final String assignedVariable = ctAssignment.getAssigned().toString();
                            if (assignedVariable.equals(variable.getSimpleName())) {
                                assigned++;
                            }
                        }
                    }
                } else if (isDefaultVariable) {
                    for (CtPackage ctPackage : ctPackages) {
                        final List<CtClass> allClassesInsidePackage = ctPackage.getElements(ctElement -> ctElement instanceof CtClass);
                        for (CtClass ctClass : allClassesInsidePackage) {
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
                } else if (isBlockVariable) {
                    // Check final Block Variable
                    for (CtAssignment ctAssignment : ctAssignments
                    ) {
                        final String assignedVariable = ctAssignment.getAssigned().toString();
                        if (assignedVariable.equals(variable.getSimpleName())) {
                            assigned++;
                        }
                    }
                }
                // apply filter here
                if (assigned == 0 || (variableDefaultExpression == null && assigned == 1)) {
                    variable.removeModifier(ModifierKind.FINAL);
                }
            }

        }

    }


}

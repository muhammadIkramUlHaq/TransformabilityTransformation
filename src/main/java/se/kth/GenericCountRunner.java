package se.kth;

import spoon.Launcher;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtVariable;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static se.kth.shared.Constants.PROJECT_UNDER_ANALYSIS;

public class GenericCountRunner {

    public static void main(String[] args) {

        String projectName = PROJECT_UNDER_ANALYSIS;

        final String[] configurationForOriginalCode = {
                "--with-imports",
                "-i", "repos/" + projectName + "/src/main/java/",
        };

        final String[] configurationForTransformedGenerics = {
                "--with-imports",
                "-i", "target/transformed/generics/" + projectName + "/",
        };

        final Launcher launcherOriginalCode = new Launcher();
        launcherOriginalCode.setArgs(configurationForOriginalCode);
        launcherOriginalCode.run();

        final Launcher launcherTransformedClasses = new Launcher();
        launcherTransformedClasses.setArgs(configurationForTransformedGenerics);
        launcherTransformedClasses.run();
  /*
        final Launcher launcherTransformedMethods = new Launcher();
        launcherTransformedMethods.setArgs(configuration2);
        launcherTransformedMethods.run();

        final Launcher launcherTransformedClasses = new Launcher();
        launcherTransformedClasses.setArgs(configuration1);
        launcherTransformedClasses.run();

        final Launcher launcherTransformedMethods = new Launcher();
        launcherTransformedMethods.setArgs(configuration2);
        launcherTransformedMethods.run();

        final Launcher launcherTransformedVariables = new Launcher();
        launcherTransformedVariables.setArgs(configuration3);
        launcherTransformedVariables.run();*/

        final List<CtType<?>> allClasses = launcherOriginalCode.getFactory().Class().getAll();
        System.out.println(" <--------- Before Processing -------->");
        getCountsForGenericRemoval(allClasses);

        final List<CtType<?>> allClassesAfterGenericClassRemoval = launcherTransformedClasses.getFactory().Class().getAll();
        System.out.println(" <--------- After Processing -------->");
        getCountsForGenericRemoval(allClassesAfterGenericClassRemoval);

       // final List<CtType<?>> allClassesAfterGenericMethodRemoval = launcherTransformedMethods.getFactory().Class().getAll();
        //getCountsForGenericRemoval(allClassesAfterGenericMethodRemoval);
/*
        final List<CtType<?>> allClassesAfterFinalClassRemoval = launcherTransformedClasses.getFactory().Class().getAll();
        getCountsForFinalRemoval(allClassesAfterFinalClassRemoval);

        final List<CtType<?>> allClassesAfterFinalMethodRemoval = launcherTransformedMethods.getFactory().Class().getAll();
        getCountsForFinalRemoval(allClassesAfterFinalMethodRemoval);

        final List<CtType<?>> allClassesAfterFinalVariableRemoval = launcherTransformedVariables.getFactory().Class().getAll();
        getCountsForFinalRemoval(allClassesAfterFinalVariableRemoval);*/
    }

    private static void getCountsForGenericRemoval(List<CtType<?>> allClasses) {
        final long totalCtStatementsCount = allClasses.stream()
                .flatMap(ctType -> {
                    final List<CtStatement> elements = ctType.getElements(ctElement -> ctElement instanceof CtStatement);
                    return elements.stream();
                }).collect(Collectors.toList())
                .stream().count();

        System.out.println("Total Number of Classes =" + allClasses.size());
        System.out.println("Total Number of CtStatements = " + totalCtStatementsCount);
        final long genericClassCounter = allClasses
                .stream()
                .filter(ctType -> isNotEmpty(ctType.getFormalCtTypeParameters())).count();

        System.out.println("Generic Classes Count = " + genericClassCounter);

        final long genericMethodCounter = allClasses.stream()
                .flatMap(ctType -> ctType.getMethods()
                        .stream()
                        .filter(ctMethod -> isNotEmpty(ctType.getFormalCtTypeParameters())))
                .collect(Collectors.toList())
                .stream().count();
        System.out.println("Generic Methods Count = " + genericMethodCounter);

        final long genericVariableCounter = allClasses.stream()
                .flatMap(ctType -> {
                    final List<CtVariable> elements = ctType.getElements(ctElement -> ctElement instanceof CtVariable);
                    return elements.stream().filter(variable -> isNotEmpty(variable.getType()) && variable.getType().isGenerics());
                }).collect(Collectors.toList())
                .stream().count();
        System.out.println("Generic Variables Count = " + genericVariableCounter);


    }

}

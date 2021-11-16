package se.kth;

import spoon.Launcher;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtType;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

public class GenericCountRunner {

    public static void main(String[] args) {

        String projectName = "DiskLruCache";

        final String[] configuration1 = {
                "--with-imports",
                "-i", "target/transformed/classes/",
        };

        final String[] configuration2 = {
                "--with-imports",
                "-i", "target/transformed/methods/",
        };

        final String[] configuration3 = {
                "--with-imports",
                "-i", "target/transformed/variables/",
        };

        final String[] configuration = {
                "--with-imports",
                "-i", "repos/" + projectName + "/src/main/java/",
        };


        final Launcher launcher = new Launcher();
        launcher.setArgs(configuration);
        launcher.run();
/*
        final Launcher launcherTransformedClasses = new Launcher();
        launcherTransformedClasses.setArgs(configuration1);
        launcherTransformedClasses.run();

        final Launcher launcherTransformedMethods = new Launcher();
        launcherTransformedMethods.setArgs(configuration2);
        launcherTransformedMethods.run();

        final Launcher launcherTransformedVariables = new Launcher();
        launcherTransformedVariables.setArgs(configuration3);
        launcherTransformedVariables.run();*/

        final List<CtType<?>> allClasses = launcher.getFactory().Class().getAll();
        getCountsForGenericRemoval(allClasses);
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

      /*  final long finalMethodCounter = allClasses.stream()
                .flatMap(ctType -> ctType.getMethods()
                        .stream()
                        .filter(ctMethod -> ctMethod.isFinal()))
                .collect(Collectors.toList())
                .stream().count();
        System.out.println("Final Methods Count = " + finalMethodCounter);

        final long finalVariableCounter = allClasses.stream()
                .flatMap(ctType -> {
                    final List<CtVariable> elements = ctType.getElements(ctElement -> ctElement instanceof CtVariable);
                    return elements.stream().filter(variable -> variable.isFinal() && variable.toString().contains("final"));
                }).collect(Collectors.toList())
                .stream().count();
        System.out.println("Final Variables Count = " + finalVariableCounter);*/


    }

}
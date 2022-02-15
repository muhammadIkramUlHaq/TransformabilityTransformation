package se.kth;

import spoon.Launcher;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtVariable;

import java.util.List;
import java.util.stream.Collectors;

public class FinalCountRunner {
    public static void main(String[] args) {

        String projectName = "Bukkit";

        final String[] configurationForTransformedClasses = {
                "--with-imports",
                "-i", "target/transformed/classes/",
        };

        final String[] configurationForTransformedMethods = {
                "--with-imports",
                "-i", "target/transformed/methods/",
        };

        final String[] configurationForTransformedVariables = {
                "--with-imports",
                "-i", "target/transformed/variables/",
        };

        final String[] configurationForOriginalCode = {
                "--with-imports",
                "-i", "repos/" + projectName + "/src/main/java/",
        };


        final Launcher launcher = new Launcher();
        launcher.setArgs(configurationForOriginalCode);
        launcher.run();
/*
        final Launcher launcherTransformedClasses = new Launcher();
        launcherTransformedClasses.setArgs(configurationForTransformedClasses);
        launcherTransformedClasses.run();

        final Launcher launcherTransformedMethods = new Launcher();
        launcherTransformedMethods.setArgs(configurationForTransformedMethods);
        launcherTransformedMethods.run();

        final Launcher launcherTransformedVariables = new Launcher();
        launcherTransformedVariables.setArgs(configurationForTransformedVariables);
        launcherTransformedVariables.run();*/

        final String[] configurationForTransformedFinal = {
                "--with-imports",
                "-i", "target/transformed/final/",
        };

        final Launcher launcherTransformedFinal = new Launcher();
        launcherTransformedFinal.setArgs(configurationForTransformedFinal);
        launcherTransformedFinal.run();


        final Launcher launcherTransformedVariables = new Launcher();
        launcherTransformedVariables.setArgs(configurationForTransformedVariables);
        launcherTransformedVariables.run();
        
        final List<CtType<?>> allClasses = launcher.getFactory().Class().getAll();
        getCountsForFinalRemoval(allClasses);

    /*    final List<CtType<?>> allClassesAfterFinalClassRemoval = launcherTransformedClasses.getFactory().Class().getAll();
        getCountsForFinalRemoval(allClassesAfterFinalClassRemoval);

        final List<CtType<?>> allClassesAfterFinalMethodRemoval = launcherTransformedMethods.getFactory().Class().getAll();
        getCountsForFinalRemoval(allClassesAfterFinalMethodRemoval);

        final List<CtType<?>> allClassesAfterFinalVariableRemoval = launcherTransformedVariables.getFactory().Class().getAll();
        getCountsForFinalRemoval(allClassesAfterFinalVariableRemoval);*/

        final List<CtType<?>> allAfterFinalVariableRemoval = launcherTransformedFinal.getFactory().Class().getAll();
        getCountsForFinalRemoval(allAfterFinalVariableRemoval);
        
        final List<CtType<?>> allClassesAfterFinalVariableRemoval = launcherTransformedVariables.getFactory().Class().getAll();
        getCountsForFinalRemoval(allClassesAfterFinalVariableRemoval);
    }

    private static void getCountsForFinalRemoval(List<CtType<?>> allClasses) {
        final long totalCtStatementsCount = allClasses.stream()
                .flatMap(ctType -> {
                    final List<CtStatement> elements = ctType.getElements(ctElement -> ctElement instanceof CtStatement);
                    return elements.stream();
                }).collect(Collectors.toList())
                .stream().count();

        System.out.println("Total Number of Classes =" + allClasses.size());
        System.out.println("Total Number of CtStatements = " + totalCtStatementsCount);
        final long finalClassCounter = allClasses
                .stream()
                .filter(ctType -> ctType.isFinal()).count();
        System.out.println("Final Classes Count = " + finalClassCounter);

        final long finalMethodCounter = allClasses.stream()
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
        System.out.println("Final Variables Count = " + finalVariableCounter);


    }

}

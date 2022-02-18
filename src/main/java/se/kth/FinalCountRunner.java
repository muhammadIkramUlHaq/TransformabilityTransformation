package se.kth;

import spoon.Launcher;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtVariable;

import java.util.List;
import java.util.stream.Collectors;

import static se.kth.shared.Constants.PROJECT_UNDER_ANALYSIS;

public class FinalCountRunner {
    public static void main(String[] args) {

        String projectName = PROJECT_UNDER_ANALYSIS;

        final String[] configurationForOriginalCode = {
                "--with-imports",
                "-i", "repos/" + projectName + "/src/main/java/",
        };

        final String[] configurationForTransformedFinal = {
                "--with-imports",
                "-i", "target/transformed/final/" + projectName + "/",
        };

        
        final Launcher launcherOriginalCode = new Launcher();
        launcherOriginalCode.setArgs(configurationForOriginalCode);
        launcherOriginalCode.run();
        
        final Launcher launcherTransformedFinal = new Launcher();
        launcherTransformedFinal.setArgs(configurationForTransformedFinal);
        launcherTransformedFinal.run();
        
        final List<CtType<?>> allClasses = launcherOriginalCode.getFactory().Class().getAll();
        System.out.println(" <--------- Before Processing -------->");
        getCountsForFinalRemoval(allClasses);

        final List<CtType<?>> allAfterFinalRemoval = launcherTransformedFinal.getFactory().Class().getAll();
        System.out.println(" <--------- After Processing -------->");
        getCountsForFinalRemoval(allAfterFinalRemoval);
        
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

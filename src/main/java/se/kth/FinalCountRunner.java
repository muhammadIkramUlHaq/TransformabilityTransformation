package se.kth;

import spoon.Launcher;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtVariable;

import java.util.List;
import java.util.stream.Collectors;

public class FinalCountRunner {
    public static void main(String[] args) {
        final String[] configuration1 = {
                "-i", "target/transformed/se/kth/resources/",
        };

        final String[] configuration = {
                "-i", "src/main/java/se/kth/resources/",
        };
        

        final Launcher launcher = new Launcher();
        launcher.setArgs(configuration);
        launcher.run();
        
        final List<CtType<?>> allClasses = launcher.getFactory().Class().getAll();

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

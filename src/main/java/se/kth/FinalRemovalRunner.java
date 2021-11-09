package se.kth;

import spoon.Launcher;

public class FinalRemovalRunner {
    public static void main(String[] args) {
        String projectName = "DiskLruCache";
        final String[] configurationClasses;
        configurationClasses = new String[]{
                "-i", "repos/"+ projectName + "/src/main/java/",
                "-o", "target/transformed/classes/",
                "-p", "se.kth.processor.finals.FinalClassRemovalProcessor" 
        };

        final String[] configurationMethods = {
                "--with-imports",
                "-i", "repos/"+ projectName + "/src/main/java/",
                "-o", "target/transformed/methods/",
                "-p", "se.kth.processor.finals.FinalMethodRemovalProcessor"
        };

        final String[] configurationVariables = {
                "--with-imports",
                "-i", "repos/"+ projectName + "/src/main/java/",
                "-o", "target/transformed/variables/",
                "-p", "se.kth.processor.finals.FinalVariableRemovalProcessor"
        };

        final Launcher launcherFinalClassesRemoval = new Launcher();
        launcherFinalClassesRemoval.setArgs(configurationClasses);
        launcherFinalClassesRemoval.run();

        final Launcher launcherFinalMethodsRemoval = new Launcher();
        launcherFinalMethodsRemoval.setArgs(configurationMethods);
        launcherFinalMethodsRemoval.run();

        final Launcher launcherFinalVariablesRemoval = new Launcher();
        launcherFinalVariablesRemoval.setArgs(configurationVariables);
        launcherFinalVariablesRemoval.run();

        System.out.println(" Successfully Run All processor on complete porject");
    }
}

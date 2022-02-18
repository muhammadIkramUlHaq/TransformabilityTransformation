package se.kth;

import spoon.Launcher;

import static se.kth.shared.Constants.PROJECT_UNDER_ANALYSIS;

public class GenericRemovalRunner {
    public static void main(String[] args) {

        String projectName = PROJECT_UNDER_ANALYSIS;

        final String[] configurationGenerics = new String[]{
                "-i", "repos/" + projectName + "/src/main/java/",
                "-o", "target/transformed/generics/" + projectName + "/",
                "-p", "se.kth.processor.generics.GenericRemovalProcessor"
        };

        final Launcher launcherFinalRemoval = new Launcher();
        launcherFinalRemoval.setArgs(configurationGenerics);
        launcherFinalRemoval.run();
        
      /*  final String[] configurationClasses;
        configurationClasses = new String[]{
                "--with-imports",
                "-i", "src/main/java/se/kth/resources/package3",
                "-o", "target/transformed/classes/",
                "-p", "se.kth.processor.generics.GenericRemovalProcessor"
        };

        final Launcher launcherGenericClassesRemoval = new Launcher();
        launcherGenericClassesRemoval.setArgs(configurationClasses);
        launcherGenericClassesRemoval.run();*/

        /*String projectName = "commons-collections-master";
        final String[] configurationClasses;
        configurationClasses = new String[]{
                "-i", "projects/" + projectName + "/src/main/java/",
                "-o", "target/transformed/classes/",
                "-p", "se.kth.processor.generics.GenericClassRemovalProcessor"
        };

        final Launcher launcherGenericClassesRemoval = new Launcher();
        launcherGenericClassesRemoval.setArgs(configurationClasses);
        launcherGenericClassesRemoval.run();     */


     /*   final String[] configurationMethods = {
                "--with-imports",
                "-i", "repos/" + projectName + "/src/main/java/",
                "-o", "target/transformed/classes/",
                "-p", "se.kth.processor.generics.GenericClassRemovalProcessor"
        };

        final Launcher launcherGenericMethodsRemoval = new Launcher();
        launcherGenericMethodsRemoval.setArgs(configurationMethods);
        launcherGenericMethodsRemoval.run();  */

      /*  String projectName = "DiskLruCache";
        
        final String[] configurationClasses;
        configurationClasses = new String[]{
                "-i", "repos/" + projectName + "/src/main/java/",
                "-o", "target/transformed/classes/",
                "-p", "se.kth.processor.generics.GenericClassRemovalProcessor"
        };

        final String[] configurationMethods = {
                "--with-imports",
                "-i", "repos/" + projectName + "/src/main/java/",
                "-o", "target/transformed/methods/",
                "-p", "se.kth.processor.generics.GenericMethodRemovalProcessor"
        };

        final String[] configurationVariables = {
                "--with-imports",
                "-i", "repos/" + projectName + "/src/main/java/",
                "-o", "target/transformed/variables/",
                "-p", "se.kth.processor.generics.GenericVariableRemovalProcessor"
        };

        final Launcher launcherGenericClassesRemoval = new Launcher();
        launcherGenericClassesRemoval.setArgs(configurationClasses);
        launcherGenericClassesRemoval.run();

        final Launcher launcherGenericMethodsRemoval = new Launcher();
        launcherGenericMethodsRemoval.setArgs(configurationMethods);
        launcherGenericMethodsRemoval.run();

        final Launcher launcherGenericVariablesRemoval = new Launcher();
        launcherGenericVariablesRemoval.setArgs(configurationVariables);
        launcherGenericVariablesRemoval.run();

        System.out.println(" Successfully Run All processor on complete porject");*/
        System.out.println(" Successfully Run processor on complete porject");
    }
}

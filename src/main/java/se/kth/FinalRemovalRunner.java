package se.kth;

import spoon.Launcher;

import static se.kth.shared.Constants.PROJECT_UNDER_ANALYSIS;

public class FinalRemovalRunner {
    public static void main(String[] args) {
        String projectName = PROJECT_UNDER_ANALYSIS;

        final String[] configurationFinal = new String[]{
                "-i", "repos/" + projectName + "/src/main/java/",
                "-o", "target/transformed/final/" + projectName + "/",
                "-p", "se.kth.processor.finals.FinalRemovalProcessor"
        };

        final Launcher launcherFinalRemoval = new Launcher();
        launcherFinalRemoval.setArgs(configurationFinal);
        launcherFinalRemoval.run();

        System.out.println(" Successfully Run All processor on complete porject");
    }
}

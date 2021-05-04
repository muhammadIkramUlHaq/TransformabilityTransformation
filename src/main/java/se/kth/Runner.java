package se.kth;

import spoon.Launcher;

public class Runner {
    public static void main(String[] args) {
        final String[] configuration = {
                "-i", "src/main/java/se/kth/resources/",
                "-o", "target/transformed/",
                "-p", "se.kth.processor.FinalVariableRemovalProcessor",
                "--compile"
        };

        final Launcher launcher = new Launcher();
        launcher.setArgs(configuration);
        launcher.run();
    }
}

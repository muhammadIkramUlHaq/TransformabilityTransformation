package se.kth;

import spoon.Launcher;

public class Runner {
    public static void main(String[] args) {
        final String[] configuration = {
                "-i", "src/main/resources/project/",
                "-o", "target/transformed/",
                "-p", "se.kth.processor.FinalClassRemovalProcessor",
                "--compile"
        };

        final Launcher launcher = new Launcher();
        launcher.setArgs(configuration);
        launcher.run();
    }
}

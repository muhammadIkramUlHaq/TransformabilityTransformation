package files.variables;

import java.awt.*;

public class TestInnerClass {
    static final Label label = new Label();
    private interface HelloWorld {
        public void greet();
        public void greetSomeone(String someone);
    }

    private int count = 1;
    
    class EnglishGreeting implements HelloWorld {
        String name = "world";
        final static int count1 = 0;
        public void greet() {
            greetSomeone("world");
        }
        public void greetSomeone(String someone) {
            name = someone;
            count++;
            System.out.println("Hello " + name + count + count1);
        }
    }

    HelloWorld frenchGreeting = new HelloWorld() {
        String name = "tout le monde";
        final static int count1 = 0;
        public void greet() {
            greetSomeone("tout le monde");
        }
        public void greetSomeone(String someone) {
            name = someone;
            count++;
            label.setText("ewww");
            System.out.println("Salut " + name + count);
        }
    };

    public void sayHello() {
        int localCount ;
        localCount = 0;
       HelloWorld spanishGreeting = new HelloWorld() {
            String name = "mundo";
           final static int count1 = 0;
            public void greet() {
                greetSomeone("mundo");
            }
            public void greetSomeone(String someone) {
                name = someone;
                System.out.println("Hola, " + name + localCount);
            }
        };
        HelloWorld englishGreeting = new EnglishGreeting();
        englishGreeting.greet();
        frenchGreeting.greetSomeone("Fred");
        spanishGreeting.greet();
    }

    public static void main(String... args) {
        TestInnerClass myApp =
                new TestInnerClass();
        myApp.sayHello();
    }
}


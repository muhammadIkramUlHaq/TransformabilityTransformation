package files.variables.package1;

import files.variables.package1.Test4Variable;
import files.variables.package1.Test5Variable;

public class Test3Variable extends Test4Variable {
    public final static int a = 7;

    public void method3() {
        int  m = 10 + b - Test5Variable.c;
    }
}

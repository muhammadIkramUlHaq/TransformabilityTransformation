package files.variables.package2;

import files.variables.package1.Test4Variable;

public class TestProtected extends Test4Variable{
    
    public final int method1() {
        int m;
        m = 13 + Test4Variable.b;
        return m;
    }
}

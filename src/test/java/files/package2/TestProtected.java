package files.package2;

import files.package1.Test4Variable;

public class TestProtected extends Test4Variable{
    
    public final int method1() {
        int m;
        m = 13 + Test4Variable.b;
        return m;
    }
}

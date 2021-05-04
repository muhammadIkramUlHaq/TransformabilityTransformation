package se.kth.shared;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtType;

import java.util.ArrayList;
import java.util.List;

public class Utility {

    /**
     * Get the Name of Provided Class
     * @param classDef
     * @return
     */
    public static String getClassName(CtClass<?> classDef) {
        return classDef.getSimpleName();
    }

    /**
     * Get List of Subclass for given Class
     * @param currentClassName
     * @param allClasses
     * @return
     */

    public static List<CtType> getSubclassList(String currentClassName, List<CtType<?>> allClasses) {
        List<CtType> subClasses = new ArrayList<>();
        for (CtType ctClass : allClasses
        ) {
            if (ctClass.getSuperclass() != null) {
                if (ctClass.getSuperclass().getSimpleName().equals(currentClassName))
                    subClasses.add(ctClass);
            }
        }
        return subClasses;
    }
}

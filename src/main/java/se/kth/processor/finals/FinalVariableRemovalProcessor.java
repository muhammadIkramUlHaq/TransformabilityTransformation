package se.kth.processor.finals;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.*;

/**
 * Final Variable Removal
 *
 * @author Muhammad Ikram Ul Haq
 */
public class FinalVariableRemovalProcessor extends AbstractProcessor<CtVariable<?>> {

    @Override
    public boolean isToBeProcessed(CtVariable<?> ctVariable) {
        return ctVariable.isFinal();
    }

    @Override
    public void process(CtVariable<?> variable) {
        // Find static variable inside inner class- it must be final and not removed
        // •	Analysis for final variable removal
        //o	In case of Inner Class or anonymous class, we can remove the final keyword because according to java language specification the variable being used by anonymous class within enclosing brackets where it is define. It has to be final or effectively final.
        //o	In case of nested Enum inside class, all ENUM values are implicitly static and final. So we don’t need to process these. We can remove the final keyword from static final or final members if it fulfills the language specification.
        //o	As summary, All final keywords for variables can be removed expect this use case :
        //•	If the Inner lass or anonymous class has a static member then it must be explicitly final otherwise it will be compile time error.
        final CtElement parent = variable.getParent().getParent();
        if (!variable.isStatic() || (parent instanceof CtPackage) || variable.getParent() instanceof CtEnum) {
            variable.removeModifier(ModifierKind.FINAL);
        }
    }

}

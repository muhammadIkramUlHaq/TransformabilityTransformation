package se.kth.processor.finals;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtStatement;

/**
 * Ct Statement Counter
 *
 * @author Muhammad Ikram Ul Haq
 */
public class CtStatementProcessor extends AbstractProcessor<CtStatement> {
           int count = 0;
    @Override
    public boolean isToBeProcessed(CtStatement statement) {
        return statement != null;
    }

    @Override
    public void process(CtStatement classDef) {
        // Count No of Statements
      count++;
    }
}

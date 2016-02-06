package sample.junit5;

import org.junit.gen5.api.extension.ConditionEvaluationResult;
import org.junit.gen5.api.extension.TestExecutionCondition;
import org.junit.gen5.api.extension.TestExtensionContext;

public class MyExtend implements TestExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluate(TestExtensionContext context) {
        String displayName = context.getDisplayName();
        
        if ("hoge".equals(displayName)) {
            return ConditionEvaluationResult.enabled("hoge なので");
        } else {
            return ConditionEvaluationResult.disabled("hoge じゃないので");
        }
    }
}

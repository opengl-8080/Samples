package sample.junit5;

import org.junit.gen5.api.extension.ConditionEvaluationResult;
import org.junit.gen5.api.extension.ContainerExecutionCondition;
import org.junit.gen5.api.extension.ContainerExtensionContext;

public class MyExtend implements ContainerExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluate(ContainerExtensionContext context) {
        String displayName = context.getDisplayName();
        
        if (displayName.contains("Hoge")) {
            return ConditionEvaluationResult.enabled("Hoge なので");
        } else {
            return ConditionEvaluationResult.disabled("Hoge でないので");
        }
    }

}

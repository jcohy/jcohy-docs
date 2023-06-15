package rsb.reactor.aot;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.core.NativeDetector;

public class DisabledInGraalVmCondition implements ExecutionCondition {

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		var isNative = NativeDetector.inNativeImage();
		if (isNative) {
			return ConditionEvaluationResult.disabled("Test disabled in a GraalVM native image context");
		}
		else {
			return ConditionEvaluationResult.enabled("Test enabled");
		}
	}

}
package com.ssafy.tripchat.common.elparser;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class CustomSpELParser {
    public static <T> T getDynamicValue(String[] parameterNames, Object[] args, String name, Class<T> targetType) {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();

        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }

        return parser.parseExpression(name).getValue(context, targetType);
    }
}

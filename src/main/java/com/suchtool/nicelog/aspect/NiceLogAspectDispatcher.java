package com.suchtool.nicelog.aspect;

import com.suchtool.nicelog.constant.NiceLogPointcutExpression;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

@Component
public class NiceLogAspectDispatcher {
    @Autowired
    private List<LogAspectProcessor> logAspectProcessors;

    public LogAspectProcessor findMatched(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        LogAspectProcessor matchedProcessor = null;
        for (LogAspectProcessor logAspectProcessor : logAspectProcessors) {
            boolean match = match(logAspectProcessor.pointcutExpression(), method);
            if (match && !logAspectProcessor.pointcutExpression().equals(
                    NiceLogPointcutExpression.NICE_LOG_ANNOTATION_ASPECT)) {
                matchedProcessor = logAspectProcessor;
                return matchedProcessor;
            }
        }

        return null;
    }

    /**
     * 判断方法是否匹配切点表达式
     * 先判断类，如果类匹配则匹配；如果不匹配，继续匹配方法，返回方法是否匹配
     *
     * @param pointCutExpression 切点表达式
     * @param method             方法
     * @return 是否匹配
     */
    private boolean match(String pointCutExpression, Method method) {
        // 这里必须new一个，不能公用。因为它的setExpression只能调一次
        AspectJExpressionPointcut expressionPointcut = new AspectJExpressionPointcut();
        expressionPointcut.setExpression(pointCutExpression);
        Class<?> declaringClass = method.getDeclaringClass();
        return expressionPointcut.getMethodMatcher().matches(method, declaringClass);
    }
}

package com.suchtool.nicelog.aspect;

import com.suchtool.nicelog.annotation.NiceLog;
import com.suchtool.nicelog.annotation.NiceLogIgnore;
import com.suchtool.nicelog.constant.EntryTypeEnum;
import com.suchtool.nicelog.property.NiceLogProperty;
import com.suchtool.nicetool.util.spring.ApplicationContextHolder;
import org.aspectj.lang.JoinPoint;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

public abstract class NiceLogAspectProcessor implements NiceLogParamProvider {

    /**
     * 判断是否需要处理
     */
    public boolean requireProcess(Method method) {
        NiceLogProperty niceLogProperty = ApplicationContextHolder.getContext()
                .getBean(NiceLogProperty.class);

        Class<?> declaringClass = method.getDeclaringClass();

        // 如果不是收集所有，又不是@NiceLog注解的，则不处理
        if (!niceLogProperty.getCollectAll()) {
            if (!method.isAnnotationPresent(NiceLog.class)
                    && !declaringClass.isAnnotationPresent(NiceLog.class)) {
                return false;
            }
        }

        // 如果指定了不收集日志的Feign的包，则不收集。多个用,隔开
        if (EntryTypeEnum.FEIGN.equals(provideEntryType())) {
            String ignoreFeignLogPackageName = niceLogProperty.getIgnoreFeignLogPackageName();
            if (StringUtils.hasText(ignoreFeignLogPackageName)) {
                String[] split = ignoreFeignLogPackageName.split(",");
                for (String packageName : split) {
                    if (declaringClass.getName().startsWith(packageName.trim())) {
                        return false;
                    }
                }
            }

        }

        // 如果类或方法上有NiceLogIgnore，则不处理
        return !method.isAnnotationPresent(NiceLogIgnore.class)
                && !declaringClass.isAnnotationPresent(NiceLogIgnore.class);
    }

    public abstract void before(JoinPoint joinPoint);

    public abstract void afterReturning(JoinPoint joinPoint, Object returnValue);

    public abstract void afterThrowing(JoinPoint joinPoint, Throwable throwingValue);

    /**
     * 正常返回或者抛异常的处理
     */
    public abstract void returningOrThrowingProcess();

    public abstract String pointcutExpression();
}
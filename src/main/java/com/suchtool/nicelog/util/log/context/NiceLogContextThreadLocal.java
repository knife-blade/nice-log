package com.suchtool.nicelog.util.log.context;

/**
 * 接口日志的上下文信息的读写
 */
public class NiceLogContextThreadLocal {
    /**
     * 构造函数私有
     */
    private NiceLogContextThreadLocal() {

    }

    private static final ThreadLocal<NiceLogContext>
            LOG_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 清除日志上下文信息
     */
    public static void clear() {
        LOG_CONTEXT_THREAD_LOCAL.remove();
    }

    /**
     * 存储日志上下文信息
     */
    public static void write(NiceLogContext niceLogContext) {
        LOG_CONTEXT_THREAD_LOCAL.set(niceLogContext);
    }

    /**
     * 获取当前日志上下文信息
     */
    public static NiceLogContext read() {
        return LOG_CONTEXT_THREAD_LOCAL.get();
    }
}
package com.suchtool.betterlog.process.impl;

import com.suchtool.betterlog.process.BetterLogProcess;
import com.suchtool.betterlog.util.log.inner.bo.LogInnerBO;
import com.suchtool.betterutil.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BetterLogProcessDefaultImpl implements BetterLogProcess {
    @Override
    public void process(LogInnerBO logInnerBO) {
        // 本处只是将日志打印出来。实际项目可以将日志上传到ES。
        switch (logInnerBO.getLevel()) {
            case INFO:
                log.info("betterLog日志：{}", JsonUtil.toJson(logInnerBO));
                break;
            case WARNING:
                log.warn("betterLog日志：{}", JsonUtil.toJson(logInnerBO));
                break;
            case ERROR:
                log.error("betterLog日志：{}", JsonUtil.toJson(logInnerBO));
                break;
        }
    }
}
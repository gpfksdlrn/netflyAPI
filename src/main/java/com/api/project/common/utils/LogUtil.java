package com.api.project.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@Component
public class LogUtil {
    public static String name = "netfly_api";

    public static void info(String className, String str) {
        className = "[" + className + "]";
        String msg = className + str;
        log.info(msg);
    }

    public static String error(String className, String str) {
        className = "[" + className + "]";
        String msg = className + str;
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        String method = (elements.length > 2 ? elements[2].toString() : "(no caller)");
        msg += "[" + method + "]";
        log.error(msg);

        return msg;
    }

    public static void sendError(String className, String str, Throwable throwable) {
        String errorMsg = error(className, str);
        log.error(errorMsg, throwable);

        // Convert stack trace to string and limit its length
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        String stackTrace = sw.toString();

        // Limit the stack trace to the first few lines
        String[] stackTraceLines = stackTrace.split("\n");
        StringBuilder limitedStackTrace = new StringBuilder();
        for (int i = 0; i < Math.min(stackTraceLines.length, 10); i++) {
            limitedStackTrace.append(stackTraceLines[i]).append("\n");
        }

        // Construct detailed error message
        String detailedErrorMsg = errorMsg + "\n" + limitedStackTrace;

        ApiUtil.sendTelegram(detailedErrorMsg);
    }
}

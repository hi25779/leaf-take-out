package com.lhy.leaftakeout.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author : Jesse(lhy)
 * @mail : 859028027@qq.com
 * @created : 2022-11-20
 **/
@RestControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException e) {
        log.error(e.getMessage());

        if (e.getMessage().contains("Duplicate entry")) {
            String msg = e.getMessage().split(" ")[2];
            msg = msg.substring(1, msg.length() - 1);
            return R.error(msg);
        }
        return R.error("Unknown error");
    }
}

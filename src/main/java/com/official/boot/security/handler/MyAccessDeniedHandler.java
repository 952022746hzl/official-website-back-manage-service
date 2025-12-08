package com.official.boot.security.handler;

import com.official.boot.core.web.ResultCode;
import com.official.boot.core.web.WebResponseHelper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 无权限访问处理器
 *
 * @author Ray.Hao
 * @since 2.0.0
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        WebResponseHelper.writeError(response, ResultCode.ACCESS_UNAUTHORIZED);
    }

}

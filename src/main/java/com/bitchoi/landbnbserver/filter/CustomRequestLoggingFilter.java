package com.bitchoi.landbnbserver.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

@Component
public class CustomRequestLoggingFilter extends CommonsRequestLoggingFilter {

    public CustomRequestLoggingFilter() {
        this.setIncludeQueryString(true);
        this.setIncludePayload(true);
        this.setIncludeHeaders(false);
        this.setMaxPayloadLength(1024);
        this.setAfterMessagePrefix("REQUEST DATA: ");
    }

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return logger.isDebugEnabled();
    }
    @Override
    protected void beforeRequest(HttpServletRequest request,String message) {

    }
}

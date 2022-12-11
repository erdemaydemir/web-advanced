package com.forguta.libs.web.common.feign;

import feign.Response;

public interface FeignHttpExceptionHandler {
    Exception handle(Response response) throws Exception;
}
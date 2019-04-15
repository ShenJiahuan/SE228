package com.shenjiahuan.eBook.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HandlerResponse {

    private Object result;

    private boolean success;

    public HandlerResponse(Object result, boolean success){
        this.result = result;
        this.success = success;
    }
}

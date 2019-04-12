package com.shenjiahuan.eBook.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HandlerResponse {

    private Object message;

    private boolean success;

    public HandlerResponse(Object message, boolean success){
        this.message = message;
        this.success = success;
    }
}

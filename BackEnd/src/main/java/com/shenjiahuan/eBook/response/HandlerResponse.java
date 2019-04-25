package com.shenjiahuan.eBook.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HandlerResponse {

    private Object result;

    /*
     * status:
     *  0 - Success
     * -1 - Unauthorized
     * -2 - Incorrect parameters
     */
    private int status;

    public HandlerResponse(Object result, int status){
        this.result = result;
        this.status = status;
    }
}

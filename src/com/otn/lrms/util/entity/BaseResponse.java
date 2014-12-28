
package com.otn.lrms.util.entity;

public class BaseResponse<T> extends BaseResponseHeader {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}

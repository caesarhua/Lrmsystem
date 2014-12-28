
package com.otn.lrms.util.net;

import com.otn.lrms.util.Constants;
import com.otn.lrms.util.entity.BaseResponseHeader;

public class Result {
    private String statusCode = Constants.HTTP_NET_ERROR;

    private String jsonString;

    private BaseResponseHeader head;

    private Object body;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public BaseResponseHeader getHead() {
        return head;
    }

    public void setHead(BaseResponseHeader head) {
        this.head = head;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

}

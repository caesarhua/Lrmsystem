
package com.otn.lrms.util.net;

public interface DataReqObserver {

    void onResponseSucess(String method, Result result);

    void onResponseError(String method, Result result);

}

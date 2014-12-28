
package com.otn.lrms.util.net;

import com.otn.lrms.util.Config;
import com.otn.lrms.util.Constants;
import com.otn.lrms.util.entity.BaseResponseHeader;
import com.otn.lrms.util.helper.LogUtil;
import com.otn.lrms.util.helper.ParseHelper;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;

import java.util.List;

public class DataEnginer implements IHttpTaskCallBack {

    private boolean isLoading = false;

    private String method = "";

    private HttpTask mHttpTask;

    private String url = "";

    private List<NameValuePair> nameValuePairs = null;

    /**
     * get方式，设置请求地址
     */
    public void setUrl(String... params) {
        String method = params[0];

        String rest = "/rest";
        String rest2 = "/rest2";

        setMethod(method);
        StringBuffer sb = new StringBuffer(Config.getHost());

        if (Constants.METHOD_ANNOUNCE.equals(method)) {
            // 公告例外
            sb.append(rest2).append("/").append(params[0]);
            url = sb.toString();
            return;
        } else {
            sb.append(rest);
        }

        if (Constants.METHOD_AUTH.equals(method)) {
            // 登陆接口
            sb.append("/auth?username=").append(params[1]).append("&password=").append(params[2]);
            url = sb.toString();

        } else if (Constants.METHOD_QUCIKBOOK.equals(method)
                || Constants.METHOD_FREEBOOK.equals(method)
                || Constants.METHOD_EXTEND.equals(method)) {
            // post
            // 快速预约、自选预约
            sb.append("/v2/").append(params[0]);
            url = sb.toString();

        } else if (Constants.METHOD_VIEW.equals(method)) {
            // 历史查看
            for (int i = 0; i < params.length; i++) {
                sb.append("/").append(params[i]);
            }
            sb.append("?token=").append(Config.getToken());
            url = sb.toString();

        } else if (Constants.METHOD_ENDTIME.equals(method)) {

            sb.append("/").append(params[0]).append("?seat=").append(params[1]).append("&date=")
                    .append(params[2]).append("&start=").append(params[3]).append("&token=")
                    .append(Config.getToken());

            url = sb.toString();

        } else if (Constants.METHOD_STARTTIME.equals(method)) {
            sb.append("/").append(params[0]).append("?id=").append(params[1]).append("&date=")
                    .append(params[2]).append("&token=").append(Config.getToken());

            url = sb.toString();

        }
        // else if (Constants.METHOD_FILTERS.equals(method))
        // {
        // sb.append("/v2");
        // for (int i = 0; i < params.length; i++)
        // {
        // sb.append("/").append(params[i]);
        // }
        // url = sb.toString();
        // }
        else {
            sb.append("/v2");
            for (int i = 0; i < params.length; i++) {
                sb.append("/").append(params[i]);
            }
            sb.append("?token=").append(Config.getToken());
            url = sb.toString();
        }

    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    private DataReqObserver dataReqObserver;

    public DataEnginer(DataReqObserver dataReqObserver) {
        this.dataReqObserver = dataReqObserver;
    }

    public void request() {
        if (isLoading()) {
            return;
        }
        isLoading = true;
        mHttpTask = new HttpTask(this);
        mHttpTask.setNameValuePairs(nameValuePairs);
        mHttpTask.execute(url);
    }

    public void cancelRequest() {
        if (null != mHttpTask) {
            mHttpTask.cancel(true);
        }
        setLoading(false);
    }

    private boolean isLoading() {
        return this.isLoading;
    }

    private void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void setNameValuePairs(List<NameValuePair> nameValuePairs) {
        this.nameValuePairs = nameValuePairs;
    }

    @Override
    public void requestReturned(final Result result) {
        setLoading(false);
        BaseResponseHeader header = new BaseResponseHeader();
        result.setHead(header);
        try {
            if (result != null && Integer.valueOf(result.getStatusCode()) == HttpStatus.SC_OK) {
                Object resp = ParseHelper.getInstance().parseResp(method, result.getJsonString());
                result.setBody(resp);
                if (resp instanceof BaseResponseHeader) {
                    BaseResponseHeader respHeader = (BaseResponseHeader) resp;
                    header.setCode(respHeader.getCode());
                    header.setMessage(respHeader.getMessage());
                    header.setStatus(respHeader.getStatus());
                } else {
                    header.setCode("0");
                    header.setMessage("");
                    header.setStatus(Constants.SUCCESS);
                }
            } else {
                header.setCode("-10");
                header.setMessage("系统返回错误");
                header.setStatus(Constants.FAIL);
            }

            if (Constants.CODE_TOKEN.equals(header.getCode())
                    || Constants.MSG_TOKEN.equals(header.getMessage())) {
                header.setMessage("登陆超时或者在别处登陆，请退出重新登陆 ");
            }

            if (Constants.SUCCESS.equals(header.getStatus())) {
                dataReqObserver.onResponseSucess(method, result);
            } else {
                dataReqObserver.onResponseError(method, result);
            }

        } catch (Exception e) {
            header.setCode("-1");
            header.setMessage("系统出错");
            header.setStatus(Constants.FAIL);
            dataReqObserver.onResponseError(method, result);
            LogUtil.e("DataEnginer", "fatal=" + e.toString());
        }

    }
}

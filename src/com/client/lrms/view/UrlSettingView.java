
package com.client.lrms.view;

import com.client.lrms.R;
import com.otn.lrms.util.Config;
import com.otn.lrms.util.Constants;
import com.otn.lrms.util.helper.SharedPreferencesHelper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UrlSettingView extends FrameLayout {

    private ImageView webIconView;

    private ImageView ipIconView;

    private TextView webLableText;

    private TextView webHeadText;

    private TextView webEndText;

    private TextView ipLableText;

    private TextView ipMaohaoText;

    private EditText webAddress;

    private EditText ipAddress;

    private EditText portAddress;

    private Button saveBtn;

    private OnClickListener saveBtnClick;

    private boolean isTypeWeb = true;

    public UrlSettingView(Context context) {
        super(context);
        initView(context, R.layout.server_set_dialog);
    }

    public UrlSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UrlSettingView(Context context, int layout) {
        super(context);
        initView(context, layout);
    }

    private void initView(Context context, int layout) {

        View view = View.inflate(context, layout, null);
        LinearLayout ipAndPortInputLayout = (LinearLayout) view.findViewById(R.id.save_ip_port);
        ipAndPortInputLayout.setVisibility(View.VISIBLE);
        addView(view);

        webIconView = (ImageView) findViewById(R.id.web_icon);
        ipIconView = (ImageView) findViewById(R.id.ip_icon);
        webLableText = (TextView) findViewById(R.id.web_lable_text);
        webHeadText = (TextView) findViewById(R.id.web_head_text);
        webEndText = (TextView) findViewById(R.id.web_end_text);
        ipLableText = (TextView) findViewById(R.id.ip_lable_text);
        ipMaohaoText = (TextView) findViewById(R.id.ip_maohao_text);
        ipAddress = (EditText) findViewById(R.id.et_server_ip);
        portAddress = (EditText) findViewById(R.id.et_server_port);
        webAddress = (EditText) findViewById(R.id.et_server_address);
        saveBtn = (Button) findViewById(R.id.save);

        webIconView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                isSelectorView(true);
                isTypeWeb = true;
                webAddress.requestFocus();
            }
        });

        ipIconView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                isSelectorView(false);
                isTypeWeb = false;
                ipAddress.requestFocus();

            }
        });

        webAddress.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    isSelectorView(hasFocus);
                    isTypeWeb = hasFocus;
                }
            }
        });

        ipAddress.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    isSelectorView(!hasFocus);
                    isTypeWeb = !hasFocus;
                }
            }
        });

        portAddress.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    isSelectorView(!hasFocus);
                    isTypeWeb = !hasFocus;
                }
            }
        });
        saveBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                hideKeyboard();
                if (isTypeWeb) {
                    String web = webAddress.getText().toString().trim();
                    SharedPreferencesHelper.setString(Constants.URL_WEB, web);
                    SharedPreferencesHelper.setInt(Constants.SERVER_TYPE,
                            Constants.WEB_ACCESSING_SERVER);
                    String host = getContext().getString(R.string.web_head_text) + web
                            + getContext().getString(R.string.web_end_text);
                    Config.setHost(host);
                } else {
                    String ip = ipAddress.getText().toString().trim();
                    String port = portAddress.getText().toString().trim();
                    SharedPreferencesHelper.setInt(Constants.SERVER_TYPE,
                            Constants.IP_PORT_ACCESSING_SERVER);
                    SharedPreferencesHelper.setString(Constants.URL_IP, ip);
                    SharedPreferencesHelper.setString(Constants.URL_PORT, port);
                    if (port == null || "".equals(port)) {
                        port = "80";
                    }
                    Config.setHost(ip + ":" + port);
                }
                if (saveBtnClick != null) {
                    saveBtnClick.onClick(v);
                }
            }
        });
        // 填充上次保存的数据
        if (SharedPreferencesHelper.getInt(Constants.SERVER_TYPE) == Constants.WEB_ACCESSING_SERVER) {
            webAddress.setText(SharedPreferencesHelper.getString(Constants.URL_WEB));
            webAddress.requestFocus();
        } else {
            ipAddress.setText(SharedPreferencesHelper.getString(Constants.URL_IP));
            portAddress.setText(SharedPreferencesHelper.getString(Constants.URL_PORT));
            ipAddress.requestFocus();
        }
    }

    public void setOnClickListener(OnClickListener saveBtnClick) {
        this.saveBtnClick = saveBtnClick;

    }

    private void isSelectorView(boolean isSelector) {
        webIconView.setBackgroundResource(isSelector == true ? R.drawable.web_pressed
                : R.drawable.web_normal);
        ipIconView.setBackgroundResource(isSelector == true ? R.drawable.ip_normal
                : R.drawable.ip_pressed);
        webLableText.setTextColor(getResources().getColor(
                isSelector == true ? R.color.btn_text : R.color.gray_font));
        webHeadText.setTextColor(getResources().getColor(
                isSelector == true ? R.color.btn_text : R.color.gray_font));
        webEndText.setTextColor(getResources().getColor(
                isSelector == true ? R.color.btn_text : R.color.gray_font));
        ipLableText.setTextColor(getResources().getColor(
                isSelector == true ? R.color.gray_font : R.color.btn_text));
        ipMaohaoText.setTextColor(getResources().getColor(
                isSelector == true ? R.color.gray_font : R.color.btn_text));
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(webAddress.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(ipAddress.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(portAddress.getWindowToken(), 0);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility != View.VISIBLE) {
            hideKeyboard();
        }
    }

}

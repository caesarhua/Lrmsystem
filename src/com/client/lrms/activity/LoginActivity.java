
package com.client.lrms.activity;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.client.lrms.R;
import com.client.lrms.view.CustomDialog;
import com.client.lrms.view.UrlSettingView;
import com.otn.lrms.util.Config;
import com.otn.lrms.util.Constants;
import com.otn.lrms.util.entity.AuthResp;
import com.otn.lrms.util.entity.BaseResponse;
import com.otn.lrms.util.helper.CustomToast;
import com.otn.lrms.util.helper.ParseHelper;
import com.otn.lrms.util.helper.SharedPreferencesHelper;
import com.otn.lrms.util.net.DataEnginer;
import com.otn.lrms.util.net.Result;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

/**
 * @author LuYozone 登录
 */
public class LoginActivity extends BaseNoTitleActivity {

    private Button loginBtn;

    private Button registerBtn;

    private EditText etName;

    private EditText etPassword;

    private TextView serverSet;

    private CheckBox cbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // if (!isNetAvailable())
        // {
        // showSettingDialog();
        // }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveAccountInfo();
    }

    private void test() {

        Date date = new Date(1384329786559L);
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }

        String.valueOf(cal.get(Calendar.YEAR));

        String jsonString = ParseHelper.getInstance().getAssets("layouts.xml");
        ParseHelper.getInstance().parseLayouts(jsonString);

        jsonString = ParseHelper.getInstance().getAssets("endtimes.xml");
        ParseHelper.getInstance().parseEndTime(jsonString);

        jsonString = ParseHelper.getInstance().getAssets("rooms.xml");
        ParseHelper.getInstance().parseRooms(jsonString);

    }

    private void initView() {

        ImageView ivLogo = (ImageView) findViewById(R.id.iv_logo);
        if (new File(Config.LOGO_PATH).exists()) {
            ivLogo.setVisibility(View.VISIBLE);
            ivLogo.setImageURI(Uri.parse(Config.LOGO_PATH));
        } else {
            ivLogo.setVisibility(View.GONE);
        }

        etName = (EditText) findViewById(R.id.et_userName);
        etPassword = (EditText) findViewById(R.id.et_password);

        loginBtn = (Button) findViewById(R.id.login);
        registerBtn = (Button) findViewById(R.id.register);

        serverSet = (TextView) findViewById(R.id.server_set);
        cbRemember = (CheckBox) findViewById(R.id.remember_password);

        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideKeyboard();
                launchLogin();
                // test();

            }
        });

        SharedPreferencesHelper.setBoolean("auto", cbRemember.isChecked());
        cbRemember.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferencesHelper.setBoolean("auto", isChecked);
            }
        });

        findViewById(R.id.rl_parent).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });

        serverSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetDialog(false);
            }
        });
    }

    private void saveAccountInfo() {
        if (SharedPreferencesHelper.getBoolean("auto")) {
            SharedPreferencesHelper.setString("name", etName.getText().toString().trim());
            SharedPreferencesHelper.setString("password", etPassword.getText().toString().trim());
        } else {
            SharedPreferencesHelper.setString("name", "");
            SharedPreferencesHelper.setString("password", "");
        }
    }

    private void launchLogin() {
        // 检查是否输入用户名及密码
        if (TextUtils.isEmpty(etName.getText().toString().trim())
                || TextUtils.isEmpty(etName.getText().toString().trim())) {
            CustomToast.longtShow("请输入用户名及密码");
            return;
        }

        // 检查是否设置服务器地址
        if (TextUtils.isEmpty(Config.getHost())) {
            showSetDialog(true);
            return;
        }

        doLoginReq();

    }

    private void initData() {
        if (SharedPreferencesHelper.getBoolean("auto")) {
            etName.setText(SharedPreferencesHelper.getString("name"));
            etPassword.setText(SharedPreferencesHelper.getString("password"));
            cbRemember.setChecked(true);
        }
    }

    private void showSetDialog(final boolean isLogin) {
        UrlSettingView urlView = new UrlSettingView(this);
        final CustomDialog dialog = new CustomDialog(this);
        urlView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (isLogin) {
                    launchLogin();
                }

            }
        });
        dialog.setContentView(urlView);
        dialog.show();
    }

    private void doLoginReq() {
        showLoading();
        String username = etName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        DataEnginer dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_AUTH, username, password);
        dataEnginer.request();
    }

    /**
     * 隐藏软键盘
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etName.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);
    }

    @Override
    protected void refreshAfterDialog() {

    }

    private boolean isNetAvailable() {
        boolean netSataus = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        cm.getActiveNetworkInfo();
        if (cm.getActiveNetworkInfo() != null) {
            netSataus = cm.getActiveNetworkInfo().isAvailable();
        }
        return netSataus;
    }

    private void showSettingDialog() {
        Builder builder = new Builder(this);
        builder.setTitle("设置网络");
        builder.setMessage("网络没有打开。\n点击\"设置\"按钮进入设置。");
        builder.setPositiveButton("设置", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent i = new Intent();
                i.setAction(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        });
        builder.setNegativeButton("取消", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setCancelable(false);
        builder.create().show();

    }

    @Override
    public void onResponseSucess(String method, Result result) {
        dismissLoading();

        BaseResponse<AuthResp> resp = (BaseResponse<AuthResp>) result.getBody();
        Config.setToken(resp.getData().getToken());
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}

package com.client.lrms.activity;

import com.client.lrms.R;
import com.client.lrms.view.UrlSettingView;
import com.otn.lrms.util.Constants;
import com.otn.lrms.util.helper.CustomToast;
import com.otn.lrms.util.helper.LogUtil;
import com.otn.lrms.util.helper.SharedPreferencesHelper;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MoreActivity extends BaseNoTitleActivity
{
    private LinearLayout about;

    private LinearLayout setIPAndPort;

    private ImageView rightIcon;

    private TextView serverStyleText;

    private boolean isShowInput = true;

    private LinearLayout setContainer;

    private UpgradeCheck checkVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        initView();
    }

    View parentView;

    private void addSetLayout()
    {
        setContainer.removeAllViews();
        parentView = new UrlSettingView(this, R.layout.layout_ipsetting);
        setContainer.addView(parentView);
        parentView.setVisibility(View.GONE);
        parentView.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                isShowSelectorLayout(false);
                showServerStyleText();
            }
        });

    }

    public void initView()
    {
        about = (LinearLayout) findViewById(R.id.about);
        setIPAndPort = (LinearLayout) findViewById(R.id.set_ip_port);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        serverStyleText = (TextView) findViewById(R.id.server_style_text);

        setContainer = (LinearLayout) findViewById(R.id.ll_ipsetting_parent);

        about.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MoreActivity.this, AnnounceActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.ll_set_parent).setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (isShowInput)
                {
                    addSetLayout();
                }
                isShowSelectorLayout(isShowInput);
            }
        });

        showServerStyleText();

        about.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MoreActivity.this, AnnounceActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.ll_version).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (checkVersion != null && checkVersion.isLoading())
                {
                    CustomToast.shortShow("正在检测新版本，请稍候");
                }
                else
                {
                    CustomToast.shortShow("检测新版本，请稍候");
                    checkVersion = new UpgradeCheck(MoreActivity.this);
                    checkVersion.execute();
                }
            }
        });

        findViewById(R.id.ll_us).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MoreActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });

        TextView versionTv = (TextView) findViewById(R.id.version_name);

        versionTv.setText("v" + getVersion());
    }

    private String getVersion()
    {
        PackageManager pm = getPackageManager();
        try
        {
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            return info.versionName;

        }
        catch (NameNotFoundException e)
        {
            LogUtil.e(getClass().getSimpleName(), e.toString());
            return "1.0";
        }
    }

    /**
     * <是否显示访问服务器方式选项> <如果为false则不显示选择项反之则显示选择项>
     * 
     * @param isShow [是否显示]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @author LuYozone
     */
    private void isShowSelectorLayout(boolean isShow)
    {
        if (isShow)
        {
            parentView.setVisibility(View.VISIBLE);
            setIPAndPort.setBackgroundResource(R.drawable.more_item_press);
            rightIcon.setBackgroundResource(R.drawable.go_down_pressed);
            isShowInput = false;
        }
        else
        {
            parentView.setVisibility(View.GONE);
            setIPAndPort.setBackgroundResource(R.drawable.more_item_normal);
            rightIcon.setBackgroundResource(R.drawable.go_right_pressed);
            isShowInput = true;
        }
    }

    /**
     * <设置当前是否显示以及访问服务器的方式> <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @author LuYozone
     */
    private void showServerStyleText()
    {
        if (SharedPreferencesHelper.getInt(Constants.SERVER_TYPE) == Constants.WEB_ACCESSING_SERVER)
        {
            serverStyleText.setText(getResources().getString(R.string.web_index_text));
            serverStyleText.setVisibility(View.VISIBLE);
        }
        else if (SharedPreferencesHelper.getInt(Constants.SERVER_TYPE) == Constants.IP_PORT_ACCESSING_SERVER)
        {
            serverStyleText.setText(getResources().getString(R.string.port_index_text));
            serverStyleText.setVisibility(View.VISIBLE);
        }
        else
        {
            serverStyleText.setVisibility(View.GONE);
        }
    }

    @Override
    protected void refreshAfterDialog()
    {

    }

}

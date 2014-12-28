
package com.client.lrms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.Toast;

import com.client.lrms.R;

public class MainActivity extends BaseActivity {

    private static final String TAB_QUICK = "quick";

    private static final String TAB_CUSTOMER = "customer";

    private static final String TAB_INFO = "info";

    private static final String TAB_ABOUT = "about";

    private LinearLayout bodyLayout;

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);

        setTitleBackVisiable(false);
        initBodyView();
        initTabView();
    }

    private void initBodyView() {
        bodyLayout = (LinearLayout) findViewById(R.id.body_layout);
    }

    private void initTabView() {

        RadioButton rb = (RadioButton) findViewById(R.id.rb_1);
        rb.setOnCheckedChangeListener(new TabChangeListener());
        rb.setChecked(true);

        rb = (RadioButton) findViewById(R.id.rb_2);
        rb.setOnCheckedChangeListener(new TabChangeListener());

        rb = (RadioButton) findViewById(R.id.rb_3);
        rb.setOnCheckedChangeListener(new TabChangeListener());

        rb = (RadioButton) findViewById(R.id.rb_4);
        rb.setOnCheckedChangeListener(new TabChangeListener());

    }

    private class TabChangeListener implements OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Intent intent = new Intent();
            int checkedId = buttonView.getId();
            if (!isChecked) {
                return;
            }
            switch (checkedId) {
                case R.id.rb_1:
                    intent.setClass(getApplicationContext(), HomeActivity.class);
                    gotoActivity(TAB_INFO, intent);
                    break;
                case R.id.rb_2:
                    intent.setClass(getApplicationContext(), QuickPreordainActicity.class);
                    gotoActivity(TAB_QUICK, intent);
                    break;
                case R.id.rb_3:
                    intent.setClass(getApplicationContext(), SeatsPreordainActivity.class);
                    gotoActivity(TAB_CUSTOMER, intent);
                    break;
                // case R.id.rb_3:
                // intent.setClass(getApplicationContext(),
                // MyPreordainActivity.class);
                // gotoActivity(TAB_INFO, intent);
                // break;
                case R.id.rb_4:
                    intent.setClass(getApplicationContext(), MoreActivity.class);
                    gotoActivity(TAB_ABOUT, intent);
                    break;

                default:
                    break;
            }
        }

    }

    public void gotoHome() {

        RadioButton rb = (RadioButton) findViewById(R.id.rb_1);
        rb.setOnCheckedChangeListener(new TabChangeListener());
        rb.setChecked(true);

    }

    @SuppressWarnings("deprecation")
    private void gotoActivity(String tab, Intent intent) {
        bodyLayout.removeAllViewsInLayout();
        bodyLayout.addView(
                getLocalActivityManager().startActivity(tab,
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView(),
                new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() != KeyEvent.ACTION_UP) {
            boolean canExit = true;
            if (getCurrentActivity() instanceof SeatsPreordainActivity) {
                canExit = ((SeatsPreordainActivity) getCurrentActivity()).goBack();
            }
            if (!canExit) {
                return true;
            }
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, getString(R.string.exit), Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

}

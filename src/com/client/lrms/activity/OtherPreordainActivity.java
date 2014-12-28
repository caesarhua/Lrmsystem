
package com.client.lrms.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.client.lrms.R;
import com.client.lrms.adapter.SeatGridViewAdapter;
import com.client.lrms.adapter.ViewPageAdapter;
import com.client.lrms.view.CustomDialog;
import com.client.lrms.view.CustomSpinner;
import com.otn.lrms.util.Constants;
import com.otn.lrms.util.entity.BaseResponse;
import com.otn.lrms.util.entity.FiltersInfo;
import com.otn.lrms.util.entity.FiltersReq;
import com.otn.lrms.util.entity.FreeTimes;
import com.otn.lrms.util.entity.FreeTimes.Times;
import com.otn.lrms.util.entity.QuickBook;
import com.otn.lrms.util.entity.SeatInfo;
import com.otn.lrms.util.entity.SeatInfo.Seats;
import com.otn.lrms.util.helper.CustomToast;
import com.otn.lrms.util.helper.Helper;
import com.otn.lrms.util.helper.LogUtil;
import com.otn.lrms.util.helper.SeatStatus;
import com.otn.lrms.util.net.DataEnginer;
import com.otn.lrms.util.net.Result;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class OtherPreordainActivity extends BaseNoTitleActivity {

    private static final String TAG = OtherPreordainActivity.class.getSimpleName();

    private static final int MAX_INDICATORS = 6;

    private CustomSpinner spDate;

    private CustomSpinner spTime;

    private CustomSpinner spVenues;

    private CustomSpinner spRoom;

    private CustomSpinner spPowers;

    private CustomSpinner spWindow;

    private ViewPager seatPager;

    private ViewPageAdapter seatPagerAdapter;

    private LinearLayout indicators;

    private FrameLayout seatLayout;

    private TextView defaultText;

    private LinearLayout progress;

    private ImageView goLeft;

    private ImageView goRight;

    private TextView preordainInfo;

    private CustomSpinner spStart;

    private CustomSpinner spEnd;

    private TextView tvSeatCount;

    private String seatId;

    private Times startData;

    private Times endData;

    private boolean hasNextPager = false;

    private int pageState;

    private FiltersReq filtersReq = new FiltersReq();

    private int offset = 0;

    private DataEnginer dataEnginer;

    private boolean isLoading = false;

    private int previtem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_other);
        initViews();
        doFreeFiltersReq();
    }

    private boolean isLoading() {
        return this.isLoading;
    }

    private void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    private void doFreeFiltersReq() {
        showLoading();
        dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_FILTERS);
        dataEnginer.request();

    }

    private void doFreeStartTimeReq() {
        showLoading();
        dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_STARTTIME, seatId, filtersReq.getOnDate());
        dataEnginer.request();

    }

    private void doFreeEndTimeReq() {
        showLoading();
        dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_ENDTIME, seatId, filtersReq.getOnDate(),
                startData.getId());
        dataEnginer.request();

    }

    private void doFreeSeatReq() {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("offset", "" + offset));
        nameValuePairs.add(new BasicNameValuePair("batch", Constants.DEFAULT_BATCH));
        nameValuePairs.add(new BasicNameValuePair("onDate", filtersReq.getOnDate()));
        nameValuePairs.add(new BasicNameValuePair("hour", filtersReq.getHour()));
        nameValuePairs.add(new BasicNameValuePair("room", filtersReq.getRoom()));
        nameValuePairs.add(new BasicNameValuePair("power", filtersReq.getPower()));
        nameValuePairs.add(new BasicNameValuePair("window", filtersReq.getWindow()));
        dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_FREE_SEATS);
        dataEnginer.setNameValuePairs(nameValuePairs);
        dataEnginer.request();

    }

    private void doFreeBookReq() {
        showLoading();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("seat", seatId));
        nameValuePairs.add(new BasicNameValuePair("startTime", startData.getId()));
        nameValuePairs.add(new BasicNameValuePair("endTime", endData.getId()));
        nameValuePairs.add(new BasicNameValuePair("date", filtersReq.getOnDate()));
        dataEnginer = new DataEnginer(this);
        dataEnginer.setUrl(Constants.METHOD_FREEBOOK);
        dataEnginer.setNameValuePairs(nameValuePairs);
        dataEnginer.request();

    }

    /**
     * 查询条件view
     * 
     * @param filtersInfo
     */
    private void setupFiltersView(final FiltersInfo filtersInfo) {
        if (filtersInfo == null) {
            return;
        }
        // 日期
        int size = filtersInfo.getDates().size();
        final String[] dates = filtersInfo.getDates().toArray(new String[size]);
        spDate.initSpinner(this, dates, new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                filtersReq.setOnDate(dates[arg2]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spDate.hideButton();
        spDate.setOnClickListener(null);

        // 时长
        int hours = filtersInfo.getHours();
        String[] times = new String[hours + 1];
        times[0] = "不限时长";
        for (int i = 1; i <= hours; i++) {
            times[i] = i + " 小时";
        }
        spTime.initSpinner(this, times, new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int index = arg2;
                if (index > 0) {
                    filtersReq.setHour(String.valueOf((index)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spTime.hideButton();
        spTime.setOnClickListener(null);

        // 场馆
        String[] venues = new String[filtersInfo.getBuildings().size() + 1];
        venues[0] = "不限场馆";
        for (int i = 0; i < filtersInfo.getBuildings().size(); i++) {
            venues[i + 1] = filtersInfo.getBuildings().get(i).getName();
        }
        spVenues.initSpinner(this, venues, new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spVenues.hideButton();
        spVenues.setOnClickListener(null);

        // 房间
        String[] rooms = new String[filtersInfo.getRooms().size() + 1];
        rooms[0] = "不限房间";
        for (int i = 0; i < filtersInfo.getRooms().size(); i++) {
            rooms[i + 1] = filtersInfo.getRooms().get(i).getName();
        }
        spRoom.initSpinner(this, rooms, new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int index = arg2 - 1;
                if (index >= 0) {
                    filtersReq.setRoom(String.valueOf(filtersInfo.getRooms().get(index).getId()));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spRoom.hideButton();
        spRoom.setOnClickListener(null);

        // 电源
        String[] powers = new String[] {
                "不限电源", "有电源", "无电源"
        };
        final String[] powersKey = new String[] {
                "", "1", "0"
        };
        spPowers.initSpinner(this, powers, new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                filtersReq.setPower(powersKey[arg2]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spPowers.hideButton();
        spPowers.setOnClickListener(null);
        String[] powers1 = new String[] {
                "不限靠窗", "靠窗", "不靠窗"
        };
        final String[] powersKey1 = new String[] {
                "", "1", "0"
        };
        spWindow.initSpinner(this, powers1, new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                filtersReq.setWindow(powersKey1[arg2]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spWindow.hideButton();
        spWindow.setOnClickListener(null);

    }

    private OnClickListener spClick = new OnClickListener() {

        @Override
        public void onClick(View v) {
            doFreeFiltersReq();
        }
    };

    private void initViews() {
        spDate = (CustomSpinner) findViewById(R.id.sp_date);
        spTime = (CustomSpinner) findViewById(R.id.sp_time);
        spVenues = (CustomSpinner) findViewById(R.id.sp_venues);
        spRoom = (CustomSpinner) findViewById(R.id.sp_room);
        spPowers = (CustomSpinner) findViewById(R.id.sp_powers);
        spWindow = (CustomSpinner) findViewById(R.id.sp_window);

        spDate.setOnClickListener(spClick);
        spTime.setOnClickListener(spClick);
        spVenues.setOnClickListener(spClick);
        spRoom.setOnClickListener(spClick);
        spPowers.setOnClickListener(spClick);
        spWindow.setOnClickListener(spClick);

        seatPager = (ViewPager) findViewById(R.id.seat_pager);
        seatLayout = (FrameLayout) findViewById(R.id.rl_all_seat);
        defaultText = (TextView) findViewById(R.id.default_text);
        progress = (LinearLayout) findViewById(R.id.progress);
        goLeft = (ImageView) findViewById(R.id.goLeft);
        goRight = (ImageView) findViewById(R.id.goRight);

        findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // showLoading();
                showSeatLayoutProgress();
                initViewPager();
                doFreeSeatReq();

                // new Handler().postDelayed(new Runnable() {
                //
                // @Override
                // public void run() {
                // progress.setVisibility(View.GONE);
                // // dismissLoading();
                // initViewPager();
                // }
                // }, 1000);
            }
        });

        tvSeatCount = (TextView) findViewById(R.id.tv_seats_count);
    }

    // 第一次查询显示，加载进度
    private void showSeatLayoutProgress() {
        progress.setVisibility(View.VISIBLE);
        defaultText.setVisibility(View.GONE);
        seatLayout.setVisibility(View.INVISIBLE);
    }

    // 查询分页是，加载进度
    private void showMorePagerProgress() {
        progress.setVisibility(View.VISIBLE);
        seatPager.setVisibility(View.INVISIBLE);
    }

    // 显示座位区域
    private void showSeatLayout() {
        progress.setVisibility(View.GONE);
        seatLayout.setVisibility(View.VISIBLE);
        seatPager.setVisibility(View.VISIBLE);
    }

    // 加载默认文字，请求失败后显示
    private void showDefaultText() {
        progress.setVisibility(View.GONE);
        defaultText.setVisibility(View.VISIBLE);
        seatLayout.setVisibility(View.INVISIBLE);
    }

    private int seatCount = 0;

    private void setupViewPager(SeatInfo seats) {

        setLoading(false);

        showSeatLayout();

        List<Seats> seatInfos = seats.getSeats();

        if (seatInfos == null || seatInfos.isEmpty()) {
            if (seatPagerAdapter.getCount() == 0) {
                // 第一次加载
                showDefaultText();
                tvSeatCount.setTag(seatInfos.size());
            } else {
                // 分页加载
                progress.setVisibility(View.GONE);
            }
            CustomToast.longtShow(getString(R.string.data_error));
            goRight.setBackgroundResource(R.drawable.go_right_normal);
            hasNextPager = false;
            return;
        }
        seatCount += seatInfos.size();

        hasNextPager = seatInfos.size() >= Integer.valueOf(Constants.DEFAULT_BATCH);

        List<List<Seats>> lists = Helper.getInstance().splitList(seatInfos,
                Integer.valueOf(Constants.DEFAULT_PER));

        List<View> listViews = new ArrayList<View>();
        for (List<Seats> list : lists) {
            listViews.add(SeatViewPage(list));
        }

        seatPagerAdapter.addAll(listViews);

        int currentItem = seatPager.getCurrentItem();

        if (currentItem == 0) {
            seatPager.setCurrentItem(0, true);
            TextView tvIndicator = (TextView) indicators.findViewById(R.id.tv_seat_indicator);
            tvIndicator.setText("1 / " + seatPagerAdapter.getCount() + "页");
            for (int i = 0; i < indicators.getChildCount() - 1; i++) {
                ImageView iv = (ImageView) indicators.getChildAt(i);
                if (currentItem == i) {
                    iv.setImageResource(R.drawable.index_curpage);
                } else {
                    iv.setImageResource(R.drawable.index_normal);
                }
            }
        } else {
            seatPager.setCurrentItem(currentItem + 1, true);
        }

        for (int i = 0; i < indicators.getChildCount() - 1; i++) {
            ImageView iv = (ImageView) indicators.getChildAt(i);
            if (i >= seatPagerAdapter.getCount()) {
                iv.setVisibility(View.GONE);
            } else {
                iv.setVisibility(View.VISIBLE);
            }
        }

        tvSeatCount.setText("可用座位列表（" + seatCount + "）：");

    }

    private void initIndicator() {
        indicators = (LinearLayout) findViewById(R.id.ll_indicator);
        indicators.setVisibility(View.VISIBLE);

        tvSeatCount.setText("可用座位列表：");
        seatCount = 0;

        TextView tvIndicator = (TextView) indicators.findViewById(R.id.tv_seat_indicator);
        tvIndicator.setText("");

    }

    private void initViewPager() {
        initIndicator();

        seatPagerAdapter = new ViewPageAdapter();

        seatPager.setAdapter(seatPagerAdapter);

        seatPager.setCurrentItem(0);

        seatPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(final int arg0) {

                // if (pageState == ViewPager.SCROLL_STATE_DRAGGING)
                {
                    if (arg0 == 0) {
                        goLeft.setBackgroundResource(R.drawable.go_left_normal);
                    } else if (arg0 == seatPagerAdapter.getCount() - 1 && !hasNextPager) {
                        goRight.setBackgroundResource(R.drawable.go_right_normal);
                    } else {
                        goLeft.setBackgroundResource(R.drawable.go_left_pressed);
                        goRight.setBackgroundResource(R.drawable.go_right_pressed);
                    }

                    boolean rightDirect = arg0 > previtem;
                    showIndicators(arg0, rightDirect);
                    previtem = arg0;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

                if (arg0 == seatPagerAdapter.getCount() - 1 && arg1 <= 0
                        && pageState == ViewPager.SCROLL_STATE_DRAGGING) {
                    // 最后一页向左滑动时，请求下一页
                    if (hasNextPager && !isLoading()) {
                        setLoading(true);
                        showMorePagerProgress();
                        offset++;
                        doFreeSeatReq();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                pageState = arg0;
            }
        });
    }

    /**
     * 虚拟引导点。
     * 
     * @param currentItem
     */
    private void showIndicators(int currentItem, boolean right) {
        int itemCount = seatPagerAdapter.getCount();
        LogUtil.i(TAG, "showIndicators currentItem = " + currentItem + "; itemCount = " + itemCount);

        TextView tvIndicator = (TextView) indicators.findViewById(R.id.tv_seat_indicator);
        tvIndicator.setText((currentItem + 1) + " / " + seatPagerAdapter.getCount() + "页");

        if (itemCount > MAX_INDICATORS) {
            if (right) {
                int maxRange = (itemCount / MAX_INDICATORS) * MAX_INDICATORS - 1;
                // MAX_INDICATOR一个循环，0--maxRange范围内
                if (currentItem > maxRange) {
                    // 超出循环的，判断当前与总共有几个，指引点往后移。
                    int remaining = itemCount - currentItem;
                    currentItem = MAX_INDICATORS - remaining;

                } else {
                    // 在循环内，直接取余
                    currentItem = currentItem % MAX_INDICATORS;
                }

            } else {
                int minRange = itemCount - (itemCount / MAX_INDICATORS) * MAX_INDICATORS;
                // MAX_INDICATOR一个循环，minRange--itemCount范围内
                if (currentItem >= minRange) {
                    // 超出循环的，无需修改；
                    // 循环的内，判断itemCount与总共有几个，指引点往前移。
                    int remaining = (itemCount - 1 - currentItem) % MAX_INDICATORS;
                    currentItem = MAX_INDICATORS - remaining - 1;
                }
            }
        }

        LogUtil.i(TAG, "showIndicators after currentItem = " + currentItem);

        for (int i = 0; i < indicators.getChildCount() - 1; i++) {
            ImageView iv = (ImageView) indicators.getChildAt(i);
            if (currentItem == i) {
                iv.setImageResource(R.drawable.index_curpage);
            } else {
                iv.setImageResource(R.drawable.index_normal);
            }
        }

    }

    private View SeatViewPage(List<Seats> seatInfos) {
        LinearLayout layout = new LinearLayout(this);
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(
                LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.seat_page, null);
        GridView seatPageGridView = (GridView) view.findViewById(R.id.gv_seat_page);
        SeatGridViewAdapter adapter = new SeatGridViewAdapter(this, seatInfos);
        seatPageGridView.setAdapter(adapter);

        seatPageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Seats seats = (Seats) arg0.getAdapter().getItem(arg2);
                if (SeatStatus.FREE.getName().equals(seats.getStatus())) {
                    selectStartTime(seats);
                    seatId = String.valueOf(seats.getId());
                } else {
                    CustomToast.shortShow("当前座位不是空闲座位，无法预约");
                }
            }
        });
        layout.addView(view);
        return layout;
    }

    private void selectStartTime(Seats seats) {
        startData = null;
        endData = null;
        View view = View.inflate(this, R.layout.layout_date_select, null);
        final CustomDialog dialg = new CustomDialog(this);
        dialg.setContentView(view);

        preordainInfo = (TextView) view.findViewById(R.id.address_info);
        preordainInfo.setText(seats.getLongName());

        spStart = (CustomSpinner) view.findViewById(R.id.sp_start);
        spStart.setIndex("选择开始时间");
        spStart.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                doFreeStartTimeReq();
            }
        });

        spEnd = (CustomSpinner) view.findViewById(R.id.sp_end);

        initSpinnerEndtime();

        view.findViewById(R.id.btn_select_ok).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (startData == null) {
                    CustomToast.shortShow("请选择开始时间");
                } else if (endData == null) {
                    CustomToast.shortShow("请选择结束时间");
                } else {
                    doFreeBookReq();
                    dialg.dismiss();
                }
            }
        });

        view.findViewById(R.id.btn_select_cancel).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialg.dismiss();
            }
        });

        dialg.show();
    }

    private void initSpinnerEndtime() {

        spEnd.setIndex("选择结束时间");

        spEnd.showButton();

        spEnd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (startData == null) {
                    CustomToast.longtShow("请先选择开始时间");
                } else {
                    doFreeEndTimeReq();
                }
            }
        });

    }

    private void setupSpinnerEndtime(final List<Times> ends) {

        String[] startArr = new String[ends.size()];
        for (int i = 0; i < ends.size(); i++) {
            startArr[i] = ends.get(i).getValue();
        }

        spEnd.initSpinner(this, startArr, new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                endData = ends.get(arg2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spEnd.hideButton();
        spEnd.showSpinner();
        spEnd.setOnClickListener(null);

    }

    private void setupSpinnerStarttime(final List<Times> starts) {

        String[] startArr = new String[starts.size()];
        for (int i = 0; i < starts.size(); i++) {
            startArr[i] = starts.get(i).getValue();
        }

        spStart.initSpinner(this, startArr, new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (startData != null && !starts.get(arg2).getId().equals(startData.getId())) {
                    // 重新选择开始时间后，结束时间要初始化
                    endData = null;
                    initSpinnerEndtime();
                }
                startData = starts.get(arg2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spStart.hideButton();
        spStart.showSpinner();
        spStart.setOnClickListener(null);

    }

    @Override
    public void onResponseError(String method, Result result) {
        super.onResponseError(method, result);
        if (Constants.METHOD_FREE_SEATS.equals(method)) {
            showDefaultText();
        }
    }

    @Override
    protected void refreshAfterDialog() {

    }

    @Override
    public void onResponseSucess(String method, Result result) {
        if (Constants.METHOD_FILTERS.equals(method)) {
            FiltersInfo filtersInfo = (FiltersInfo) result.getBody();
            setupFiltersView(filtersInfo);
        } else if (Constants.METHOD_STARTTIME.equals(method)) {

            FreeTimes respone = (FreeTimes) result.getBody();
            setupSpinnerStarttime(respone.getStartTimes());

        } else if (Constants.METHOD_ENDTIME.equals(method)) {

            FreeTimes respone = (FreeTimes) result.getBody();
            setupSpinnerEndtime(respone.getEndTimes());

        } else if (Constants.METHOD_FREE_SEATS.equals(method)) {

            SeatInfo seats = (SeatInfo) result.getBody();
            setupViewPager(seats);

        } else if (Constants.METHOD_FREEBOOK.equals(method)) {

            BaseResponse<QuickBook> resp = (BaseResponse<QuickBook>) result.getBody();

            if (resp.getData() != null) {
                sumbitSuccessShow(resp.getData().getReservation());
            }
        }

    }

}

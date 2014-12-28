
package com.client.lrms.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.client.lrms.R;
import com.otn.lrms.util.net.Result;

public class HomeFrag extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_home, null);
    }

    @Override
    public void onResponseSucess(String method, Result result) {

    }

}

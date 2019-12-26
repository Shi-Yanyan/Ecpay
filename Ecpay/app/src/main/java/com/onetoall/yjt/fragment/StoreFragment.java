package com.onetoall.yjt.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onetoall.yjt.MyApplication;
import com.onetoall.yjt.R;
import com.onetoall.yjt.controller.store.ChoseStoreActivity;
import com.onetoall.yjt.controller.store.CustomerManagerActivity;
import com.onetoall.yjt.controller.store.PayChannelActivity;
import com.onetoall.yjt.controller.store.StoreDetailInfoActivity;
import com.onetoall.yjt.core.BaseFragment;
import com.onetoall.yjt.domain.Store;
import com.onetoall.yjt.model.Callback;
import com.onetoall.yjt.model.OnBaseModelListener;
import com.onetoall.yjt.model.impl.StoreModel;
import com.onetoall.yjt.utils.Constants;
import com.onetoall.yjt.utils.UMEvent;
import com.onetoall.yjt.utils.UMEventUtil;
import com.onetoall.yjt.widget.QToolbar;
import com.onetoall.yjt.widget.row.BaseRowDescriptor;
import com.onetoall.yjt.widget.row.ContainerDescriptor;
import com.onetoall.yjt.widget.row.ContainerView;
import com.onetoall.yjt.widget.row.GroupDescriptor;
import com.onetoall.yjt.widget.row.OnRowClickListener;
import com.onetoall.yjt.widget.row.expand.IOSRowDescriptor;
import com.onetoall.yjt.widget.row.tool.RowActionEnum;
import com.onetoall.yjt.widget.tab.ITabFragment;

import java.util.ArrayList;

/**
 * 店铺
 * Created by qinwei on 2016/10/18 13:40
 * email:qinwei_it@163.com
 */

public class StoreFragment extends BaseFragment implements ITabFragment, OnRowClickListener, SwipeRefreshLayout.OnRefreshListener {
    private QToolbar toolbar;


    private StoreModel mStoreModel;


    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_store;
    }


    @Override
    protected void initView(View v) {
        toolbar = (QToolbar) v.findViewById(R.id.toolbar);


    }

    @Override
    protected void initData() {
        super.initData();
        toolbar.setTitle(R.string.app_home_shop_label);
        mStoreModel = new StoreModel((OnBaseModelListener) getActivity());





//        loadDataFromServer();
    }

    private void loadDataFromServer() {
        String storeId = MyApplication.getInstance().getStore().getStore_id() + "";
        String tel = MyApplication.getInstance().getTel();
        String storeNumber = MyApplication.getInstance().getStore().getStore_number();
        mStoreModel.loadStoreInfo(storeId, tel, storeNumber, new Callback<Store>() {


            @Override
            public void onSuccess(Store data) {

            }

            @Override
            public void onFailure(int code, String msg) {

            }
        });
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public void onMenuItemClick(int id) {

    }

    @Override
    public void onRowClick(View rowView, RowActionEnum action) {

    }



    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.PERSON_CHANGE_STORE) {
//            loadDataFromServer();
        }
    }

    @Override
    public void onRefresh() {


//        loadDataFromServer();
    }
}

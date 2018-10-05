package cn.hg.community;

import android.content.Context;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;

import java.lang.reflect.ParameterizedType;

import cn.hg.common.BaseResp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class MyCallBack<T> implements Callback<T>{

    private SmartRefreshLayout mSmartRefreshLayout;
    private Context mContext;

    public MyCallBack(Context context) {
        mContext = context;
    }

    public MyCallBack(Context context,SmartRefreshLayout smartRefreshLayout) {
        this(context);
        mSmartRefreshLayout = smartRefreshLayout;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        finishRefresh();
        if (response.isSuccessful()){
            try {
                if (response.body() == null){
                    onFailure(call,new Exception("body返回为空"));
                }else {
                    onResponse(response.body());
                }
            }catch (Exception e){
                onFailure(call,e);
            }
        }else 
            onFailure(call,new Exception(response.message()));
    }

    private void finishRefresh() {
        if (mSmartRefreshLayout != null){
            if (mSmartRefreshLayout.getState() == RefreshState.Refreshing)
                mSmartRefreshLayout.finishRefresh();
            if (mSmartRefreshLayout.getState() == RefreshState.Loading)
                mSmartRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        finishRefresh();
    }

    abstract void onResponse(T t);
}

package cn.hg.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import cn.hg.common.BaseResp;
import cn.hg.common.RetrofitUtil;

import static android.app.Activity.RESULT_OK;


public class CommunityFragment extends Fragment{


    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private View mView;
    private Context mContext;
    private int page = 1;
    private PostAdapter mPostAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_community, container, false);
        initView();
        return mView;
    }

    private void initView() {
        mRecyclerView = mView.findViewById(R.id.recycler_view);
        mSmartRefreshLayout = mView.findViewById(R.id.smart_refresh_layout);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
       // mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        mSmartRefreshLayout.autoRefresh();

        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            getData();
        });

        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            getData();
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK)
            mSmartRefreshLayout.autoRefresh();
    }

    private void getData() {
        Api.instance().getPosts(page).enqueue(new MyCallBack<BaseResp<List<Post>>>(mContext, mSmartRefreshLayout) {
            @Override
            void onResponse(BaseResp<List<Post>> listBaseResp) {
                if (listBaseResp.getCode() == 10000) {
                    if (page == 1) {
                        mPostAdapter = new PostAdapter(listBaseResp.getData(), mContext);
                        mRecyclerView.setAdapter(mPostAdapter);
                    } else
                        mPostAdapter.add(listBaseResp.getData());
                } else {

                }
            }
        });
    }
}

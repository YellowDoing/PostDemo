package cn.hg.postdemo;

import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import cn.hg.common.BaseActivity;
import cn.hg.community.PublishActivity;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        startActivity(new Intent(mContext,PublishActivity.class));
    }
}

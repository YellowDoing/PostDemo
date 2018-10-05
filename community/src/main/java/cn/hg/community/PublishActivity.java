package cn.hg.community;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;


import cn.hg.common.BaseActivity;


/**
 * 发表帖子界面
 */
public class PublishActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private TextView tvPublish;
    private EditText etContent;
    private ImageView ivBack;
    private ImageSelectAdapter mImageSelectAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        Utils.init(this);


        mRecyclerView = findViewById(R.id.rv_select_images);
        etContent = findViewById(R.id.et_content);
        tvPublish = findViewById(R.id.tv_publish);
        ivBack = findViewById(R.id.iv_back);

        ivBack.setOnClickListener(this);
        tvPublish.setOnClickListener(this);

        mImageSelectAdapter = new ImageSelectAdapter(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mImageSelectAdapter);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_publish) {
            publish();
        }
    }

    private void publish() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // Log.d("ssssssssssssss", data.getData().toString());

    }
}

package cn.hg.community;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.NineGridViewAdapter;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.hg.common.BaseActivity;
import me.iwf.photopicker.PhotoPreview;


/**
 * 评论详情
 */
public class PostDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvAvatar; //头像
    private NineGridView mIvImages; //图片列表
    private RecyclerView mRvReplies; //回复列表
    private TextView mTvContent, mTvNickName, mTvReply, mTvLikeNum;
    private TextView mTvtime;
    private EditText mEtReply; //回复输入框
    private int mPage = 1; // 评论列表分页
    private Post mPost; //帖子实体类
    private CommentAdapter mAdapter; // 评论适配器
    private String theOhterNickName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        initView();
        setData();
    }

    private void initView() {
        mIvAvatar = findViewById(R.id.iv_avatar);
        mIvImages = findViewById(R.id.rv_images);
        mTvLikeNum = findViewById(R.id.tv_like_num);
        mRvReplies = findViewById(R.id.rv_replys);
        mTvNickName = findViewById(R.id.tv_nick_name);
        mTvtime = findViewById(R.id.tv_time);
        mTvReply = findViewById(R.id.tv_reply);
        mEtReply = findViewById(R.id.et_reply);
        mTvContent = findViewById(R.id.tv_content);
        mAdapter = new CommentAdapter(new ArrayList<>());
        mRvReplies.setLayoutManager(new LinearLayoutManager(this));
        mRvReplies.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRvReplies.setAdapter(mAdapter);
        mTvReply.setOnClickListener(this);
    }

    private void setData() {
        mPost = (Post) getIntent().getSerializableExtra("data");
        //加载头像
        Glide.with(mContext).load(mPost.getCreator().getAvatar()).into(mIvAvatar);
        mTvNickName.setText(mPost.getCreator().getNickname());
        mTvContent.setText(mPost.getContent());
        mTvtime.setText(mPost.getCreate_time());

        List<ImageInfo> infoList = new ArrayList<>();
        ArrayList<String> attachment = mPost.getMedia_attachment();
        int size = attachment.size();

        for (int j = 0; j < size; j++) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setBigImageUrl(mPost.getMedia_attachment().get(j));
            imageInfo.setThumbnailUrl(mPost.getMedia_attachment().get(j));
            infoList.add(imageInfo);
        }

        mIvImages.setAdapter(new NineGridViewAdapter(mContext, infoList) {
            @Override
            protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List<ImageInfo> imageInfo) {
                super.onImageItemClick(context, nineGridView, index, imageInfo);
                PhotoPreview.builder().setShowDeleteButton(false).setPhotos(attachment).setCurrentItem(index).start((Activity) mContext);
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v == mTvReply) { //回复

        }
    }
}

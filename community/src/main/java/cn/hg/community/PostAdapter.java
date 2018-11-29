package cn.hg.community;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.NineGridViewAdapter;
import com.zhuang.likeviewlibrary.LikeView;

import java.util.ArrayList;
import java.util.List;

import cn.hg.common.BaseResp;
import cn.hg.common.RetrofitUtil;
import cn.hg.common.TimeUtil;
import cn.hg.common.User;
import me.iwf.photopicker.PhotoPreview;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> mPostList;
    private Context mContext;
    private LayoutInflater mInflater;

    public PostAdapter(List<Post> postList, Context context) {
        mPostList = postList;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        NineGridView.setImageLoader(new NineGridView.ImageLoader() {
            @Override
            public void onDisplayImage(Context context, ImageView imageView, String url) {
                Glide.with(mContext).load(url).apply(new RequestOptions().centerCrop()).into(imageView);
            }

            @Override
            public Bitmap getCacheImage(String url) {
                return null;
            }
        });
    }

    public void add(List<Post> postList) {
        mPostList.addAll(postList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PostViewHolder(mInflater.inflate(R.layout.list_item_post, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int i) {
        Post post = mPostList.get(i);


        User user = post.getCreator();
        Glide.with(mContext).load(user.getAvatar()).apply(new RequestOptions().circleCrop()).into(holder.ivAvatar);
        holder.tvNickName.setText(user.getNickname());

        if (post.getContent() == null)
            holder.tvContent.setVisibility(View.GONE);
        else {
            holder.tvContent.setVisibility(View.VISIBLE);
            holder.tvContent.setText(post.getContent());
        }

        holder.likeView.setLikeCount(post.getGreat_num());
        holder.tvReplyNum.setText(String.valueOf(post.getComment_num()));

        holder.tvTime.setText(TimeUtil.getStandardDate(post.getUpdate_time()));

        List<ImageInfo> infoList = new ArrayList<>();
        ArrayList<String> attachment = post.getMedia_attachment();
        int size = attachment.size();

        for (int j = 0; j < size; j++) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setBigImageUrl(post.getMedia_attachment().get(j));
            imageInfo.setThumbnailUrl(post.getMedia_attachment().get(j));
            infoList.add(imageInfo);
        }


        holder.nineGridView.setAdapter(new NineGridViewAdapter(mContext, infoList) {
            @Override
            protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List<ImageInfo> imageInfo) {
                super.onImageItemClick(context, nineGridView, index, imageInfo);
                PhotoPreview.builder().setShowDeleteButton(false).setPhotos(attachment).setCurrentItem(index).start((Activity) mContext);
            }
        });

        holder.likeView.setOnLikeListeners(isCancel -> {
            if (!isCancel) great(post.getId());
        });

        holder.itemView.setOnClickListener(v -> {

        });


/*
        //接口返回的图片地址
        final Community community = mDataList.get(position);

        final ArrayList<String> imagePaths = community.getImagePaths();

        if (holder.mContainer.getChildCount() > 0)
            holder.mContainer.removeAllViews();

        if (imagePaths != null && imagePaths.size() > 0) {
            RecyclerView recyclerView = new RecyclerView(mContext);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
            recyclerView.setAdapter(new ImageAdapter(mContext, imagePaths));
            // TODO: 2017/11/21 空白区域点击部分需要做处理
            holder.mContainer.addView(recyclerView);
        }

        holder.mTvReplyNum.setText(String.valueOf(community.getReplyNum()));
        holder.mTvCreateTime.setText(isNull(community.getCreateTime()));
        holder.mTvNickName.setText(isNull(community.getNickName()));
        Glide.with(mContext).load(isNull(community.getAvatar())).centerCrop().into(holder.mIvAvatar);
        holder.mTvLikeNum.setText(String.valueOf(community.getLikeNum()));
        if (isNull(community.getContent()).trim().equals("")) holder.mTvContent.setVisibility(View.GONE);
        else {
            holder.mTvContent.setVisibility(View.VISIBLE);
            holder.mTvContent.setText(community.getContent());
        }

        //接口返回该贴是否已喜欢
        if (community.isLike()) {
            holder.mIvLike.setImageResource(R.drawable.ic_zan_hover);
            holder.mTvLikeNum.setTextColor(mContext.getResources().getColor(R.color.like_num));
        } else {
            holder.mIvLike.setImageResource(R.drawable.ic_zan);
            holder.mTvLikeNum.setTextColor(mContext.getResources().getColor(R.color.gray));
        }

        //接口回调点赞点击事件
        holder.mIvLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!community.isLike()) mInterface.like(new CommunityInterface.Subsriber() {
                    @Override
                    public void onComplete() {
                        holder.mTvLikeNum.setText(String.valueOf(Integer.parseInt(holder.mTvLikeNum.getText().toString()) + 1));
                        holder.mTvLikeNum.setTextColor(mContext.getResources().getColor(R.color.like_num));
                        holder.mIvLike.setImageResource(R.drawable.ic_zan_hover);
                        community.setLike(true);
                    }
                }, community.getId());
                else mInterface.unLike(new CommunityInterface.Subsriber() {
                    @Override
                    public void onComplete() {
                        holder.mTvLikeNum.setText(String.valueOf(Integer.parseInt(holder.mTvLikeNum.getText().toString()) - 1));
                        holder.mTvLikeNum.setTextColor(mContext.getResources().getColor(R.color.gray));
                        holder.mIvLike.setImageResource(R.drawable.ic_zan);
                        community.setLike(false);
                    }
                }, community.getId());
            }
        });

        //帖子点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, CommunityDetialActivity.class)
                        .putExtra("community", community));
            }
        });

        //回复
        holder.mIvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/11/15 回复功能
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        ImageView ivReply;
        LikeView likeView;
        TextView tvNickName, tvContent, tvTime, tvReplyNum;
        LinearLayout container;
        NineGridView nineGridView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            ivReply = itemView.findViewById(R.id.iv_reply);
            tvReplyNum = itemView.findViewById(R.id.tv_reply_num);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvNickName = itemView.findViewById(R.id.tv_name);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvTime = itemView.findViewById(R.id.tv_update_time);
            container = itemView.findViewById(R.id.container);
            nineGridView = itemView.findViewById(R.id.nine_grid_view);
            likeView = itemView.findViewById(R.id.likeView);
        }
    }


    //点赞
    private void great(int id) {
        Api.instance().great(id,User.getCurrentUser().getId())
                .enqueue(new MyCallBack<BaseResp>(mContext) {
                    @Override
                    void onResponse(BaseResp baseResp) {
                        if (baseResp.getCode() == 10000)
                            ToastUtils.showShort("点赞成功");
                    }
                });
    }
}

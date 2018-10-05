package cn.hg.community;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.IntentUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import java.util.ArrayList;

import cn.hg.common.Constant;
import me.iwf.photopicker.PhotoPicker;


/**
 * 图片选择器
 */
public class ImageSelectAdapter extends RecyclerView.Adapter<ImageSelectAdapter.ImageSelectViewHolder> {


    private PublishActivity mContext;
    private ArrayList<String> mPaths;
    private LayoutInflater mInflater;
    //private int dp_8,dp_72;


    public ImageSelectAdapter(PublishActivity context) {
        mContext = context;
        mPaths = new ArrayList<>();
      /*  dp_8 = dip2px(8);
        dp_72 = dip2px(72);*/
        mInflater = LayoutInflater.from(mContext);
    }

    public void add(ArrayList<String> paths) {
        mPaths.addAll(paths);
        notifyDataSetChanged();
    }

    public void reset(ArrayList<String> paths) {
        mPaths = paths;
        notifyDataSetChanged();
    }

    public ArrayList<String> getPaths() {
        return mPaths;
    }

    @Override
    public ImageSelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageSelectViewHolder(mInflater.inflate(R.layout.list_item_image_select, parent, false));
    }

    @Override
    public void onBindViewHolder(ImageSelectViewHolder holder, final int position) {

        if (position == mPaths.size()) {
            holder.imageView.setImageResource(R.drawable.ic_add_picture);
            holder.imageView.setOnClickListener(v -> {
                //showTypeDialog()
              /*  Intent intent = new Intent(mContext, ImageGridActivity.class);
                mContext.startActivityForResult(intent, 1);*/

                PhotoPicker.builder()
                        .setPhotoCount(9)
                        .setShowCamera(true)
                        .setShowGif(false)
                        .setPreviewEnabled(true)
                        .start(mContext, PhotoPicker.REQUEST_CODE);
            });
            holder.ivClose.setVisibility(View.GONE);
        } else {
            holder.ivClose.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(mPaths.get(position)).apply(new RequestOptions().centerCrop()).into(holder.imageView);

            holder.imageView.setOnClickListener(v -> {
                // TODO: 2018/10/5 图片预览 
            });

            holder.ivClose.setOnClickListener(v -> {
                mPaths.remove(position);
                notifyDataSetChanged();
            });
        }
    }

    @Override
    public int getItemCount() {
        return mPaths.size() < 9 ? mPaths.size() + 1 : 9;
    }

    class ImageSelectViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, ivClose;

        public ImageSelectViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            ivClose = itemView.findViewById(R.id.iv_close);
        }
    }

    private void showTypeDialog() {
        new AlertDialog.Builder(mContext)
                .setItems(new String[]{"相册", "拍照"}, (dialog, which) -> {
                    switch (which) {
                        case 0:
                              /*   Intent intent = IntentUtils.getCaptureIntent
                        (Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath()));
                mContext.startActivityForResult(intent,Constant.REQUEST_CODE_CAMERA);*/
                            break;
                        case 1:
                            break;
                    }
                }).show();
    }

}
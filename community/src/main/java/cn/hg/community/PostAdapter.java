package cn.hg.community;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> mPostList;
    private Context mContext;
    private LayoutInflater mInflater;

    public PostAdapter(List<Post> postList, Context context) {
        mPostList = postList;
        mContext = context;
        mInflater  = LayoutInflater.from(mContext);
    }

    public void add(List<Post> postList) {
        mPostList.addAll(postList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PostViewHolder(mInflater.inflate(R.layout.list_item_post,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {
        Post post = mPostList.get(i);


    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {


        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

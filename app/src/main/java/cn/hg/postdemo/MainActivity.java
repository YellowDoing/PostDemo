package cn.hg.postdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import butterknife.ButterKnife;
import cn.hg.common.BaseActivity;
import cn.hg.community.CommunityFragment;
import cn.hg.community.PublishActivity;

public class MainActivity extends BaseActivity {

    private CommunityFragment mCommunityFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCommunityFragment = (CommunityFragment)getSupportFragmentManager()
                .findFragmentById(R.id.community_fragment);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                mCommunityFragment.startActivityForResult(new Intent(mContext,PublishActivity.class),0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

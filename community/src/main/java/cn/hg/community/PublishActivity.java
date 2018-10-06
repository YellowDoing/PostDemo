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
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.CosXmlSimpleService;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.transfer.COSXMLUploadTask;
import com.tencent.cos.xml.transfer.TransferConfig;
import com.tencent.cos.xml.transfer.TransferManager;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.auth.ShortTimeCredentialProvider;

import java.util.ArrayList;
import java.util.List;

import cn.hg.common.BaseActivity;
import cn.hg.common.BaseResp;
import cn.hg.common.NavigationBar;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.iwf.photopicker.PhotoPicker;


/**
 * 发表帖子界面
 */
public class PublishActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private EditText etContent;
    private ImageSelectAdapter mImageSelectAdapter;
    private int type;
    private TransferManager mTransferManager;
    private List<String> uploadPaths;
    private List<String> media_attachment = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        mRecyclerView = findViewById(R.id.rv_select_images);
        etContent = findViewById(R.id.et_content);

        mImageSelectAdapter = new ImageSelectAdapter(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mImageSelectAdapter);

        type = getIntent().getIntExtra("type", 2);

        initcos();

        NavigationBar navigationBar = findViewById(R.id.navigation_bar);

        navigationBar.setRightTitleClick(v -> {
            uploadPaths = mImageSelectAdapter.getPaths();
            if (uploadPaths.size() > 0)
                upload();
            else {
                if (etContent.getText().toString().trim().isEmpty()) {
                    Toast.makeText(mContext, "请输入您想要发表的内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                publish();
            }

        });
    }

    /**
     * 发表
     */
    private void publish() {
        Post post = new Post();
        post.setContent(etContent.getText().toString().isEmpty() ? null : etContent.getText().toString());
        post.setType(type);
        post.setMedia_attachment(media_attachment);
        post.setUser_id(SPUtils.getInstance().getInt("user_id",1));

        RetrofitUtil.create().publish(SPUtils.getInstance().getString("token","7b7fefe3e6efe762d505294590cc3e7a"),post).enqueue(new MyCallBack<BaseResp>(mContext) {
            @Override
            void onResponse(BaseResp baseResp) {
                    ToastUtils.showShort(baseResp.getMessage());
                    if (baseResp.getCode() == 10000){
                        setResult(RESULT_OK);
                        finish();
                    }
            }
        });
    }

    private void initcos() {
        String appid = "1253660948";
        String region = "ap-guangzhou";

        CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
                .setAppidAndRegion(appid, region)
                .setDebuggable(true)
                .builder();

        String secretId = "AKIDGSVRFsM3kHcp0aLpKAsTvkR0OKCwms3T";
        String secretKey = "bTIVfkgSp7FHvVuCMK7h6eyANVUYTDl7";

        QCloudCredentialProvider credentialProvider = new ShortTimeCredentialProvider(secretId,
                secretKey, 300);

        CosXmlSimpleService cosXml = new CosXmlSimpleService(mContext, serviceConfig, credentialProvider);

        mTransferManager = new TransferManager(cosXml, new TransferConfig.Builder().build());
    }


    @Override
    public void onClick(View v) {

    }

    private void upload() {

        String srcPath = uploadPaths.get(0);
        String cosPath = "hg" + System.currentTimeMillis() + srcPath.substring(srcPath.indexOf('.'));

        switch (type) {
            case 2: //图片
                cosPath = "community/picture/" + cosPath;
                break;
            case 3: //音频
                cosPath = "community/video/" + cosPath;
                break;
            case 4: //视频
                cosPath = "community/audio/" + cosPath;
                break;
        }

        String bucket = "huanggan-1253660948";
        COSXMLUploadTask cosxmlUploadTask = mTransferManager.upload(bucket, cosPath, srcPath, null);

        cosxmlUploadTask.setCosXmlResultListener(new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest request, CosXmlResult result) {
                uploadPaths.remove(0);
                if (uploadPaths.size() > 0)
                    upload();
                else
                    publish();
            }

            @Override
            public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
                Log.d("TESTddddddddddd", "Failed: " + (exception == null ? serviceException.getMessage() : exception.toString()));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhotoPicker.REQUEST_CODE:
                    mImageSelectAdapter.add(data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS));
                    break;
            }
        }
    }
}

package cn.hg.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.CosXmlSimpleService;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
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
import cn.hg.common.RetrofitUtil;
import cn.hg.common.User;
import me.iwf.photopicker.PhotoPicker;


/**
 * 发表帖子界面
 * 目前只有图文类型
 */
public class PublishActivity extends BaseActivity implements View.OnClickListener {

    private EditText etContent;
    private ImageSelectAdapter mImageSelectAdapter;
    //private int type; //1 文字 2 图片 3 音频 4 视频
    private TransferManager mTransferManager; //腾讯云工具
    private List<String> uploadPaths;
    private List<String> media_attachment = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        RecyclerView recyclerView = findViewById(R.id.rv_select_images);
        etContent = findViewById(R.id.et_content);
        NavigationBar navigationBar = findViewById(R.id.navigation_bar);

        mImageSelectAdapter = new ImageSelectAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mImageSelectAdapter);


        //type = getIntent().getIntExtra("type", 2);

        initcos();

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
        //post.setType(type);
        post.setMedia_attachment(media_attachment);
        post.setUser_id(User.getCurrentUser().getId());

        Api.instance().publish(post).enqueue(new MyCallBack<BaseResp>(mContext) {
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

    /**
     * 初始化腾讯云cos
     */
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

    /**
     * 上传媒体资源
     */
    private void upload() {

        String srcPath = uploadPaths.get(0);
        String cosPath = "community/picture/hg" + System.currentTimeMillis() + srcPath.substring(srcPath.indexOf('.'));

        /*switch (type) {
            case 2: //图片
                cosPath = "community/picture/" + cosPath;
                break;
            case 3: //音频
                cosPath = "community/video/" + cosPath;
                break;
            case 4: //视频
                cosPath = "community/audio/" + cosPath;
                break;
        }*/

        String bucket = "huanggan-1253660948";
        COSXMLUploadTask cosxmlUploadTask = mTransferManager.upload(bucket, cosPath, srcPath, null);

        cosxmlUploadTask.setCosXmlResultListener(new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest request, CosXmlResult result) {
                uploadPaths.remove(0);
                media_attachment.add("https://" + result.accessUrl);
                if (uploadPaths.size() > 0)
                    upload();
                else
                    publish();
            }

            @Override
            public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
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

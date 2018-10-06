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

import cn.hg.common.BaseActivity;
import me.iwf.photopicker.PhotoPicker;


/**
 * 发表帖子界面
 */
public class PublishActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private EditText etContent;
    private ImageSelectAdapter mImageSelectAdapter;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        mRecyclerView = findViewById(R.id.rv_select_images);
        etContent = findViewById(R.id.et_content);


        mImageSelectAdapter = new ImageSelectAdapter(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mImageSelectAdapter);

        type = getIntent().getIntExtra("type",1);

        initcos();
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

        CosXmlSimpleService cosXml =new CosXmlSimpleService(mContext,serviceConfig,credentialProvider);

        TransferManager transferManager = new TransferManager(cosXml, new TransferConfig.Builder().build());

        String bucket = "huanggan-1253660948";//储存桶名称
        //String cosPath = [对象键]
        // (https://cloud.tencent.com/document/product/436/13324)，即存储到 COS 上的绝对路径; //格式如 cosPath = "test.txt";
        String srcPath = "本地文件的绝对路径"; // 如 srcPath=Environment.getExternalStorageDirectory().getPath() + "/test.txt";
        String cosPath = "";
        switch (type){
            case 2: //图片
               // cosPath = "community/picture/" +
                break;
            case 3: //音频
                break;
            case 4: //视频
                break;
        }

//上传文件
        COSXMLUploadTask cosxmlUploadTask = transferManager.upload(bucket, cosPath, srcPath, null);
//设置上传进度回调
        cosxmlUploadTask.setCosXmlProgressListener((complete, target) -> {
            float progress = 1.0f * complete / target * 100;
            Log.d("TEST", String.format("progress = %d%%", (int) progress));
        });
//设置返回结果回调
        cosxmlUploadTask.setCosXmlResultListener(new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest request, CosXmlResult result) {
                Log.d("TEST", "Success: " + result.printResult());
            }

            @Override
            public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
                Log.d("TEST", "Failed: " + (exception == null ? serviceException.getMessage() : exception.toString()));
            }
        });
    }


    @Override
    public void onClick(View v) {

    }

    private void publish() {

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

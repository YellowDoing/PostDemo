package cn.hg.common;

import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class MyResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final TypeAdapter<T> adapter;
    private final Gson gson;

    MyResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
        gson = new Gson();
    }


    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        BaseResp<Object> stringBaseResp = gson.fromJson(response, new TypeToken<BaseResp<Object>>() {
        }.getType());
        if (stringBaseResp.getCode() != 10000) {
            ToastUtils.showShort(stringBaseResp.getMessage());
        }
        return adapter.fromJson(stringBaseResp.getData().toString());
    }
}

package cn.hg.postdemo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import cn.hg.common.BaseResp;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    String a = "{\n"+
            "    \"code\": 10000,\n"+
            "    \"message\": \"操作成功\",\n"+
            "    \"data\": [\n"+
            "    ]\n"+
            "}";
    @Test
    public void addition_isCorrect() {
        BaseResp<Object> resp = new Gson().fromJson(a,new TypeToken<BaseResp<Object>>(){}.getType());
        System.out.print(resp.getData().toString());
    }
}
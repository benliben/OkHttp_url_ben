package com.example.benben.okhttp_url_ben;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benben.okhttputils.OkHttpUtils;
import com.example.benben.okhttputils.callback.BitmapCallback;
import com.example.benben.okhttputils.callback.FileCallBack;
import com.example.benben.okhttputils.callback.StringCallback;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.id_progress)
    ProgressBar mProgress;
    @InjectView(R.id.id_textview)
    TextView mTextview;
    @InjectView(R.id.id_imageview)
    ImageView mImageview;
    private String mBaseUrl = "http://pic10.nipic.com/20101004/3320946_021726451306_2.jpg";

    private static final String TAG = "lyx";


    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request) {
            super.onBefore(request);
            setTitle("loading...");
        }

        @Override
        public void onAfter() {
            super.onAfter();
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(okhttp3.Call call, Exception e) {
            e.printStackTrace();
            mTextview.setText("失败+onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response) {
            Log.e("TAG","onResponse：complete");
            mTextview.setText("响应+onResponse:" + response);
        }

        @Override
        public void inProgress(float progress) {
            Log.e(TAG, "inProgress:" + progress);
            mProgress.setProgress((int) (100 * progress));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mProgress.setMax(100);
    }


    /**获取Html*/
    public void getHtml(View view) {
        String url = "https://www.baidu.com/baidu?wd=html%CE%C4%BC%FE&tn=monline_4_dg";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new MyStringCallback());
    }


    public void postString(View view) {
        String url = mBaseUrl + "user!postString";
        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf_8"))
                .content(new Gson().toJson(new User("lyx","123")))
                .build()
                .execute(new MyStringCallback());
    }

    /**文件*/
    public void postFile(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
        if (!file.exists()) {
            Toast.makeText(MainActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = mBaseUrl + "user!postFile";
        OkHttpUtils
                .postFile()
                .url(url)
                .file(file)
                .build()
                .execute(new MyStringCallback());

    }


    /**用户*/
    public void getUser(View view) {
        String url = mBaseUrl + "user!getUser";
        OkHttpUtils
                .post()//
                .url(url)//
                .addParams("username", "lyx")//
                .addParams("password", "123")//
                .build()//
                .execute(new UserCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e) {
                        mTextview.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(User response) {
                        mTextview.setText("onResponse:" + response.username);
                    }
                });
    }



    /**用户*/
    public void getUsers(View view) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "lyx");
        String url = mBaseUrl + "user!getUsers";
        OkHttpUtils
                .post()
                .url(url)
//                .params(params)
                .build()
                .execute(new ListUserCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e) {
                        mTextview.setText("错误+onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(List<User> response) {
                        mTextview.setText("响应+onResponse:" + response);
                    }
                });
    }



    public void getHttpsHtml(View view) {
        String url = "https://kyfw.12306.cn/otn/";

        OkHttpUtils
                .get()//
                .url(url)//
                .build()//
                .execute(new MyStringCallback());

    }

    public void getImage(View view) {
        mTextview.setText("");
        String url = "http://images.csdn.net/20150817/1.jpg";
        OkHttpUtils
                .get()//
                .url(url)//
                .tag(this)//
                .build()//
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e) {
                        mTextview.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Bitmap bitmap) {
                        Log.e("TAG","onResponse：complete");
                        mImageview.setImageBitmap(bitmap);
                    }
                });
    }


    /**上传文件*/
    public void uploadFile(View view) {

        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
        if (!file.exists()) {
            Toast.makeText(MainActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("username", "犇犇");
        params.put("password", "123");

        Map<String, String> headers = new HashMap<>();
        headers.put("APP-Key", "APP-Secret222");
        headers.put("APP-Secret", "APP-Secret111");

        String url = mBaseUrl + "user!uploadFile";

        OkHttpUtils.post()//
                .addFile("mFile", "messenger_01.png", file)//
                .url(url)//
                .params(params)//
                .headers(headers)//
                .build()//
                .execute(new MyStringCallback());
    }


    /**多文件的上传*/
    public void multiFileUpload(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
        File file2 = new File(Environment.getExternalStorageDirectory(), "test1#.txt");
        if (!file.exists()) {
            Toast.makeText(MainActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("username", "犇犇");
        params.put("password", "123");

        String url = mBaseUrl + "user!uploadFile";
        OkHttpUtils.post()//
                .addFile("mFile", "messenger_01.png", file)//
                .addFile("mFile", "test1.txt", file2)//
                .url(url)
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }


    /**下载文件*/
    public void downloadFile(View view)
    {
        String url = "https://github.com/hongyangAndroid/okhttp-utils/blob/master/gson-2.2.1.jar?raw=true";
        OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "gson-2.2.1.jar") {

                    @Override
                    public void onBefore(Request request)
                    {
                        super.onBefore(request);
                    }

                    @Override
                    public void inProgress(float progress, long total) {
                        mProgress.setProgress((int) (100 * progress));
                    }

                    @Override
                    public void onError(okhttp3.Call call, Exception e) {
                        Log.e(TAG, "onError :" + e.getMessage());
                    }

                    @Override
                    public void onResponse(File file) {
                        Log.e(TAG, "onResponse :" + file.getAbsolutePath());
                    }
                });
    }

    /**其他请求*/
    public void otherRequestDemo(View view)
    {
        //also can use delete ,head , patch
        /*
        OkHttpUtils
                .put()//
                .url("http://11111.com")
                .requestBody
                        ("may be something")//
                .build()//
                .execute(new MyStringCallback());

        OkHttpUtils
                .head()//
                .url(url)
                .addParams("name", "zhy")
                .build()
                .execute();
       */
    }

    /**清空*/
    public void clearSession(View view) {
        OkHttpUtils.getInstance().getCookieStore().removeAll();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}

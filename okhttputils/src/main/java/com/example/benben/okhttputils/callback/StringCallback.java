package com.example.benben.okhttputils.callback;

import okhttp3.Response;

/**
 * Created by benben on 2016/5/6.
 */
public abstract class StringCallback extends Callback<String> {
    @Override
    public String parseNetworkResponse(Response response) throws Exception {
        return response.body().string();
    }
}

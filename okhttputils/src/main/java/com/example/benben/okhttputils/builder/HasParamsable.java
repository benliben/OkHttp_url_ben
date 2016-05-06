package com.example.benben.okhttputils.builder;

import java.util.Map;

/**
 * Created by benben on 2016/5/6.
 */
public interface HasParamsable {
    public abstract OkHttpRequestBuilder params(Map<String, String> params);

    public abstract OkHttpRequestBuilder addParams(String key, String val);

}

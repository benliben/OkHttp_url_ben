package com.example.benben.okhttputils.builder;

import com.example.benben.okhttputils.OkHttpUtils;
import com.example.benben.okhttputils.request.OtherRequest;
import com.example.benben.okhttputils.request.RequestCall;

/**
 * Created by benben on 2016/5/6.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers).build();
    }
}

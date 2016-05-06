package com.example.benben.okhttputils.callback;

import android.telecom.Call;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by benben on 2016/5/6.
 */
public abstract class Callback<T> {

    /**
     * UI Thread
     *
     * @param request
     */
    public void onBefore(Request request)
    {
    }

    /**
     * UI Thread
     *
     * @param
     */
    public void onAfter()
    {
    }

    /**
     * UI Thread
     *
     * @param progress
     */
    public void inProgress(float progress)
    {

    }
    /**
     * Thread Pool Thread
     *
     * @param response
     */
    public abstract T parseNetworkResponse(Response response) throws Exception;

    public abstract void onError(okhttp3.Call call, Exception e);

    public abstract void onResponse(T response);


    public static Callback CALLBACK_DEFAULT = new Callback()
    {

        @Override
        public Object parseNetworkResponse(Response response) throws Exception
        {
            return null;
        }

        @Override
        public void onError(okhttp3.Call call, Exception e)
        {

        }

        @Override
        public void onResponse(Object response)
        {

        }
    };

}

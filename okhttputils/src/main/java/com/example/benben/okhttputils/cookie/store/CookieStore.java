package com.example.benben.okhttputils.cookie.store;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by tangjunjie on 2016/5/6.
 */
public interface CookieStore {
    public void add(HttpUrl url, List<Cookie> cookie);

    public  List<Cookie> get(HttpUrl uri);

    public  List<Cookie> getCookies();

    public  boolean remove(HttpUrl uri, Cookie cookie);

    public boolean removeAll();
}

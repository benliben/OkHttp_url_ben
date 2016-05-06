package com.example.benben.okhttputils.cookie.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by beneben on 2016/5/6.
 */
public class MemoryCookieStore implements CookieStore {

    private final HashMap<String, List<Cookie>> allcookies = new HashMap<>();

    @Override
    public void add(HttpUrl url, List<Cookie> cookie) {
        List<Cookie> oldCookies = allcookies.get(url.host());

        Iterator<Cookie> itMew = cookie.iterator();
        Iterator<Cookie> itOld = oldCookies.iterator();

        while (itMew.hasNext()) {
            String va=itMew.next().name();
            while (va != null && itOld.hasNext()) {
                String v = itOld.next().name();
                if (v != null && va.equals(v)) {
                    itOld.remove();
                }
            }
        }
        oldCookies.addAll(cookie);
    }

    @Override
    public List<Cookie> get(HttpUrl uri) {
        List<Cookie> cookies = allcookies.get(uri.host());
        if (cookies == null) {
            cookies = new ArrayList<>();
            allcookies.put(uri.host(), cookies);
        }
        return cookies;
    }

    @Override
    public List<Cookie> getCookies() {
        List<Cookie> cookies = new ArrayList<>();
        Set<String> httpUrls = allcookies.keySet();
        for (String url : httpUrls) {
            cookies.addAll(allcookies.get(url));
        }
        return cookies;
    }

    @Override
    public boolean remove(HttpUrl uri, Cookie cookie) {
        List<Cookie> cookies = allcookies.get(uri);
        if (cookie != null) {
            return cookies.remove(cookie);
        }
        return false;
    }

    @Override
    public boolean removeAll() {
        allcookies.clear();
        return true;
    }
}

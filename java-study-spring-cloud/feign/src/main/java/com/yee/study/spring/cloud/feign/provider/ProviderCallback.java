package com.yee.study.spring.cloud.feign.provider;

/**
 * @author Roger.Yi
 */
public class ProviderCallback{

    private String url;

    public void onResponse(String resp) {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

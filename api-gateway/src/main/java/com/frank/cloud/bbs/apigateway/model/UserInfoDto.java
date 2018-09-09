package com.frank.cloud.bbs.apigateway.model;

/**
 * Created by  Frank on 2018-1-9.
 */
public class UserInfoDto {
    private String email;
    private String userId;
    private String nickName;
    private String jsession;
    private String mipCookieUrl;
    private String mipSsoTokenUrl;
    private String oamid;
    private String mipssotoken;
    private String miptoken;
    private String mtpRole;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getJsession() {
        return jsession;
    }

    public void setJsession(String jsession) {
        this.jsession = jsession;
    }

    public String getMipCookieUrl() {
        return mipCookieUrl;
    }

    public void setMipCookieUrl(String mipCookieUrl) {
        this.mipCookieUrl = mipCookieUrl;
    }

    public String getMipSsoTokenUrl() {
        return mipSsoTokenUrl;
    }

    public void setMipSsoTokenUrl(String mipSsoTokenUrl) {
        this.mipSsoTokenUrl = mipSsoTokenUrl;
    }

    public String getOamid() {
        return oamid;
    }

    public void setOamid(String oamid) {
        this.oamid = oamid;
    }

    public String getMipssotoken() {
        return mipssotoken;
    }

    public void setMipssotoken(String mipssotoken) {
        this.mipssotoken = mipssotoken;
    }

    public String getMiptoken() {
        return miptoken;
    }

    public void setMiptoken(String miptoken) {
        this.miptoken = miptoken;
    }

    public String getMtpRole() {
        return mtpRole;
    }

    public void setMtpRole(String mtpRole) {
        this.mtpRole = mtpRole;
    }
}

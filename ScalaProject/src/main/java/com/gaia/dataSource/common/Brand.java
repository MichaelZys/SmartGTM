package com.gaia.dataSource.common;


public class Brand {

    private String openid;
    private String appkey;

    public Brand(String brand) {
        if(brand.equals("km")){
            openid = "7905800163465908101";
            appkey = "rHmdOJmPy76K0yLXv0";
        }else if(brand.equals("ky")){
            openid = "4907821606602529814";
            appkey = "3rA8rVO093SfG79rYD";
        }else if(brand.equals("by")){
            openid = "6551855645179371040";
            appkey = "97k9808W20sL2By1SL";
        }
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

//    public static void ky(){
//        String openid = "4907821606602529814";
//        String appkey = "3rA8rVO093SfG79rYD";
//    }
//
//    public static void km(){
//        String openid = "7905800163465908101";
//        String appkey = "rHmdOJmPy76K0yLXv0";
//    }
//
//    public static void by(){
//        String openid = "6551855645179371040";
//        String appkey = "97k9808W20sL2By1SL";
//    }

}

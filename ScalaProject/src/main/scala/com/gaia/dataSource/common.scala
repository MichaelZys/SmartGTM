package com.gaia.dataSource

/**
  * @author michael
  * @create 2020-04-18 10:36
  */
class common {

    def getSign(appSecret: String, api: String, appKey: String, pageIndex: String, pageSize: String, toAppKey: String, ver: String, para: String, appSecret_agin: String): String = {

        var s: String = "%sapi%sappKey%spageIndex%spageSize%stoAppKey%sver%s%s%s".format(appSecret, api, appKey, pageIndex, pageSize, toAppKey, ver, para, appSecret_agin)
        s
    }

}

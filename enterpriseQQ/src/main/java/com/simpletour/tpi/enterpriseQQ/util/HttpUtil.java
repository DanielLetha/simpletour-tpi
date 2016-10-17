package com.simpletour.tpi.enterpriseQQ.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Brief :  ${用途}
 * @Author: liangfei/liangfei@simpletour.com
 * @Date :  2016/10/17 17:28
 * @Since ： ${VERSION}
 * @Remark: ${Remark}
 */
public class HttpUtil {

    public static String httpGet(String urlstr) throws IOException {
        return httpMethod(urlstr,"GET");
    }
    public static String httpPost(String urlstr) throws IOException {
        return httpMethod(urlstr,"POST");
    }

    private static String httpMethod(String urlstr,String type) throws IOException {
        URL url = new URL(urlstr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        InputStream is = conn.getInputStream();

        byte[] buff = new byte[is.available()];
        is.read(buff);
        String ret = new String(buff, "utf-8");

        is.close();
        conn.disconnect();

        return ret;
    }
}

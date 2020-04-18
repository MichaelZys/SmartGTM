package com.gaia.dataSource.WQPackage.util;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.*;
import java.util.zip.GZIPInputStream;

public class WQPostMethod extends PostMethod {
    public WQPostMethod(String url) {
        super(url);
    }

    public String getRequestCharSet() {
        return "UTF-8";
    }

    public String getResponseCharSet() {
        return "UTF-8";
    }

    public void setRequestEntityBody(String body) throws UnsupportedEncodingException {
        addRequestHeader("Accept-Encoding", "gzip, deflate");
        setRequestEntity(new StringRequestEntity(body, "application/json", getRequestCharSet()));
    }

    public String getResponseBodyAsString()
            throws IOException {
        if ((getResponseBody() != null) || (getResponseStream() != null)) {
            Header header = getResponseHeader("Content-Encoding");
            if ((header != null) && (header.getValue().toLowerCase().indexOf("gzip") > -1)) {
                InputStream is = getResponseBodyAsStream();
                GZIPInputStream gzip = new GZIPInputStream(is);

                InputStreamReader isr = new InputStreamReader(gzip, getResponseCharSet());
                BufferedReader br = new BufferedReader(isr);
                StringBuffer sb = new StringBuffer();
                String str;
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
                isr.close();
                gzip.close();
                return sb.toString();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(getResponseBodyAsStream(), getResponseCharSet()));
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            return sb.toString();
        }

        return null;
    }
}

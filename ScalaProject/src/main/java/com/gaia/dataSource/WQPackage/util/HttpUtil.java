package com.gaia.dataSource.WQPackage.util;

import com.gaia.dataSource.WQPackage.vo.WQRequest;
import com.gaia.dataSource.WQPackage.vo.WQOpenApi;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpUtil {
    public static Log log = LogFactory.getLog(HttpUtil.class);

    public static String httpPost(WQRequest request) throws Exception
    {
        return httpPost(request.getRequestUrl(), request.getRequestdata(), request.getWqOpenApi().getTimeout());
    }

    public static String httpPost(String url, String body, int timeout) throws Exception
    {
//        log.info("REQ WQ365 URL: " + url);
//        log.info("REQ WQ365 BODY: " + body);
        HttpClient http = new HttpClient();

        http.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);

        http.getHttpConnectionManager().getParams().setSoTimeout(timeout);
        WQPostMethod method = new WQPostMethod(url.toString());
        method.setRequestEntityBody(body.toString());
        http.executeMethod(method);

        return getStreamBody(method);
    }

    private static String getStreamBody(WQPostMethod method) throws Exception
    {
        InputStream in = null;
        try
        {
            boolean isGzip = false;
            Header encodeHeader = method.getResponseHeader("Content-Encoding");
            if (encodeHeader != null)
            {
                String encode = encodeHeader.getValue();
                if (encode.contains("gzip"))
                {
                    isGzip = true;
                    log.info("RESPONSE is Gzip!");
                }
            }
            in = method.getResponseBodyAsStream();
            byte[] body = getStreamBody(in);
            byte[] unGzipBody = unGzip(body, isGzip);
            String ret = null;
            if (unGzipBody != null)
            {
                ret = new String(unGzipBody, method.getResponseCharSet());
            }
            else
            {
                ret = "";
            }
//            log.info("RES WQ365: " + ret);
            return ret;
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (Exception e)
                {
                    log.error("关闭输入流时发生异常,堆栈信息如下", e);
                    throw e;
                }
            }
        }
    }

    public static byte[] unGzip(byte[] data, boolean isGzip)
    {
        byte[] b = null;
        try
        {
            if ((data == null) || (data.length < 1))
            {
                return b;
            }

            if (isGzip)
            {
                ByteArrayInputStream bis = new ByteArrayInputStream(data);
                GZIPInputStream gzip = new GZIPInputStream(bis);
                byte[] buf = new byte[1024];
                int num = -1;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((num = gzip.read(buf, 0, buf.length)) != -1)
                {
                    baos.write(buf, 0, num);
                }
                b = baos.toByteArray();
                baos.flush();
                baos.close();
                gzip.close();
                bis.close();
            }
            else
            {
                return data;
            }
        }
        catch (Exception ex)
        {
            log.error("ungzip fail", ex);
        }
        return b;
    }

    private static byte[] getStreamBody(InputStream is)
    {
        byte[] body = null;
        ByteArrayOutputStream out = null;
        try
        {
            if (is == null)
            {
                return null;
            }
            out = new ByteArrayOutputStream();
            byte[] by = new byte[1024];
            int c;
            while ((c = is.read(by)) != -1)
            {
                out.write(by, 0, c);
            }

            body = out.toByteArray();
            if (out != null)
            {
                out.flush();
            }

        }
        catch (Exception e)
        {
            log.warn("获取请求主体信息出错，原因:", e);
        }
        finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
            }
            catch (IOException e)
            {
                log.error("关闭输入输出字节流出错，原因:", e);
            }
        }
        return body;
    }
}

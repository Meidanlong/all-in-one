package com.mdl.common.utils;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: meidanlong
 * @date: 2021/11/21 7:29 PM
 */
public class HttpUtil {

    private static final String PROXY_HOSTNAME = "";
    private static final Integer PROXY_PORT = 0;

    public static String doGet(String url) {							// 无参数get请求
        return doGet(url, null, null);
    }

    public static String doGet(String url, Map<String, String> header, Map<String, String> param) {
        return doGetWithProxy(url, header, param, false);
    }
    public static String doProxyGet(String url, Map<String, String> header, Map<String, String> param) {
        return doGetWithProxy(url, header, param, true);
    }

    private static String doGetWithProxy(String url, Map<String, String> header, Map<String, String> param, Boolean proxy) {	// 带参数get请求
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        if(proxy){
            httpClientBuilder.setProxy(new HttpHost(PROXY_HOSTNAME, PROXY_PORT));
        }
        CloseableHttpClient httpClient = httpClientBuilder.build();// 创建一个默认可关闭的Httpclient 对象
        String resultMsg = "";											// 设置返回值
        CloseableHttpResponse response = null;							// 定义HttpResponse 对象
        try {
            URIBuilder builder = new URIBuilder(url);					// 创建URI,可以设置host，设置参数等
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);					// 创建http GET请求
            httpGet.setHeaders(toHeaders(header));                     //设置请求的请求头
            response = httpClient.execute(httpGet);						// 执行请求
            if (response.getStatusLine().getStatusCode() == 200) {		// 判断返回状态为200则给返回值赋值
                resultMsg = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {														// 不要忘记关闭
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMsg;
    }

    public static String doPost(String url) {							// 无参数post请求
        return doPost(url, null, null);
    }

    public static String doPost(String url, Map<String, String> header, Map<String, String> param) {
        return doPostWithProxy(url, header, param, false);
    }

    public static String doProxyPost(String url, Map<String, String> header, Map<String, String> param) {
        return doPostWithProxy(url, header, param, true);
    }

    private static String doPostWithProxy(String url, Map<String, String> header, Map<String, String> param, Boolean proxy) {// 带参数post请求
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        if(proxy){
            httpClientBuilder.setProxy(new HttpHost(PROXY_HOSTNAME, PROXY_PORT));
        }
        CloseableHttpClient httpClient = httpClientBuilder.build();// 创建一个默认可关闭的Httpclient 对象

        CloseableHttpResponse response = null;
        String resultMsg = "";
        try {
            HttpPost httpPost = new HttpPost(url);						// 创建Http Post请求
            httpPost.setHeaders(toHeaders(header));                            //设置post请求的请求头
            if (param != null) {										// 创建参数列表
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);// 模拟表单
                httpPost.setEntity(entity);
            }
            response = httpClient.execute(httpPost);					// 执行http请求
            if (response.getStatusLine().getStatusCode() == 200) {
                resultMsg = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMsg;
    }

    public static String doPostJson(String url, Map<String, String> header, String json) {
        return doPostJsonWithProxy(url, header, json, false);
    }

    public static String doProxyPostJson(String url, Map<String, String> header, String json) {
        return doPostJsonWithProxy(url, header, json, true);
    }

    private static String doPostJsonWithProxy(String url, Map<String, String> header, String json, Boolean proxy) {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        if(proxy){
            httpClientBuilder.setProxy(new HttpHost(PROXY_HOSTNAME, PROXY_PORT));
        }
        CloseableHttpClient httpClient = httpClientBuilder.build();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeaders(toHeaders(header));                               //设置post请求的请求头
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);     //指定传输参数为json
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static Map<String, String> commonHeaders(){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }

    public static File downFile(String url, String suffix) throws IOException {
        return downFile(url, suffix, false);
    }

    public static File downFile(String url, String suffix, Boolean proxy) throws IOException {
        StopWatch watch = new StopWatch();
        watch.start();
        File tmpFile = null;
        HttpURLConnection conn = null;
        DataInputStream in = null;
        ByteArrayOutputStream out = null;
        FileOutputStream fOut = null;
        byte[] data = null;
        try {
            //创建临时文件
            tmpFile = File.createTempFile("temp", suffix);
            URL u = new URL(url);
            if (proxy) {
                conn = (HttpURLConnection) u.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_HOSTNAME, PROXY_PORT)));
            } else {
                conn = (HttpURLConnection) u.openConnection();
            }
            conn.setRequestProperty("User-Agent", "Mozilla/4.76");
            in = new DataInputStream(conn.getInputStream());
            out = new ByteArrayOutputStream();
            data = toByteArray(in, out);
            fOut = new FileOutputStream(tmpFile.getCanonicalPath());
            fOut.write(data);
        } catch (IOException e) {
            return null;
        } finally {
            closeResource(conn, in, out, fOut, null, data);
        }
        watch.stop();
        return tmpFile;
    }

    private static BasicHeader[] toHeaders(Map<String, String> headers){
        if(headers == null || headers.isEmpty()){
            return new BasicHeader[0];
        }
        BasicHeader[] basicHeaders = new BasicHeader[headers.size()];
        int index = 0;
        for(Map.Entry header : headers.entrySet()){
            basicHeaders[index++] = new BasicHeader(header.getKey().toString(), header.getValue().toString());
        }
        return basicHeaders;
    }


    /**
     * 转byte数组
     *
     * @param in
     * @param out
     * @return
     * @throws IOException
     */
    private static byte[] toByteArray(InputStream in, ByteArrayOutputStream out) throws IOException {
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    /**
     * 资源关闭
     *
     * @param conn
     * @param in
     * @param out
     * @param fOut
     * @param raf
     * @throws IOException
     */
    private static void closeResource(HttpURLConnection conn, DataInputStream in, ByteArrayOutputStream out,
                                      FileOutputStream fOut, RandomAccessFile raf, byte[] data) throws IOException {
        if (conn != null) {
            conn.disconnect();
            conn = null;
        }
        if (in != null) {
            in.close();
            in = null;
        }
        if (out != null) {
            out.close();
            out = null;
        }
        if (fOut != null) {
            fOut.close();
            fOut = null;
        }
        if (raf != null) {
            raf.close();
            raf = null;
        }
        if (data != null) {
            data = null;
        }
    }

}

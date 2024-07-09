package com.yiyue.common.util;


import com.yiyue.common.exception.CustomException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.MediaType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: XiaoQi
 * @Date: 2020/9/11 13:45
 */
public class HttpsUtil {

    private static RequestConfig requestConfig;

    /**
     * 建立连接的超时时间 (ms)
     */
    private static final int CONNECT_TIME_OUT = 600000;

    /**
     * 获取数据的超时时间 (ms)
     */
    private static final int SOCKET_TIME_OUT = 600000;

    /**
     * 连接池获取到连接的超时时间 (ms)
     */
    private static final int CONNECTION_REQUEST_TIME_OUT = 600000;

    static {
        RequestConfig.Builder configBuilder = RequestConfig.custom()
                // 设置建立连接的超时时间
                .setConnectTimeout(CONNECT_TIME_OUT)
                // 设置获取数据的超时时间
                .setSocketTimeout(SOCKET_TIME_OUT)
                // 设置连接池获取到连接的超时时间
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIME_OUT);
        requestConfig = configBuilder.build();
    }


    /**
     * 发送GET请求（HTTP），不带参数
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String doGet(String url) {
        return doGet(url, null, null);
    }


    public static String doGet(String url, Map<String, Object> params, Map<String, String> headerParams) {
        HttpClient httpClient = HttpClients.createDefault();
        String result = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            // 添加参数
            if (!Objects.isNull(params)) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue().toString();
                    builder.setParameter(key, value);
                }
            }
            HttpGet httpGet = new HttpGet(builder.build());
            // 添加Header
            if (!Objects.isNull(headerParams)) {
                for (Map.Entry<String, String> entry : headerParams.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    httpGet.setHeader(key, value);
                }
            }
            httpGet.setConfig(requestConfig);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, StandardCharsets.UTF_8.toString());
            }
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
        return result;
    }

    /**
     * 发送 POST 请求（HTTP），不带输入数据
     *
     * @param url
     * @return
     */
    public static String doPost(String url) {
        return doPost(url, "", null);
    }


    /**
     * 发送 POST 请求，K-V形式
     *
     * @param url    API接口URL
     * @param params 参数map
     * @return
     */
    public static String doPost(String url, Map<String, Object> params, Map<String, String> headerParams) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result;
        CloseableHttpResponse response = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            HttpPost httpPost = new HttpPost(builder.build());
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<>();
            // 添加参数
            if (!Objects.isNull(params)) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue().toString();
                    NameValuePair pair = new BasicNameValuePair(key, value);
                    pairList.add(pair);
                }
            }
            // 添加Header
            if (!Objects.isNull(headerParams)) {
                for (Map.Entry<String, String> stringStringEntry : headerParams.entrySet()) {
                    Map.Entry<String, String> entry;
                    entry = stringStringEntry;
                    String key = entry.getKey();
                    String value = entry.getValue();
                    httpPost.setHeader(key, value);
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, StandardCharsets.UTF_8.toString()));
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, StandardCharsets.UTF_8.toString());
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * 发送 POST 请求，JSON形式
     *
     * @param url
     * @param json
     * @return
     */
    public static String doPost(String url, String json, Map<String, String> headerParams) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result;
        CloseableHttpResponse response = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            HttpPost httpPost = new HttpPost(builder.build());
            httpPost.setConfig(requestConfig);
            // 添加Header
            if (!Objects.isNull(headerParams)) {
                for (Map.Entry<String, String> stringStringEntry : headerParams.entrySet()) {
                    Map.Entry<String, String> entry;
                    entry = stringStringEntry;
                    String value = entry.getValue();
                    String key = entry.getKey();
                    httpPost.setHeader(key, value);
                }
            }
            StringEntity stringEntity = new StringEntity(json, StandardCharsets.UTF_8.toString());
            stringEntity.setContentEncoding(StandardCharsets.UTF_8.toString());
            stringEntity.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, StandardCharsets.UTF_8.toString());
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 发送 POST 请求，JSON形式
     *
     * @param url
     * @param json
     * @param headerParams
     * @return
     */
    public static byte[] doPostResultByte(String url, String json, Map<String, String> headerParams) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        byte[] buffer = new byte[1024];
        try {
            URIBuilder builder = new URIBuilder(url);
            HttpPost httpPost = new HttpPost(builder.build());
            httpPost.setConfig(requestConfig);
            // 添加Header
            if (!Objects.isNull(headerParams)) {
                for (Map.Entry<String, String> stringStringEntry : headerParams.entrySet()) {
                    Map.Entry<String, String> entry;
                    entry = stringStringEntry;
                    String value = entry.getValue();
                    String key = entry.getKey();
                    httpPost.setHeader(key, value);
                }
            }
            StringEntity stringEntity = new StringEntity(json, StandardCharsets.UTF_8.toString());
            stringEntity.setContentEncoding(StandardCharsets.UTF_8.toString());
            stringEntity.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
                int count;
                while ((count = instream.read(buffer, 0, 100)) > 0) {
                    swapStream.write(buffer, 0, count);
                }
                return swapStream.toByteArray();
            }
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return buffer;
    }
}

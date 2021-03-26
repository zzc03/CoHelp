package com.example.a22857.cohelp;

import android.util.Log;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Interface {



    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static OkHttpClient client;
    static {
        client = new OkHttpClient.Builder()
                .readTimeout(20000, TimeUnit.MILLISECONDS)
                .writeTimeout(20000, TimeUnit.MILLISECONDS)
                .connectTimeout(20000, TimeUnit.MILLISECONDS)
                .build();
    }

    /**
     * userId : 1234567
     * password : 123456
     * age : 23
     * sex : man
     */

    private String userId;
    private String password;
    private String age;
    private String sex;


    /**
     * get 请求
     * @param url       请求url地址
     * @return string
     * */
    public static String doGet(String url) {
        return doGet(url, null, null);
    }


    /**
     * get 请求
     * @param url       请求url地址
     * @param params    请求参数 map
     * @return string
     * */
    public String doGet(String url, Map<String, String> params) {
        return doGet(url, params, null);
    }




    /**
     * get 请求
     *
     * @param url     请求url地址
     * @param params  请求参数 map
     * @param headerMap 请求头字段
     * @return string
     */
    public static String doGet(String url, Map<String, String> params, Map<String, String> headerMap) {
        Log.i("--------------","进入doget方法");
        StringBuilder sb = new StringBuilder(url);
        if (params != null && params.keySet().size() > 0) {
            boolean firstFlag = true;
            for (String key : params.keySet()) {
                if (firstFlag) {
                    sb.append("?").append(key).append("=").append(params.get(key));
                    firstFlag = false;
                } else {
                    sb.append("&").append(key).append("=").append(params.get(key));
                }
            }
        }

        Request.Builder builder = new Request.Builder();
        if (headerMap != null && headerMap.size() > 0) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        Request request = builder.url(sb.toString()).build();
//        logger.info("do get request and url[{}]", sb.toString());
        Log.v("url-----", sb.toString());
        return execute(request);
    }

    /**
     * post 请求
     *
     * @param url    请求url地址
     * @param params 请求参数 map
     * @return string
     */
    public static String doPost(String url, Map<String, String> params,okhttp3.Callback callback) {
        OkHttpClient client=new OkHttpClient();
        RequestBody body=new FormBody.Builder()
                .add("userId",params.get("userId"))
                .add("parentId",params.get("parentId"))
                .build();
        Log.i("点赞/取消点赞路径为--------",url+"--"+params.get("userId")+"--"+params.get("parentId"));
        Request request=new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
        return "";
    }

    public static String doPut(String url, Map<String, String> params, Map<String, String> headerParam) {

        FormBody formBody =addParamToBuilder(params);

        Request.Builder request = buildHeader(headerParam);

        Request buildRequest = request.put(formBody).url(url).build();
        //logger.info("do put request and url[{}]", url);
        Log.v("put url-----", url);
        return execute(buildRequest);
    }

    public static String doDelete(String url, Map<String, String> params, Map<String, String> headerParam) {

        FormBody formBody =addParamToBuilder(params);

        Request.Builder request = buildHeader(headerParam);

        Request buildRequest = request.delete(formBody).url(url).build();
        //logger.info("do delete request and url[{}]", url);
        Log.v("delete url-----", url);
        return execute(buildRequest);
    }


    private static FormBody addParamToBuilder(Map<String, String> params){
        FormBody.Builder builder = new FormBody.Builder();

        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }

        return builder.build();
    }

    private static  Request.Builder buildHeader(Map<String, String> headerParam){
        Request.Builder request = new Request.Builder();
        if ( headerParam.size() > 0) {
            for (Map.Entry<String, String> entry : headerParam.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return request;
    }

    /**
     * post 请求, 请求数据为 json 的字符串
     * @param url       请求url地址
     * @param json      请求数据, json 字符串
     * @return string
     */
    public static String doPostJson(String url,Map<String,String> headermap, String json) {
        //logger.info("do post request and url[{}]", url);
        Log.v("post jason url-----", url);
        return exectePost(url, json,headermap, JSON);
    }

    public static String doPutJson(String url, Map<String,String> headermap, String json) {
        // logger.info("do put request and url[{}]", url);
        Log.v("put jason url-----", url);
        return exectePut(url, json,headermap, JSON);
    }

    public static String doDeleteJson(String url, Map<String,String> headermap, String json) {
        //logger.info("do delete request and url[{}]", url);
        Log.v("delete jason url-----", url);
        return execteDelete(url, json,headermap, JSON);
    }



    private static String exectePost(String url, String data,Map<String,String> headerMap, MediaType contentType) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        // Request request = new Request.Builder().url(url).post(requestBody).build();
        Request.Builder builder = buildHeader(headerMap);
        Request request = builder.post(requestBody).url(url).build();
        return execute(request);
    }

    private static String exectePut(String url, String data,Map<String,String> headerMap, MediaType contentType) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        // Request request = new Request.Builder().url(url).put(requestBody).build();
        Request.Builder builder = buildHeader(headerMap);
        Request request = builder.put(requestBody).url(url).build();
        return execute(request);
    }

    private static String execteDelete(String url, String data, Map<String,String> headerMap,MediaType contentType) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        //Request request = new Request.Builder().url(url).delete(requestBody).build();
        Request.Builder builder = buildHeader(headerMap);
        Request request = builder.delete(requestBody).url(url).build();
        return execute(request);
    }




    private static String execute(Request request) {
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return new String(response.body().bytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return "";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
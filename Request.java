package com.linxi;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Request {

    public String method;//请求方法
    private String url;//网页地址
    private String version;//协议版本号
    private Map<String, String> headers = new HashMap<>();
    public Map<String, String> params = new HashMap<>();

    public static Request parse(InputStream is) throws IOException {
        Request request = new Request();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        //解析请求行
        parseRequestLine(reader, request);
        //解析请求头
        parseRequestHeader(reader, request);
        //解析请求体
        if (request.method.toUpperCase().equals("POST")){
            parseRequestBody(reader, request);
        }
        return request;
    }

    private static void parseRequestLine(BufferedReader reader, Request request) throws IOException {
        String line = reader.readLine();
        if (line == null){
            throw new IOException("读到空行 EOF");
        }
        String[] fragments = line.split(" ");
        request.setMethod(fragments[0]);
        request.setUrl(fragments[1]);
        request.setVersion(fragments[2]);
    }

    private static void parseRequestHeader(BufferedReader reader, Request request) throws IOException {
        String line;
        while ((line = reader.readLine()) != null && line.length() != 0){
            //请求头的键值对用 ":" 隔开
            String[] kv = line.split(":");
            String key = kv[0].trim();
            String value = kv[1].trim();
            request.headers.put(key, value);
        }
    }

    private void setRequestParams(String s) throws UnsupportedEncodingException {
        for (String kv : s.split("&")){
            String[] kvs = kv.split("=");
            String key = URLDecoder.decode(kvs[0],"UTF-8");
            String value = URLDecoder.decode(kvs[1],"UTF-8");
            params.put(key, value);
        }
    }

    private static void parseRequestBody(BufferedReader reader, Request request) throws IOException {
        int len = Integer.parseInt(request.headers.get("Content-Length"));
        char[] buf = new char[len];
        reader.read(buf, 0 ,len);
        request.setRequestParams(String.valueOf(buf));
    }

    private void setMethod(String fragment) {
        method = fragment;
    }

    private void setUrl(String fragment) throws UnsupportedEncodingException {
        //若 url 中有键值对，则要用 ？ 分隔
        //index?name=linxi?time=1  ==> QueryString
        String[] group = fragment.split("\\?");
        url = group[0];
        if (group.length > 1){
            //若有 QueryString 则获取参数
            setRequestParams(group[1]);
        }
    }

    private void setVersion(String fragment) {
        version = fragment;
    }

    public String getUrl() {
        return url;
    }
}

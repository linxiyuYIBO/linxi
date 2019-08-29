package com.linxi;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Response {

    private OutputStream os;
    private Map<String, String> headers = new HashMap<>();
    private byte[] buf = new byte[8192];
    private int contentLength = 0;
    public String status = "200 OK";

    public static Response build(OutputStream os) {
        Response response = new Response();
        response.os = os;
        response.setDefaultHeaders();
        return response;
    }

    private void setDefaultHeaders() {
        setHeader("Server","JM/1.0.0");
        setHeader("Content-Type","text/html; charset=UTF-8");
    }

    public void setHeader(String server, String s) {
        headers.put(server, s);
    }

    public void println(Object o) throws UnsupportedEncodingException {
        write(o.toString().getBytes("UTF-8"));
    }

    private void write(byte[] bytes) {
        write(bytes, bytes.length);
    }

    public void write(byte[] bytes, int length) {
        System.arraycopy(bytes, 0, buf, contentLength, length);
        contentLength += length;
    }

    public void flush() throws IOException {
        setHeader("Content-Length",String.valueOf(contentLength));
        sendResponseLine();
        sendResponseHeader();
        sendResponseBody();
    }

    private void sendResponseLine() throws IOException {
        // HTTP/1.0 200 OK\r\n
        os.write(("HTTP/1.0 "+ status + "\r\n").getBytes("UTF-8"));

    }
    private void sendResponseHeader() throws IOException {
        for (Map.Entry<String, String> header: headers.entrySet()){
            os.write((header.getKey() + ":" + header.getValue()
                    + "\r\n").getBytes("UTF-8"));
        }
        os.write("\r\n".getBytes("UTF-8"));
    }

    private void sendResponseBody() throws IOException {
        os.write(buf);
    }
}

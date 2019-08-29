package com.linxi;

import java.io.*;

public class StaticController implements Controller {

    public void doGet(Request request, Response response) throws IOException {
        String url = request.getUrl();
        if (url.equals("/")){
            url = "post.html";
        }
        String filename = "D:\\\\httpd\\webapp\\" + url;
        /**
         * String filename = getFilename(request.getUrl());
         * InputStream is = new FileInputStream();
         * byte[] buf = new byte[1024];
         * int len;
         * while(len = is.read(buf))
         */


        File file = new File(filename);
        InputStream is = new FileInputStream(file);
        byte[] buf = new byte[8192];
        int len = is.read(buf);
        response.write(buf, len);
        is.close();
    }
}

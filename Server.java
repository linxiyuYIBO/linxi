package com.linxi;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private Controller staticController = new StaticController();
    private CustomClassLoader classLoader = new CustomClassLoader();

    public static void main(String[] args) throws IOException{
        new Server().run();
    }

    private void run() throws IOException {
        //线程数量
        ExecutorService pool = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true){
            Socket socket = serverSocket.accept();
            pool.execute(new Runnable() {
                public void run() {
                    try {
                        Request request = Request.parse(socket.getInputStream());
                        System.out.println(request);
                        Response response = Response.build(socket.getOutputStream());
                        //System.out.println(response);
                        Controller controller = null;
                        if (hasStaticFile(request.getUrl())){
                            controller = staticController;
                        } else {
                            //动态全归为Hello
                            String className;
                            if (request.getUrl().equals("/")) {
                                //列表页
                                className = "ListController";
                            } else if (request.getUrl().equals("/article")){
                                //博客详情页
                                className = "ArticleController";
                            } else if (request.getUrl().equals("/post")){
                                //写入博客内容
                                className = "PostController";
                            } else {
                                className = "ListController";
                            }
                            Class<?> cls = classLoader.loadClass(className);
                            controller = (Controller)cls.newInstance();
                        }
                        if (request.method.toUpperCase().equals("GET")) {
                            controller.doGet(request, response);
                        } else {
                            controller.doPost(request, response);
                        }
                        response.flush();
                        socket.close();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private boolean hasStaticFile(String url) {
        if (url.equals("/")){
            url = "post.html";
        }
        String filename = "D:\\\\httpd\\webapp\\" + url;
        File file = new File(filename);
        return file.exists();
    }
}

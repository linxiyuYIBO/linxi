package com.linxi.database;

import java.sql.*;

public class DataBase {
    public static void dataBase(String[] args) {
        //声明Connection对象
        Connection con = null;
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://localhost:3306/sqltestdb";
        //MySQL配置时的用户名
        String user = "root";
        //MySQL配置时的密码
        String password = "123456";
        Statement st = null;
        ResultSet rs = null;
        //遍历查询结果集
        try {
        //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url,user,password);
            if(!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //2.创建statement类对象，用来执行SQL语句！！
            st = con.createStatement();
            //要执行的SQL语句
            String sql = "select * from goods";
            //3.ResultSet类，用来存放获取的结果集！！
            rs = st.executeQuery(sql);
            System.out.println("------------------------------------------------");
            System.out.println("               执行结果如下所示:                ");
            System.out.println("------------------------------------------------");
            System.out.println("商品编号" + "\t" +"商品名称" + "\t" + "商品价格");
            System.out.println("-------------------------------------------------");
            String id = null;
            String name = null;
            double price = 0.0D;
            while(rs.next()) {
                id = rs.getString("id");
                name = rs.getString("name");
                price = rs.getDouble("price");
                //输出结果
                System.out.println(id + "\t" + name + "\t" + price);
            }
        } catch(ClassNotFoundException e) {
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
            }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            System.out.println("数据库数据成功获取！！");
            try {
                if (rs == null) {
                    rs.close();
                }
                if (st == null){
                    st.close();
                }
                if (con == null){
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

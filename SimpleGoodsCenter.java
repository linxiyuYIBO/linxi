package com.linxi;

import com.linxi.goods.Goods;
import com.linxi.goods.GoodsCenter;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

//商品管理中心：管理商品的所有信息
//             对存储文件中的商品与用户选择的商品做匹配
public class SimpleGoodsCenter implements GoodsCenter {
    private final Map<String, Goods> goodsMap = new HashMap<>();

    @Override
    public void addGoods(Goods goods) {
        this.goodsMap.put(goods.getId(), goods);
    }

    @Override
    public void removeGoods(String goodsId) {
        this.goodsMap.remove(goodsId);
    }

    @Override
    public void updateGoods(Goods goods) {
        if (this.goodsMap.containsKey(goods.getId())) {
            this.goodsMap.put(goods.getId(), goods);
        }
    }

    @Override
    public boolean isExistGoods(String goodsId) {
        return this.goodsMap.containsKey(goodsId);
    }

    @Override
    public Goods getGoods(String goodsId) {
        return this.goodsMap.get(goodsId);
    }

    @Override
    public String listGoods() {
        StringBuilder sb = new StringBuilder();
        sb.append("******************  商品清单  ********************");
        sb.append("\n");
        sb.append("\t" + "编号" + "\t" + "产品名称" + "\t\t" + "单价");
        sb.append("\n");

        for (Goods goods : this.goodsMap.values()) {
            sb.append("\t" + goods.getId() + "\t" + goods.getName() + "\t\t" + goods.getPrice());
            sb.append("\n");
        }
        sb.append("*************************************************");
        sb.append("\n");
        return sb.toString();
    }

    private String goodsFile = System.getProperty("user.dir") + File.separator + "goods.txt";

    @Override
    public void store() {
        //TODO goodsMap数据存储到文件
        //存储格式自定义：
        //id:name:price
        System.out.println("保存所有商品到文件：编号，名称，单价");
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(goodsFile)
        )) {
            for (Map.Entry<String, Goods> entry : this.goodsMap.entrySet()) {
                Goods goods = entry.getValue();
                String line = String.format("%s:%s:%f", goods.getId(), goods.getName(), goods.getPrice());
                writer.write(line);
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("保存商品信息出错：" + e.getMessage());
        }
    }

    @Override
    public void load() {
        //TODO 文件中的数据读取，goods -> goodsMap
        System.out.println("从文件中读取商品信息，实例化对象");
        File file = new File(goodsFile);
        if (file.exists() && file.isFile()) {
            try (BufferedReader reader = new BufferedReader(
                    new FileReader(goodsFile)
            )) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(":");
                    if (values.length == 3) {
                        String id = values[0];
                        String name = values[1];
                        double price = Double.parseDouble(values[2]);

                        this.goodsMap.put(id, new Goods(id, name, price));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("加载文件信息的文件不存在：" + goodsFile);
        }
    }
}

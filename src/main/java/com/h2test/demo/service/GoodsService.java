package com.h2test.demo.service;

import com.h2test.demo.mapper.GoodsMapper;
import com.h2test.demo.pojo.Goods;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class GoodsService {
    @Resource
    private GoodsMapper goodsMapper;

    @Resource(name = "h2Template")
    private JdbcTemplate h2Template;

    public Goods getH2GoodsById(Long goodsId) {
        Map<String, Object> map = h2Template.queryForMap("select * from goods");
//        Goods goodsOne = goodsMapper.selectOneGoods(goodsId);
        System.out.println(map);
        Goods goods = new Goods();
        BeanUtils.copyProperties(map,goods);
        return goods;
    }

    //得到一件商品的信息
    public Goods getOneGoodsById(Long goodsId) {
        System.out.println("get data from mysql");
        Goods goodsOne = goodsMapper.selectOneGoods(goodsId);
        System.out.println(goodsOne);
        return goodsOne;
    }

    //添加一件商品到数据库
    public Long addOneGoods(Goods goods) {
        int insNum = goodsMapper.insertOneGoods(goods);
        if (insNum == 0) {
            return 0L;
        } else {
            Long goodsId = goods.getGoodsId();//该对象的自增ID
            return goodsId;
        }
    }

    public int addH2OneGoods(Goods goods) {
        String insertSql = "insert into goods(goodsName,subject,price,stock) values (?,?,?,?)";
        int update = h2Template.update(insertSql, goods.getGoodsName(),goods.getSubject(),goods.getPrice(),goods.getStock());
        System.out.println("影响条数："+update);
        return update;
    }
}

package com.h2test.demo.controller;

import com.h2test.demo.constant.ResponseCode;
import com.h2test.demo.pojo.Goods;
import com.h2test.demo.result.RestResult;
import com.h2test.demo.service.GoodsService;

import javax.annotation.Resource;
import org.springframework.cache.CacheManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.sql.SQLException;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @Resource(name = "h2Template")
    private JdbcTemplate h2Template;

    //商品详情 参数:商品id
    @GetMapping("/one")
    @ResponseBody
    public RestResult goodsInfo(@RequestParam(value="goodsid",required = true,defaultValue = "0") Long goodsId) {
        Goods goods = goodsService.getOneGoodsById(goodsId);
        if (goods == null) {
            return RestResult.error(ResponseCode.GOODS_NOT_EXIST);
        } else {
            return RestResult.success(goods);
        }
    }

    @GetMapping("/h2one")
    @ResponseBody
    public RestResult h2goodsInfo(@RequestParam(value="goodsid",required = true,defaultValue = "0") Long goodsId) {
        Goods goods = goodsService.getH2GoodsById(goodsId);
        if (goods == null) {
            return RestResult.error(ResponseCode.GOODS_NOT_EXIST);
        } else {
            return RestResult.success(goods);
        }
    }

    @GetMapping("/init")
    @ResponseBody
    public void init() throws SQLException {

        String initSql = "CREATE TABLE goods (\n" +
                " goodsId BIGINT(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',\n" +
                " goodsName varchar(500)  NOT NULL DEFAULT '' COMMENT 'name',\n" +
                " subject varchar(200) NOT NULL DEFAULT '' COMMENT '标题',\n" +
                " price decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '价格',\n" +
                " stock int(11) NOT NULL DEFAULT '0' COMMENT 'stock',\n" +
                " PRIMARY KEY (`goodsId`),\n" +
                " UNIQUE KEY `goodsName` (`goodsName`)\n" +
                ")";

        h2Template.execute(initSql);
    }

    @GetMapping("/insert")
    @ResponseBody
    public void insert() throws SQLException {

        Goods goodsOne = new Goods();
        //goodsOne.setGoodsId(13L);
        goodsOne.setGoodsName("商品名称");
        goodsOne.setSubject("商品描述");
        goodsOne.setPrice(new BigDecimal(101));
        goodsOne.setStock(13);

        int goodsId = goodsService.addH2OneGoods(goodsOne);
        System.out.println(goodsId);
    }
}


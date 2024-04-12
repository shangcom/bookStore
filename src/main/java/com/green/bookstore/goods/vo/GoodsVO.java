package com.green.bookstore.goods.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
public class GoodsVO {
    private int goods_id;
    private String goods_title;
    private String goods_writer;
    private int    goods_price;
    private String goods_publisher;
    private String goods_sort;
    private int    goods_sales_price;
    private int    goods_point;
    private Date    goods_published_date;
    private int    goods_total_page;
    private String goods_isbn;
    private String goods_delivery_price;
    private Date goods_delivery_date;
    private String goods_fileName;
    private String goods_status;
    private String goods_writer_intro;
    private String goods_contents_order;
    private String goods_intro;
    private String goods_publisher_comment;
    private String goods_recommendation;
    private Date   goods_credate;

    public GoodsVO() {
    }


}

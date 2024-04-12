package com.green.bookstore.goods.controller;

import com.green.bookstore.common.base.BaseController;
import com.green.bookstore.goods.service.GoodsService;
import com.green.bookstore.goods.controller.GoodsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("goodsController")
@RequestMapping(value="/goods")
public class GoodsControllerImpl extends BaseController implements GoodsController {

    @Autowired
    private GoodsService goodService;

    @Override
    public ModelAndView goodsDetail(String goods_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }

    @Override
    public String keywordSearch(String keyword, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }

    @Override
    public ModelAndView searchGoods(String searchWord, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }
}

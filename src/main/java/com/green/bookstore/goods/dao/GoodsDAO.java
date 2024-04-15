package com.green.bookstore.goods.dao;

import com.green.bookstore.goods.vo.GoodsVO;
import com.green.bookstore.goods.vo.ImageFileVO;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface GoodsDAO {
    public List<GoodsVO> selectGoodsList(String goodsStatus ) throws DataAccessException;
    public List<String> selectKeywordSearch(String keyword) throws DataAccessException;
    public GoodsVO selectGoodsDetail(String goods_id) throws DataAccessException;
    public List<ImageFileVO> selectGoodsDetailImage(String goods_id) throws DataAccessException;
    public List<GoodsVO> selectGoodsBySearchWord(String searchWord) throws DataAccessException;
}


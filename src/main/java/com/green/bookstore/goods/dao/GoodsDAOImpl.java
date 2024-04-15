package com.green.bookstore.goods.dao;

import com.green.bookstore.goods.vo.GoodsVO;
import com.green.bookstore.goods.vo.ImageFileVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("goodsDAO")
public class GoodsDAOImpl implements GoodsDAO {

/*  목적: 데이터베이스와의 상호작용을 추상화하고, 비즈니스 로직에서 데이터베이스 연산을 쉽게 수행할 수 있도록 함.
    작동 방식: @Repository 어노테이션이 붙은 GoodsDAOImpl 클래스는 MyBatis의 SqlSession을 사용하여 Mapper XML에 정의된 쿼리를 실행함.
    예를 들어, selectGoodsList 메서드는 "mapper.goods.selectGoodsList"에 해당하는 SQL 쿼리를 실행하고, 결과를 GoodsVO 객체의 리스트로 반환.*/

    @Autowired
    private SqlSession sqlSession;  // /webapp/WEB-INF/spring/mybatis-context.xml에 정의되어 있음.

    @Override
    public List<GoodsVO> selectGoodsList(String goodsStatus) throws DataAccessException {
        List<GoodsVO> goodsList = (ArrayList) sqlSession.selectList("mapper.goods.selectGoodsList", goodsStatus);
        return goodsList;
    }

    @Override
    public List<String> selectKeywordSearch(String keyword) throws DataAccessException {
        List<String> list=(ArrayList)sqlSession.selectList("mapper.goods.selectKeywordSearch",keyword);
        return list;
    }
    @Override
    public GoodsVO selectGoodsDetail(String goods_id) throws DataAccessException{
        GoodsVO goodsVO=(GoodsVO)sqlSession.selectOne("mapper.goods.selectGoodsDetail",goods_id);
        return goodsVO;
    }

    @Override
    public List<ImageFileVO> selectGoodsDetailImage(String goods_id) throws DataAccessException{
        List<ImageFileVO> imageList=(ArrayList)sqlSession.selectList("mapper.goods.selectGoodsDetailImage",goods_id);
        return imageList;
    }

    @Override
    public List<GoodsVO> selectGoodsBySearchWord(String searchWord) throws DataAccessException {
        List<GoodsVO> list = (ArrayList) sqlSession.selectList("mappers.goods.selectGoodsBySearchWord", searchWord);
        return list;
    }


}

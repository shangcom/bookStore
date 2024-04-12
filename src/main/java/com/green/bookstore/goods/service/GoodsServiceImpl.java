package com.green.bookstore.goods.service;



import com.green.bookstore.goods.dao.GoodsDAO;
import com.green.bookstore.goods.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("goodsService")
@Transactional(propagation= Propagation.REQUIRED) //  스프링의 선언적 트랜잭션 관리 기능. 데이터의 일관성과 무결성을 유지할 수 있으며, 여러 데이터베이스 작업을 하나의 작업 단위로 묶어 처리
/*REQUIRED 옵션 : 호출되는 메소드는 호출하는 메소드가 이미 시작한 트랜잭션에 자동으로 참여.
만약 최초 호출 메소드가 트랜잭션 없이 실행되고 있다면, 첫 @Transactional 메소드 호출 시 새로운 트랜잭션이 시작됨.
이 트랜잭션은 @Transactional 어노테이션이 붙은 메소드의 실행이 완료될 때까지 유지되며, 실행 중 예외가 발생하면 트랜잭션은 롤백됨.*/
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDAO goodsDAO;

    @Override
    public Map<String, List<GoodsVO>> listGoods() throws Exception {
        // bestseller, newbook. steadyseller를 조건으로 각각 도서정보를 조회해서 HashMap에 저장한 후 반환.
        Map<String, List<GoodsVO>> goodsMap = new HashMap<String, List<GoodsVO>>();

        List<GoodsVO> goodsList = goodsDAO.selectGoodsList("bestseller");
        goodsMap.put("bestseller", goodsList);

        goodsList = goodsDAO.selectGoodsList("newbook");
        goodsMap.put("newbook", goodsList);

        goodsList = goodsDAO.selectGoodsList("steadyseller");
        goodsMap.put("steadyseller", goodsList);

        return goodsMap;
    }

    @Override
    public Map goodsDetail(String _goods_id) throws Exception {
        return null;
    }

    @Override
    public List<String> keywordSearch(String keyword) throws Exception {
        return null;
    }

    @Override
    public List<GoodsVO> searchGoods(String searchWord) throws Exception {
        return null;
    }

}

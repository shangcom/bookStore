package com.green.bookstore.goods.service;


import com.green.bookstore.goods.dao.GoodsDAO;
import com.green.bookstore.goods.vo.GoodsVO;
import com.green.bookstore.goods.vo.ImageFileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("goodsService") // 비즈니스 로직 처리하는 서비스 컴포넌트임을 나타내는 어노테이션.
@Transactional(propagation = Propagation.REQUIRED)
//  스프링의 선언적 트랜잭션 관리 기능. 데이터의 일관성과 무결성을 유지할 수 있으며, 여러 데이터베이스 작업을 하나의 작업 단위로 묶어 처리
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

    @Override //상품 상세 정보
    public Map goodsDetail(String _goods_id) throws Exception {
        Map goodsMap = new HashMap(); //상품 상세 정보와 이미지 리스트를 담을 Map 객체를 생성
        GoodsVO goodsVO = goodsDAO.selectGoodsDetail(_goods_id); //상품 ID를 이용해 상품 상세 정보를 조회
        goodsMap.put("goodsVO", goodsVO); //조회된 상품 상세 정보(goodsVO)를 goodsMap에 "goodsVO"라는 키로 추가
        List<ImageFileVO> imageList = goodsDAO.selectGoodsDetailImage(_goods_id); //상품 ID를 이용해 해당 상품의 이미지 리스트를 조회
        goodsMap.put("imageList", imageList); //조회된 이미지 리스트를 goodsMap에 "imageList"라는 키로 추가
        return goodsMap; // 상품의 상세 정보와 관련 이미지 리스트를 포함하는 Map 객체를 반환
    }

    @Override
    public List<String> keywordSearch(String keyword) throws Exception {
        List<String> list = goodsDAO.selectKeywordSearch(keyword);
        return list;
    }

    @Override
    public List<GoodsVO> searchGoods(String searchWord) throws Exception {
        List goodsList = goodsDAO.selectGoodsBySearchWord(searchWord);
        return goodsList;
    }

}

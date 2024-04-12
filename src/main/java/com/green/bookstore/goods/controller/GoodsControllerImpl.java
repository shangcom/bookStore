package com.green.bookstore.goods.controller;

import com.green.bookstore.common.base.BaseController;
import com.green.bookstore.goods.service.GoodsService;
import com.green.bookstore.goods.controller.GoodsController;
import com.green.bookstore.goods.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller("goodsController")
@RequestMapping(value="/goods")
public class GoodsControllerImpl extends BaseController implements GoodsController {

    @Autowired
    private GoodsService goodService;

    @Override
    public ModelAndView goodsDetail(@RequestParam("goods_id") String goods_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String viewName = (String) request.getAttribute("viewName"); //현재 요청된 매핑의 뷰 이름을 가져옴.
        HttpSession session = request.getSession(); // 최근 본 상품 목록에 정보 전달하기 위한 세션 생성.
        Map goodsMap = goodService.goodsDetail(goods_id); // 상품 상세 정보 조회하여 goodsMap 형태로 반환.
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("goodsMap", goodsMap); // 모델앤뷰 생성하고 상품 정보가 담긴 goodsMap을 "goodsMap"이란 이름으로 추가함. 이를 통해 뷰에서 상품 정보 사용 가능.

        // 최근 본 상품 위한 코드
        GoodsVO goodsVO = (GoodsVO) goodsMap.get("goodsVO"); //
        addGoodsInQuick(goods_id, goodsVO, session); // 최근 본 상품 목록에 추가
        return mav;
    }

    @Override
    public String keywordSearch(String keyword, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }

    @Override
    public ModelAndView searchGoods(String searchWord, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }

    private void addGoodsInQuick(String goods_id, GoodsVO goodsVO, HttpSession session) {
        boolean already_existed=false;
        List<GoodsVO> quickGoodsList;
        quickGoodsList=(ArrayList<GoodsVO>)session.getAttribute("quickGoodsList");
        // 세션에서 "quickGoodsList"를 가져옴


        if(quickGoodsList != null){ // 최근 본 상품 리스트에 상품이 있을 경우
            if(quickGoodsList.size() < 4) { // 4개 미만일 경우
                for (int i = 0; i < quickGoodsList.size(); i++) {
                    GoodsVO _goodsBean = (GoodsVO) quickGoodsList.get(i);
                    if (goods_id.equals(_goodsBean.getGoods_id())) { // 상품 ID(goods_id)가 목록에 있는 상품들의 ID와 일치하면, 이미 존재하는 것으로 간주함.
                        already_existed=true;
                        break;
                    }
                }
                if (already_existed == false) { // 목록의 크기가 4보다 작고, 상품이 목록에 이미 존재하지 않는 경우, 상품을 목록에 추가.
                    quickGoodsList.add(goodsVO);
                }
            }
        } else { // 최근 본 상품 리스트가 비어있는 경우, 새로운 ArrayList(GoodsVO)를 생성.
            quickGoodsList = new ArrayList<GoodsVO>();
            quickGoodsList.add(goodsVO);
        }

        // 변경된 "quickGoodsList"와 그 크기("quickGoodsListNum")를 세션에 저장
        session.setAttribute("quickGoodsList", quickGoodsList);
        session.setAttribute("quickGoodsListNum", quickGoodsList.size());
    }
}

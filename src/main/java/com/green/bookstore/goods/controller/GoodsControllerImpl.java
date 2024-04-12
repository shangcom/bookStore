package com.green.bookstore.goods.controller;

import com.green.bookstore.common.base.BaseController;
import com.green.bookstore.goods.service.GoodsService;
import com.green.bookstore.goods.vo.GoodsVO;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller("goodsController")
@RequestMapping(value = "/goods")
public class GoodsControllerImpl extends BaseController implements GoodsController {

    @Autowired
    private GoodsService goodService;

    @RequestMapping(value = "/goodsDetail.do", method = RequestMethod.GET)
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

    /*사용자가 입력하는 키워드를 받아 해당 키워드를 포함하는 상품 제목 목록을 JSON 형태로 반환하는 메서드*/
    @RequestMapping(value = "/goods/keywordSearch.do", method = RequestMethod.GET, produces = "application/text; charset=utf-8")
    /*메소드가 처리할 HTTP 요청의 경로(/keywordSearch.do), 메소드(GET), 그리고 응답의 MIME 타입(application/text; charset=utf8)을 지정.
    클라이언트가 이 경로로 GET 요청을 보내면, 이 메소드가 호출되며, 응답은 UTF-8 인코딩의 텍스트 형태로 반환됨.*/
    public @ResponseBody String keywordSearch(@RequestParam("keyword") String keyword, HttpServletRequest request, HttpServletResponse response) throws Exception {
        /*@RequestParam("keyword") String keyword: HTTP 요청 파라미터 중 keyword라는 이름의 값을 찾아 메소드의 keyword 매개변수에 바인딩*/

        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        //@RequestMapping에서 이미 응답 타입을 지정했지만, 보다 명시적인 설정을 위해 이 코드가 추가함.

        if (keyword == null || keyword.equals("")) {
            return null;
        }

        keyword = keyword.toUpperCase(); //검색의 대소문자 구분을 없애기 위함
        List<String> keywordList = goodService.keywordSearch(keyword); //서비스 계층의 keywordSearch 메소드를 호출하여, 입력된 키워드를 포함하는 상품 제목 목록을 가져옴.

        JSONObject jsonObject = new JSONObject(); //JSON 객체를 생성. 이 객체는 최종적으로 클라이언트에 반환될 데이터를 담게 됨.
        jsonObject.put("keyword", keywordList); //JSON 객체에 keyword라는 키로 상품 제목 목록을 추가합니다.
        String jsonInfo = jsonObject.toString(); //JSON 객체를 문자열로 변환.

        return jsonInfo; //JSON 형태의 문자열을 클라이언트에 반환
    }
    /*페이지를 새로고침하지 않고도 서버로부터 관련 상품 정보를 받아와 실시간으로 검색 결과를 사용자에게 보여주어야 한다.
이러한 기능을 구현하기 위해 서버 측에서는 @ResponseBody 어노테이션을 활용한 REST API를 제공.
클라이언트(웹 페이지)는 AJAX 기술을 사용하여 "자바" 검색어와 함께 서버에 요청을 보냄.
서버는 이 요청을 받고, 데이터베이스에서 "자바"에 해당하는 상품 정보를 조회한 후, 이를 JSON 형태로 클라이언트에게 응답함.
이때, 클라이언트는 받은 JSON 데이터를 활용해 사용자에게 실시간으로 검색 결과를 보여줌.
이 과정에서 페이지 전체를 새로고침할 필요가 없으므로 사용자 경험이 개선됨*/


    /*searchGoods() 메서드 : 사용자가 입력한 검색어에 해당하는 상품 리스트를 조회하고, 이를 모델 데이터로 전달하여 지정된 뷰를 통해 사용자에게 보여주는 역할.*/
    @RequestMapping(value = "/searchGoods.do", method = RequestMethod.GET)
    public ModelAndView searchGoods(@RequestParam("searchWord") String searchWord, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String viewName = (String) request.getAttribute("viewName");
        /*요청 객체에서 viewName 속성을 가져와서 문자열 변수 viewName에 저장
        * 이 viewName은 응답으로 사용될 뷰의 이름을 나타냄. 이 값은 일반적으로 인터셉터나 필터를 통해 사전에 설정됨.*/
        List<GoodsVO> goodsList = goodService.searchGoods(searchWord); // searchGoods 메서드 사용해서 검색어에 해당하는 상품 조회하여 goodsVO 객체 리스트로 반환.
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("goodsList", goodsList);

        return mav; // 조회된 상품 리스트를 mav에 담아 전달.
    }

    private void addGoodsInQuick(String goods_id, GoodsVO goodsVO, HttpSession session) {
        boolean already_existed = false;
        List<GoodsVO> quickGoodsList;
        quickGoodsList = (ArrayList<GoodsVO>) session.getAttribute("quickGoodsList");
        // 세션에서 "quickGoodsList"를 가져옴


        if (quickGoodsList != null) { // 최근 본 상품 리스트에 상품이 있을 경우
            if (quickGoodsList.size() < 4) { // 4개 미만일 경우
                for (int i = 0; i < quickGoodsList.size(); i++) {
                    GoodsVO _goodsBean = (GoodsVO) quickGoodsList.get(i);
                    if (goods_id.equals(_goodsBean.getGoods_id())) { // 상품 ID(goods_id)가 목록에 있는 상품들의 ID와 일치하면, 이미 존재하는 것으로 간주함.
                        already_existed = true;
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

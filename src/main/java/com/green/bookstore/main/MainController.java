package com.green.bookstore.main;

import com.green.bookstore.common.base.BaseController;
import com.green.bookstore.goods.service.GoodsService;
import com.green.bookstore.goods.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

// MainController의 역할 : /main/main.do 경로로 들어오는 요청을 처리하고, 상품 목록을 뷰에 전달하여 사용자에게 상품 정보를 보여주는 역할.
@Controller("mainController") // 이 클래스의 인스턴스는 스프링 MVC에서 HTTP 요청을 처리하는 빈(Bean)으로 관리되며, 빈 이름은 "mainController".
@EnableAspectJAutoProxy //스프링 AOP(Aspect-Oriented Programming)를 활성화하고, 프록시 기반의 AOP를 사용하도록 설정.
// 즉, 이 컨트롤러 또는 이 컨트롤러가 사용하는 컴포넌트에서 AOP 기능을 사용할 수 있게함.
public class MainController extends BaseController {

    @Autowired
    private GoodsService goodsService;
    //GoodsService 타입의 객체를 자동으로 주입받음. MainController가 GoodsService의 메소드를 사용하여 상품 정보 등을 처리할 수 있게 해줌.

    @RequestMapping(value="/main/main.do", method={RequestMethod.POST, RequestMethod.GET})
/*POST와 GET 두 가지 메소드를 모두 처리할 수 있도록 설정. 사용자가 main.do 경로에 POST 요청을 보내거나, URL을 통해 직접 접근(GET 요청)하는 경우에도 이 메소드가 호출됨.*/
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession session;
        ModelAndView mav = new ModelAndView(); //객체는 컨트롤러가 처리 결과를 담아 뷰에 전달하는 데 사용
        String viewName = (String) request.getAttribute("viewName"); //요청 속성에서 뷰 이름을 가져옴. 특정 뷰를 동적으로 선택할 때 사용됨.
        mav.setViewName(viewName); //객체에 뷰 이름을 설정

        session = request.getSession(); //HTTP 세션을 가져옴.
        session.setAttribute("side_menu", "user");
        Map<String, List<GoodsVO>> goodsMap = goodsService.listGoods(); // GoodsService를 통해 상품 목록을 가져와 goodsMap에 저장
        mav.addObject("goodsMap", goodsMap); //ModelAndView 객체에 goodsMap을 추가. 뷰에서 상품 목록에 접근할 수 있게 됨.
        return mav; //설정된 ModelAndView 객체를 반환. 이 객체는 설정된 뷰 이름에 해당하는 뷰를 렌더링하고, goodsMap을 모델 데이터로 사용하여 최종적으로 클라이언트에 응답을 생성.
    }
}

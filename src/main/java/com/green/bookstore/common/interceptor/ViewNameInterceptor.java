package com.green.bookstore.common.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewNameInterceptor extends  HandlerInterceptorAdapter{

    /*preHandle() 메서드 : getViewName 메서드를 호출하여 현재 요청에 해당하는 뷰 이름을 결정하고, 이를 request 객체의 속성으로 저장
    * 이렇게 저장된 속성은 컨트롤러 내에서 사용
    * 반환값이 true이면 요청 처리가 계속 진행되고, false이면 처리가 중단됨. 여기서는 무조건 true를 반환하여 요청 처리가 계속되도록 했음.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        try {
            String viewName = getViewName(request);
            request.setAttribute("viewName", viewName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
        /*preHandle 메서드에서 반환하는 true 값은 스프링 MVC의 디스패처 서블릿(Dispatcher Servlet)에게 현재 요청을 처리해도 좋다는 신호를 줌.
        즉, 요청의 사전 처리가 성공적으로 완료되었으며, 요청을 해당 컨트롤러로 계속 전달할 수 있음을 의미.
        true를 반환하는 경우: 요청 처리 흐름이 계속 진행됨. 즉, preHandle 메서드 이후에 컨트롤러의 메서드가 호출되고, 그 결과에 따라 뷰가 렌더링됨.
        false를 반환하는 경우: 요청 처리 흐름이 중단됨. 컨트롤러의 메서드는 호출되지 않으며, 추가적인 요청 처리나 뷰 렌더링도 이루어지지 않음.
        이 경우, 보통 preHandle 메서드 내에서 사용자에게 오류 페이지를 보여주거나, 로그인 페이지로 리다이렉트하는 등의 처리를 수행함.
        */
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex)    throws  Exception {
    }

    /*getViewName() : 요청된 URI로부터 뷰 이름을 추출하는 로직.
    예를 들어, URI가 /app/goods/detail.do이고, 컨텍스트 패스가 /app이라면, 이 메서드는 /goods/detail을 뷰 이름으로 결정함.
    파일 확장자나 쿼리 스트링, 세미콜론 등은 뷰 이름에서 제외됨.
    추출된 뷰 이름은 컨트롤러에서 반환될 뷰의 이름과 일치해야함. */
    private String getViewName(HttpServletRequest request) throws Exception {
        String contextPath = request.getContextPath();
        String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
        if (uri == null || uri.trim().equals("")) {
            uri = request.getRequestURI();
        }

        int begin = 0;
        if (!((contextPath == null) || ("".equals(contextPath)))) {
            begin = contextPath.length();
        }

        int end;
        if (uri.indexOf(";") != -1) {
            end = uri.indexOf(";");
        } else if (uri.indexOf("?") != -1) {
            end = uri.indexOf("?");
        } else {
            end = uri.length();
        }

        String fileName = uri.substring(begin, end);
        if (fileName.indexOf(".") != -1) {
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        }
        if (fileName.lastIndexOf("/") != -1) {
            fileName = fileName.substring(fileName.lastIndexOf("/",1), fileName.length());
        }
        return fileName;
    }
}

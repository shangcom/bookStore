package com.green.bookstore.admin.order.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface AdminOrderController {
	public ModelAndView adminOrderMain(@RequestParam Map<String, String> dateMap,
									   HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ResponseEntity modifyDeliveryState(@RequestParam Map<String, String> deliveryMap,
											  HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ModelAndView orderDetail(@RequestParam("order_id") int order_id, 
            HttpServletRequest request, HttpServletResponse response)  throws Exception;
	
	
}

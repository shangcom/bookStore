package com.green.bookstore.admin.order.dao;

import java.util.ArrayList;
import java.util.Map;

import com.green.bookstore.member.vo.MemberVO;
import com.green.bookstore.order.vo.OrderVO;
import org.springframework.dao.DataAccessException;


public interface AdminOrderDAO {
	public ArrayList<OrderVO> selectNewOrderList(Map condMap) throws DataAccessException;
	public void  updateDeliveryState(Map deliveryMap) throws DataAccessException;
	public ArrayList<OrderVO> selectOrderDetail(int order_id) throws DataAccessException;
	public MemberVO selectOrderer(String member_id) throws DataAccessException;
}

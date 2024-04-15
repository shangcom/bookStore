package com.green.bookstore.mypage.dao;


import com.green.bookstore.member.vo.MemberVO;
import com.green.bookstore.order.vo.OrderVO;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface MyPageDAO {
	public List<OrderVO> selectMyOrderGoodsList(String member_id) throws DataAccessException;
	public List selectMyOrderInfo(String order_id) throws DataAccessException;
	public List<OrderVO> selectMyOrderHistoryList(Map dateMap) throws DataAccessException;
	public void updateMyInfo(Map memberMap) throws DataAccessException;
	public MemberVO selectMyDetailInfo(String member_id) throws DataAccessException;
	public void updateMyOrderCancel(String order_id) throws DataAccessException;
}

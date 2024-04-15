package com.green.bookstore.cart.dao;


import com.green.bookstore.cart.vo.CartVO;
import com.green.bookstore.goods.vo.GoodsVO;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface CartDAO {
	public List<CartVO> selectCartList(CartVO cartVO) throws DataAccessException;
	public List<GoodsVO> selectGoodsList(List<CartVO> cartList) throws DataAccessException;
	public boolean selectCountInCart(CartVO cartVO) throws DataAccessException;
	public void insertGoodsInCart(CartVO cartVO) throws DataAccessException;
	public void updateCartGoodsQty(CartVO cartVO) throws DataAccessException;
	public void deleteCartGoods(int cart_id) throws DataAccessException;
	
	

}

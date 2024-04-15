package com.green.bookstore.member.service;


import com.green.bookstore.member.vo.MemberVO;

import java.util.Map;

public interface MemberService {
	public MemberVO login(Map loginMap) throws Exception;
	public void addMember(MemberVO memberVO) throws Exception;
	public String overlapped(String id) throws Exception;
}

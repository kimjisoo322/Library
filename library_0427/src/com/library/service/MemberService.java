package com.library.service;

import java.util.List;

import com.library.dao.MemberDao;
import com.library.vo.Criteria;
import com.library.vo.Member;

public class MemberService {
	MemberDao dao = new MemberDao();
	
	/**
	 * 로그인
	 * @param id
	 * @param pw
	 * @return
	 */
	public Member login(String id, String pw) {
		Member member = null;
		
		member = dao.login(id, pw);
		if(member == null){
			System.err.println("아이디/비밀번호를 확인해주세요");
		} else {
			System.out.println(member.getName()+"님 환영합니다.");
		}
		return member;
	}

	public void insert (String id, String pw, String name, String adminYN) {
		
		// 아이디 중복체크
		boolean idCheck = dao.idCheck(id);
		if(idCheck) {
			
			Member member = 
					new Member(id, pw, name, adminYN, null, null);
			int res = dao.insert(member);
			if(res>0) {
				System.out.println(res+"건 입력되었습니다.");
			}else {
				System.out.println("입력중 오류가 발생 하였습니다.");
				System.out.println("관리자에게 문의해주세요.");
			}
		} else {
			System.err.println("아이디가 중복 되었습니다.");
		}
		
		
	}

	public void delete(String delId) {
		int res= dao.delete(delId);
		
		if(res>0) {
			System.out.println(res + "건 삭제 되었습니다.");
		} else {
			System.out.println("삭제중 오류가 발생 하였습니다.");
			System.out.println("관리자에게 문의해주세요.");
		}
	}

	public boolean idCheck(String id) {
		boolean res = dao.idCheck(id);
		if(!res) {
			System.out.println("아이디가 중복 되었습니다.");
		}
		return res;
	}
	
	
	// 회원 목록 조회 
	public List<Member> getList(){
		List<Member> list = dao.getList();
		for(Member member : list) {
			System.out.println(member);
		}
		
		return list;
	}
	// 회원 목록 조회 (검색조건 추가) + 페이징처리 
	public Object getListPageM(Criteria criteria) {
		List<Member> list = dao.getListPageM(criteria);
		for(Member member : list) {
			System.out.println(member);
		}
		return list;
	}
	
	// 총건수 조회 
	public int totalCntM(Criteria criteria) {
		int res = dao.totalCntM(criteria);
		if(res > 0) {
			System.out.println(res + "건 (멤버)");
		}else {
			System.out.println(res + "건 조회 불가능 (멤버)");
		}
		return res;
	}
}











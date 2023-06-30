package com.library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.common.ConnectionUtil;
import com.library.vo.Criteria;
import com.library.vo.Member;

public class MemberDao {
	/**
	 * 사용자 로그인
	 * @param id
	 * @param pw
	 * @return
	 */
	public Member login(String id, String pw) {
		Member member = null;
		
		String sql = 
				String.format("select id, name, adminyn, status, grade from LIBMEMBER "
				+ "where id='%s' and pw='%s'",id,pw);
		
		// 쿼리 출력
		System.out.println(sql);
		
		try (Connection conn = ConnectionUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
			// 질의결과 결과집합을 member객체에 담아줍니다
			if(rs.next()) {
				String name = rs.getString("name");
				
				 String adminYN = rs.getString("adminYN"); 
				 String status = rs.getString("status"); 
				 String grade = rs.getString("grade");
				 
				member = new Member(id, "", name, adminYN,status,grade);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return member;
	}
	
	public int insert(Member member) {
		int res = 0;
		String sql = String.format(
				"INSERT INTO LIBMEMBER (id, pw, name, adminYN)  VALUES "
				+ "('%s','%s','%s')"
				, member.getId(), member.getPw()
				, member.getName(), member.getAdminyn());
		
		System.out.println(sql);
		try (Connection conn = ConnectionUtil.getConnection();
				Statement stmt = conn.createStatement();){
			res = stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
		
	}
	
	/**
	 * 아이디 중복 체크
	 * 중복일경우 false리턴
	 * 
	 * @param id
	 * @return
	 */
	public boolean idCheck(String id) {
		boolean res = false;
		
		String sql = String.format(
				"select * from LIBMEMBER where id = '%s'",id);
		System.out.println(sql);
		try (Connection conn = ConnectionUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
			
			// rs.next = 결과집합이 있으면 true -> !rs.next를 반환
			return !rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return res;
	}
	
	public int delete(String id) {
		int res = 0;
		
		String sql = String.format
				("delete from LIBMEMBER where id = '%s'",id);
		
		System.out.println(sql);
		
		try (Connection conn = ConnectionUtil.getConnection();
				Statement stmt = conn.createStatement();){
			
			res = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	// 회원 목록
	public List<Member> getList(){
		List<Member> list = new ArrayList<Member>();
		String sql = "SELECT * FROM LIBMEMBER";
		
		try (
				Connection conn = ConnectionUtil.getConnection();
				Statement stmt = conn.createStatement();
				){
				ResultSet rs = 	stmt.executeQuery(sql);
				while(rs.next()) {
					String id = rs.getString("id");
					String pw = rs.getString("pw");
					String name = rs.getString("name");
					String adminyn = rs.getString("adminyn");
					String status = rs.getString("status");
					String grade = rs.getString("grade");
					
					Member member = new Member(id, pw, name, adminyn, status, grade);
					list.add(member);
				}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return list;
	}
	
	// 회원 목록 + 검색조건 + 페이징처리
	public List<Member> getListPageM(Criteria criteria){
		List<Member> list = new ArrayList<Member>();
		String sql = "SELECT * FROM ( SELECT ROWNUM RN, T.* FROM (SELECT LIBMEMBER.* FROM LIBMEMBER ";
		if (criteria.getSearchWorld() != null && !"".equals(criteria.getSearchWorld())) {
		    sql += " where " + criteria.getSearchField() + " Like '%" + criteria.getSearchWorld() + "%' ";
		};
	
		
		sql += "ORDER BY GRADE ASC" + " )T" + " ) " + " WHERE RN BETWEEN "  + criteria.getStartNo() + " AND " + criteria.getEndNo();
		
		try (
				Connection conn = ConnectionUtil.getConnection();
				Statement stmt = conn.createStatement();
				){
				ResultSet rs = 	stmt.executeQuery(sql);
				while(rs.next()) {
					String id = rs.getString("id");
					String pw = rs.getString("pw");
					String name = rs.getString("name");
					String adminyn = rs.getString("adminyn");
					String status = rs.getString("status");
					String grade = rs.getString("grade");
					
					Member member = new Member(id, pw, name, adminyn, status, grade);
					list.add(member);
				}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return list;
	}
	
	// 총 건수 조회 
	public int totalCntM(Criteria criteria) {
		int res = 0;
		String sql = "SELECT COUNT(*) FROM LIBMEMBER";
		if (criteria.getSearchWorld() != null && !"".equals(criteria.getSearchWorld())) {
		    sql += " where " + criteria.getSearchField() + " Like '%" + criteria.getSearchWorld() + "%' ";
		};

		System.out.println(sql + "멤버총건수");
		try (
				Connection conn = ConnectionUtil.getConnection();
				Statement stmt =  conn.createStatement();
				){
				ResultSet rs =	stmt.executeQuery(sql);
				if(rs.next()) {
					res = rs.getInt(1);
				}
				rs.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return res;
	}
}






























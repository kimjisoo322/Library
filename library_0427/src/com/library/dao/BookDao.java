package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.library.common.ConnectionUtil;
import com.library.common.DBConnectionPool;
import com.library.vo.Book;
import com.library.vo.Criteria;

public class BookDao {
	/**
	 * 도서목록 조회
	 * 
	 * @return ", nvl((select RENTYN from RENT where NO = no and RENTYN='Y'),'N')
	 *         rentyn,
	 */
	public List<Book> getList() {
		List<Book> list = new ArrayList<Book>();
		// String sql = "SELECT * FROM BOOK ORDER BY NO DESC";

		String sql = "select no, title, publisher, author " + "from book order by no DESC";
		/*
		 * String sql = "select no, title, publisher " + " , nvl((select RENTYN " +
		 * " from RENT " + " where NO =  " + " and RENTYN='Y'),'N') rentyn " +
		 * " , author " + " from book " + " order by no DESC ";
		 */
		System.out.println(sql);

		// try ( 리소스생성 ) => try문이 종료되면서 리소스를 자동으로 반납

		try (Connection conn = ConnectionUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				int no = rs.getInt("NO");
				String title = rs.getString("TITLE");
				String publisher = rs.getString("PUBLISHER");
				String rentYN = rs.getString("RENTYN");
				String author = rs.getString("AUTHOR");

				Book book = new Book(no, title, publisher, rentYN, author);
				list.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	// 페이징 처리 + 검색조건
	public List<Book> getListPage(Criteria criteria) {
		List<Book> list = new ArrayList<Book>();
		String sql = "SELECT * FROM (" + "SELECT ROWNUM RN, T.* FROM (" + "SELECT book.* FROM book ";
		if (criteria.getSearchWorld() != null && !"".equals(criteria.getSearchWorld())) {
			sql += "WHERE " + criteria.getSearchField() + " Like '%" + criteria.getSearchWorld() + "%' ";
		}
		sql += "ORDER BY no DESC" + "  )T" + " ) " + "WHERE RN BETWEEN " + criteria.getStartNo() + " AND "
				+ criteria.getEndNo();
		try (Connection conn = ConnectionUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				int no = rs.getInt("NO");
				String title = rs.getString("TITLE");
				String publisher = rs.getString("PUBLISHER");
				String rentYN = rs.getString("RENTYN");
				String author = rs.getString("AUTHOR");

				Book book = new Book(no, title, publisher, rentYN, author);
				list.add(book);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return list;
	}

	/**
	 * 도서 등록
	 * 
	 * @param book
	 * @return
	 */
	public int insert(Book book) {
		int res = 0;
		// INSERT INTO BOOK VALUES(SEQ_BOOK_NO.NEXTVAL,'제목','저자', '출판사', 'N', '0',
		// '원본파일명.txt', '저장된파일명.txt', 'R001');
		String sql = "INSERT INTO BOOK VALUES (SEQ_BOOK_NO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";

		// 실행될 쿼리를 출력해봅니다
		System.out.println("insert : " + sql);

		try (Connection conn = ConnectionUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, book.getTitle());
			pstmt.setString(2, book.getAuthor());
			pstmt.setString(3, book.getPublisher());
			pstmt.setString(4, book.getRentyn());
			pstmt.setString(5, book.getVisitcount());
			pstmt.setString(6, book.getOfile());
			pstmt.setString(7, book.getSfile());
			pstmt.setString(8, book.getRentno());

			res = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("도서등록 : 북다오 : " + res);
		return res;
	}

	/**
	 * 도서 삭제
	 * 
	 * @return
	 */
	public int delete(String no) {
		int res = 0;

		String sql = String.format("delete from book where no in (%s)", no);

		// 실행될 쿼리를 출력해봅니다
		// System.out.println(sql);
		try (Connection conn = ConnectionUtil.getConnection(); Statement stmt = conn.createStatement();) {
			res = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}

	// 총 건수 조회
	public int totalCount(Criteria criteria) {
		int res = 0;
		String sql = "select count(*) from book";
		if (criteria.getSearchWorld() != null && !"".equals(criteria.getSearchWorld())) {
			sql += " where " + criteria.getSearchField() + " Like '%" + criteria.getSearchWorld() + "%' ";
		}
		System.out.println(sql);
		try (Connection conn = ConnectionUtil.getConnection(); Statement stmt = conn.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				res = rs.getInt(1);
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return res;
	}

	// 도서 상세보기 ( 대여 처리 )
	public Book selectOne(String no, String rentno) {
		Book book = null;
		// 5개 ( 번호, 제목, 출판사, 대여여부, 저자 )
		String sql = "select " + " b.no, b.title, b.publisher, b.visitcount, d.rentyn, b.author, d.rentid"
				+ " , to_char(startdate,'yy/mm/dd') startdate" + " , to_char(enddate, 'yy/mm/dd') enddate"
				+ " , RETURNDATE, sfile, ofile, d.RENTNO " + " from book b, rent d "
				+ " where b.rentno = d.rentno(+) and b.no = ?";
		System.out.println(sql);
		try (Connection conn = ConnectionUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, no);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				book = new Book();
				book.setNo(rs.getInt("NO"));
				book.setTitle(rs.getString("TITLE"));
				book.setPublisher(rs.getString("PUBLISHER"));
				book.setRentyn(rs.getString("RENTYN"));
				book.setAuthor(rs.getString("AUTHOR"));
				book.setVisitcount(rs.getString("VISITCOUNT"));
				book.setOfile(rs.getString("ofile"));
				book.setSfile(rs.getString("sfile"));
				book.setRentno(rs.getString("rentno"));
				// rent 테이블
				book.setRentid(rs.getString("rentid"));
				book.setStartdate(rs.getString("startdate"));
				book.setEnddate(rs.getString("enddate"));
				book.setReturndate(rs.getString("returndate"));

			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return book;
	}

	// 조회수 1증가
	public int updateVisit(String no) {
		int res = 0;
		String sql = "UPDATE BOOK SET VISITCOUNT = VISITCOUNT+1 WHERE NO = ?";
		try (Connection conn = ConnectionUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, no);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return res;
	}

	// 도서 대여!!!
	/*
	 * 1) 대여 번호 조회 (R00001) => rs.next (첫번째 컬럼 읽어오기) 2) Book 테이블 업데이트 (rentyn = "Y",
	 * rentno =대여번호 / 대여여부 대여번호 ) 조건 : 도서번호, rentno가 null 또는 빈문자열 3) rent 테이블 insert
	 * (세션에서 받은 id값, 도서번호, 대여여부 = y , 반납일 null -> 반납시 해당 번호의 대여여부를 n (update)
	 */
	public int rentBook(Book book) {
		int res = 0;
		// 1) 대여 번호 조회
		String sql1 = "select 'R'||LPAD(SEQ_번호.NEXTVAL,5,0)FROM DUAL";

		// 2) book 테이블 update
		String sql2 = "UPDATE BOOK " + " SET RENTNO = ?, rentyn = 'Y' " + " WHERE NO = ? "
				+ " AND (RENTNO IS NULL OR RENTNO = '')";

		// 3) rent 테이블 insert 대여번호(rentno) / 대여아이디(rentid) / 도서번호(no) 값을 넣어줌
		String sql3 = "INSERT INTO RENT VALUES " + " ( ?, ?, ?, 'Y', SYSDATE, NULL, SYSDATE+14, NULL )";

		try (Connection conn = ConnectionUtil.getConnection();) {
			PreparedStatement pstmt = conn.prepareStatement(sql1);

			// 커넥션 자동커밋 x (한 쿼리 실행하고 닫아주고, 여러개가 모두 성공했을 때 커밋을 하는 의미)
			conn.setAutoCommit(false);
			ResultSet rs = pstmt.executeQuery(sql1);
			if (!rs.next()) {
				return res;
			}
			String rentno = rs.getString(1);
			System.out.println("rent no 대여번호: " + rentno);
			// 자원반납
			pstmt.close();

			// 2번째 쿼리문 Book 테이블 (update)
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, rentno);
			pstmt.setInt(2, book.getNo());

			res = pstmt.executeUpdate();
			System.out.println("sql2의 쿼리문 : " + sql2);
			System.out.println("res : " + res + "건 update");
			// 2번째 update를 통해 결과값이 1건이 있다면 3번째 쿼리문 insert를 실행함
			if (res > 0) {
				pstmt = conn.prepareStatement(sql3);
				pstmt.setString(1, rentno);
				pstmt.setString(2, book.getRentid());
				pstmt.setInt(3, book.getNo());
				res = pstmt.executeUpdate();

				System.out.println("sql3의 쿼리문 : " + sql3);
				System.out.println("res : " + res + "건 insert");
				if (res > 0) {
					conn.commit();
				} else {
					conn.rollback();
				}
			} else {
				conn.rollback();
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return res;
	}

	// 도서 반납
	public int returnBook(int no, String rentno) {
		int res = 0;
		String rentYN = "";
		// 1) rentYN 이 반납 가능한 도서인지 확인 ( SELECT RENTYN FROM BOOK WHERE NO = 133 )
		String sql1 = "select rentyn from book where no = ?";

		String sql2 = "UPDATE BOOK SET RENTYN = 'N', RENTNO = '' WHERE NO = ? ";
		
		String sql3 = "UPDATE RENT SET RENTYN = 'N', RETURNDATE = SYSDATE WHERE RENTNO = ? ";
		try (Connection conn = ConnectionUtil.getConnection();) {
			PreparedStatement pstmt = conn.prepareStatement(sql1);
			conn.setAutoCommit(false);
			
			pstmt.setInt(1, no);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				return res;
			}
			   rentYN = rs.getString("rentyn");
			   System.out.println("rentYN 상태: " + rentYN);
			   pstmt.close();
			   
			   // 2번째 쿼리문 
			   pstmt = conn.prepareStatement(sql2);
			   pstmt.setInt(1, no);
			   res = pstmt.executeUpdate();
			   System.out.println("sql2의 쿼리문(반납) : " + sql2);
			   System.out.println("res(반납) : " + res + "건 update");
			
			// 3) res 건이 1보다 크면 커밋, 롤백 / 3번째 쿼리문 실행
			if (res > 0) {
				pstmt = conn.prepareStatement(sql3);
				pstmt.setString(1, rentno);
				res = pstmt.executeUpdate();

				System.out.println("sql3의 쿼리문 : " + sql3);
				System.out.println("rentno : " + rentno + "건 update");
				System.out.println("res : " + res + "건 update");
				if(res > 0) {
					conn.commit();
				}else {
					conn.rollback();
				}
			}else {
				conn.rollback();
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return res;
	}
	/**
	 * 도서 업데이트
	 * 
	 * @return
	 *//*
		 * public int update(int no, String rentYN) { 
		 * 
		 *    int res = 0;
		 * 
		 *     String sql = String.format ("update book set rentYN = '%s' where no = %d",
		 * rentYN ,no);
		 * 
		 * // 실행될 쿼리를 출력해봅니다 
		 * //System.out.println(sql);
		 * 
		 * try (Connection conn = ConnectionUtil.getConnection(); 
		 *   Statement stmt = conn.createStatement(); ){ 
		 *   res = stmt.executeUpdate(sql); 
		 *   }catch(SQLException e) { e.printStackTrace(); 
		 *   } return res; 
		 *   }
		 * 
		 * public String getRentYN(int no) { String rentYN = ""; String sql =
		 * String.format( "SELECT RENTYN FROM BOOK WHERE NO = %s", no);
		 * 
		 * 
		 * try (Connection conn = ConnectionUtil.getConnection(); Statement stmt=
		 * conn.createStatement(); ResultSet rs = stmt.executeQuery(sql);){ // 조회된 행이
		 * 있는지 확인 if(rs.next()) { // DB에서 조회된 값을 변수에 저장 rentYN = rs.getString(1); }
		 * 
		 * } catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * return rentYN; }
		 */

}

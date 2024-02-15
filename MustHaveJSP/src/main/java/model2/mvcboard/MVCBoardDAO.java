package model2.mvcboard;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import common.DBConnPool;

public class MVCBoardDAO extends DBConnPool{
	public MVCBoardDAO() {}
	
	//검색 조건에 맞는 게시물의 개수를 반환
	public int selectCount(Map<String,Object> map) {
		int totalCount = 0;
		String query = "SELECT COUNT(*) FROM mvcboard";
		if(map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " " + " LIKE '%" + map.get("searchWord") + "%'";
		}
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
            rs.next();
            totalCount = rs.getInt(1);
		} catch (Exception e) {
			 System.out.println("게시물 카운트 중 예외 발생");
			e.printStackTrace();
		}
		
		return totalCount;
	}//selectCount() 메서드 종료
	
	// 검색 조건에 맞는 게시물 목록을 반환합니다(페이징 기능 지원).
	public List<MVCBoardDTO> selectListPage(Map<String,Object> map) {
		List<MVCBoardDTO> board = new Vector<MVCBoardDTO>();
		String query = " SELECT * FROM ( SELECT Tb.*, ROWNUM rNum FROM ( SELECT * FROM mvcboard ";
		if(map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " LIKE '%" + map.get("searchWord") + "%' ";
		}
		query += " ORDER BY idx DESC " + " ) Tb ) WHERE rNum BETWEEN ? AND ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, map.get("start").toString());
			pstmt.setString(2, map.get("end").toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MVCBoardDTO dto = new MVCBoardDTO();
				
				dto.setIdx(rs.getString(1));
			    dto.setName(rs.getString(2));
			    dto.setTitle(rs.getString(3));
			    dto.setContent(rs.getString(4));
			    dto.setPostdate(rs.getDate(5));
			    dto.setOfile(rs.getString(6));
			    dto.setSfile(rs.getString(7));
			    dto.setDowncount(rs.getInt(8));
			    dto.setPass(rs.getString(9));
			    dto.setVisitcount(rs.getInt(10));
			    
			    board.add(dto);
			}
		} catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		
		return board;
	}// selectListPage() 메서드 종료
	
	//게시글 데이터를 받아 DB에 추가합니다.(파일 업로드 지원)
	public int insertWrite(MVCBoardDTO dto) {
		int result = 0;
		try {
			String query = "INSERT INTO mvcboard (idx, name, title, content, ofile, sfile, pass) VALUES (seq_board_num.NEXTVAL,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(query);
			 pstmt.setString(1, dto.getName());
			 pstmt.setString(2, dto.getTitle());
			 pstmt.setString(3, dto.getContent());
			 pstmt.setString(4, dto.getOfile());
			 pstmt.setString(5, dto.getSfile());
			 pstmt.setString(6, dto.getPass());
			 result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("게시물 입력 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
	
	// 주어진 일련번호에 해당하는 게시물을 DTO에 담아 반환합니다.
	public MVCBoardDTO selectView(String idx) {
		MVCBoardDTO dto = new MVCBoardDTO(); //DTO객체 생성
		String query = "SELECT * FROM mvcboard WHERE idx=?"; //쿼리문 템플릿 준비
		try {
			pstmt = con.prepareStatement(query); //쿼리문 준비
			pstmt.setString(1, idx); //인파라미터 설정
			rs = pstmt.executeQuery(); //쿼리문 실행
			
			if(rs.next()) { //결과를 DTO 객체에 저장
				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
			    dto.setTitle(rs.getString(3));
			    dto.setContent(rs.getString(4));
			    dto.setPostdate(rs.getDate(5));
			    dto.setOfile(rs.getString(6));
			    dto.setSfile(rs.getString(7));
			    dto.setDowncount(rs.getInt(8));
			    dto.setPass(rs.getString(9));
			    dto.setVisitcount(rs.getInt(10));
			  }
		} catch (Exception e) {
			System.out.println("게시물 상세보기 중 예외 발생");
			e.printStackTrace();
		}
		return dto; //결과 반환
	}
	
	//주어진 일련번호에 해당하는 게시물의 조회수를 1 증가시킵니다.
	public void updateVisitCount(String idx) {
		String query = "UPDATE mvcboard SET visitcount=visitcount + 1 WHERE idx=?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, idx);
			pstmt.executeQuery();
		} catch (Exception e) {
			System.out.println("게시물 조회수 증가 중 예외발생");
			e.printStackTrace();
		}
	}
	
	//다운로드 횟수를 1증가 시킵니다.
	public void downCountPlus(String idx) {
		String sql = "UPDATE mvcboard SET downCount=downcount + 1 WHERE idx=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, idx);
			pstmt.executeQuery();
		} catch (Exception e) {}
	}

	//입력한 비밀번호가 지정한 일련번호의 게시물의 비밀번호와 일치하는지 확인
	public boolean confirmPassword(String pass, String idx) {
		boolean isCorr = true;
		try {
			String sql = "SELECT COUNT(*) FROM mvcboard WHERE pass? AND idx=?";
			 pstmt = con.prepareStatement(sql);
	            pstmt.setString(1, pass);
	            pstmt.setString(2, idx);
	            rs = pstmt.executeQuery();
	            rs.next();
	            if (rs.getInt(1) == 0) {
	                isCorr = false;
	            }
		} catch (Exception e) {
			isCorr = false;
			e.printStackTrace();
		}
		return isCorr;
	}
	
	//지정한 일련번호의 게시물을 삭제
	public int deletePost(String idx) {
		int result = 0;
		try {
			String query = "DELETE FROM mvcboard WHERE idx=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, idx);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("게시물 삭제 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
        
	//게시글 데이터를 받아 DB에 저장되어 있던 내용을 갱신한다. (파일 업로드 지원)
	public int updatePost(MVCBoardDTO dto) {
		int result = 0;
		try {
			//쿼리문 템플릿 준비
			String query = "UPDATE mvcboard SET title=?, name=?, content=?, ofile=?, sfile=? WHERE idx=? and pass=?";
			
			// 쿼리문 준비
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getOfile());
			pstmt.setString(5, dto.getSfile());
			pstmt.setString(6, dto.getIdx());
			pstmt.setString(7, dto.getPass());

			// 쿼리문 실행
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("게시물 수정 중 예외 발생");
			e.printStackTrace();
		}
	return result;			
	}
				

}

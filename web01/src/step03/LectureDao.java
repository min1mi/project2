/* 역할:
 * => memb 테이블의 데이터를 다루는 메서드를 모아둔 클래스이다.
 * => 출력하는 역할은 호출자에게 맡긴다.
 * => DAO의 역할은 결과를 리턴하면 된다.
 * => 커넥션은 DBConnectionPool로부터 얻어서 사용하고,
 *    사용한 후에는 반납한다.
 * => DAO는 커넥션을 관리하지 않는다.
 */
package step03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LectureDao {
  DBConnectionPool conPool;

  public LectureDao(DBConnectionPool conPool) {
    this.conPool = conPool;
  }

  public List<Lecture> selectList(int pageNo, int pageSize) throws Exception {
    // 사용할 커넥션을 DBConnectionPool로부터 빌린다.
    Connection con = conPool.getConnection();

    try (
        PreparedStatement stmt = con.prepareStatement(
            "select lno, titl, date_format(sdt,'%Y-%m-%d') sdt ,"
            + " date_format(edt,'%Y-%m-%d') edt, thrs, pric"
            + " from lect order by sdt desc limit ?, ?");) {

      stmt.setInt(1, (pageNo - 1) * pageSize/* 시작 인덱스 */);
      stmt.setInt(2, pageSize/* 꺼낼 레코드 수 */);

      ArrayList<Lecture> list = new ArrayList<>();
      try (ResultSet rs = stmt.executeQuery();) {
        Lecture lecture = null;
        while (rs.next()) {
          lecture = new Lecture();
          lecture.setLno(rs.getInt("lno"));
          lecture.setPrice(rs.getInt("pric"));
          lecture.setTotalHours(rs.getInt("thrs"));
          lecture.setTitle(rs.getString("titl"));
          lecture.setStartDate(rs.getString("sdt"));
          lecture.setEndDate(rs.getString("edt"));

          list.add(lecture);
        }
      }
      return list;

    } finally { // 다 쓴 커넥션을 반납하기 위해서
      // finally 블록은 try 블록을 벗어나기 전에 반드시 실행되는 블록이다.
      // try 블록에서 return 문을 실행하기 전에 이 블록을 실행한다.
      conPool.returnConnection(con);
    }
  }

  public Lecture selectOne(int no) throws Exception {
    Connection con = conPool.getConnection();

    try (
        PreparedStatement stmt = con.prepareStatement(
            "select lno, dscp, date_format(sdt, '%Y-%m-%d') sdt,"
            + " date_format(edt, '%Y-%m-%d') edt, qty, pric, thrs,"
            + " titl, crmno, mrno from lect where lno=?");) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery();) {
        if (!rs.next()) {
          return null;
        }

        Lecture lecture = new Lecture();
        lecture = new Lecture();
        lecture.setLno(rs.getInt("lno"));
        lecture.setCrmno(rs.getInt("crmno"));
        lecture.setMrno(rs.getInt("mrno"));
        lecture.setPrice(rs.getInt("pric"));
        lecture.setQty(rs.getInt("qty"));
        lecture.setTotalHours(rs.getInt("thrs"));
        lecture.setTitle(rs.getString("titl"));
        lecture.setDscp(rs.getString("dscp"));
        lecture.setStartDate(rs.getString("sdt"));
        lecture.setEndDate(rs.getString("edt"));

        return lecture;
      }

    } finally { // 다 쓴 커넥션을 반납하기 위해서
      // finally 블록은 try 블록을 벗어나기 전에 반드시 실행되는 블록이다.
      // try 블록에서 return 문을 실행하기 전에 이 블록을 실행한다.
      conPool.returnConnection(con);
    }
  }

  public int insert(Lecture lecture) throws Exception {
    Connection con = conPool.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement(
            "insert into lect(dscp, sdt, edt, qty, pric, thrs, titl, crmno, mrno) "
            + "values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
        ) {
      stmt.setString(1, lecture.getDscp());
      stmt.setString(2, lecture.getStartDate());
      stmt.setString(3, lecture.getEndDate());
      stmt.setInt(4, lecture.getQty());
      stmt.setInt(5, lecture.getPrice());
      stmt.setInt(6, lecture.getTotalHours());
      stmt.setString(7, lecture.getTitle());
      stmt.setInt(8, lecture.getCrmno());
      stmt.setInt(9, lecture.getMrno());
      
      
      
      return stmt.executeUpdate();
    }finally {
      conPool.returnConnection(con);
    }
  }

  public int delete(int no) throws Exception {
    Connection con = conPool.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement(
            "delete from lect where lno=?");
        ) {

      stmt.setInt(1, no);
      return stmt.executeUpdate();

    } finally {
      conPool.returnConnection(con);
    }
  }

  public int update(Lecture lecture) throws Exception {
    Connection con = conPool.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement(
            "update lect set dscp=?, sdt=?, edt=?, qty=?, pric=?, thrs=?, titl=?, crmno=?, mrno=? "
            + "where lno=?");
        ) {
      stmt.setString(1, lecture.getDscp());
      stmt.setString(2, lecture.getStartDate());
      stmt.setString(3, lecture.getEndDate());
      stmt.setInt(4, lecture.getQty());
      stmt.setInt(5, lecture.getPrice());
      stmt.setInt(6, lecture.getTotalHours());
      stmt.setString(7, lecture.getTitle());
      stmt.setInt(8, lecture.getCrmno());
      stmt.setInt(9, lecture.getMrno());
      
      return stmt.executeUpdate();
    } finally {
      conPool.returnConnection(con);
    }
  }
}














//
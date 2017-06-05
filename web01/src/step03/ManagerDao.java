package step03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ManagerDao {
  DBConnectionPool conPool;

  public ManagerDao(DBConnectionPool conPool) {
    this.conPool = conPool;
  }

  public List<Manager> selectList() throws Exception {
    // 사용할 커넥션을 DBConnectionPool로부터 빌린다.
    Connection con = conPool.getConnection();

    try (
        PreparedStatement stmt = con.prepareStatement(
            "select mr.mrno, m.name"
            + " from mgr mr inner join memb m on mr.mrno=m.mno"
            + " order by m.name asc");) {


      ArrayList<Manager> list = new ArrayList<>();
      try (ResultSet rs = stmt.executeQuery();) {
        Manager manager = null;
        while (rs.next()) {
          manager = new Manager();
          manager.setNo(rs.getInt("mrno"));
          manager.setName(rs.getString("name"));

          list.add(manager);
        }
      }
      return list;

    } finally { // 다 쓴 커넥션을 반납하기 위해서
      // finally 블록은 try 블록을 벗어나기 전에 반드시 실행되는 블록이다.
      // try 블록에서 return 문을 실행하기 전에 이 블록을 실행한다.
      conPool.returnConnection(con);
    }
  }

  public Manager selectOne(int no) throws Exception {
    Connection con = conPool.getConnection();

    try (
        PreparedStatement stmt = con.prepareStatement(
            "select mr.mrno, m.name"
            + " from mgr mr inner join memb m on mr.mrno=m.mno"
            + " order by m.name asc");) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery();) {
        if (!rs.next()) {
          return null;
        }

        Manager manager = new Manager();
        manager.setNo(rs.getInt("mrno"));
        manager.setName(rs.getString("name"));
        return manager;
      }

    } finally { // 다 쓴 커넥션을 반납하기 위해서
      // finally 블록은 try 블록을 벗어나기 전에 반드시 실행되는 블록이다.
      // try 블록에서 return 문을 실행하기 전에 이 블록을 실행한다.
      conPool.returnConnection(con);
    }
  }

  public int insert(Member member) throws Exception {
    Connection con = conPool.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement(
            "insert into memb(name, tel, email, pwd) value(?,?,?, password(?))");
        ) {
      stmt.setString(1, member.getName());
      stmt.setString(2, member.getTel());
      stmt.setString(3, member.getEmail());
      stmt.setString(4, member.getPassword());
      return stmt.executeUpdate();
    }finally {
      conPool.returnConnection(con);
    }
  }

  public int delete(int no) throws Exception {
    Connection con = conPool.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement(
            "delete from memb where mno=?");
        ) {

      stmt.setInt(1, no);
      return stmt.executeUpdate();

    } finally {
      conPool.returnConnection(con);
    }
  }

  public int update(Member member) throws Exception {
    Connection con = conPool.getConnection();
    try (
        PreparedStatement stmt = con.prepareStatement(
            "update memb set name=?, tel=?, email=?, pwd=password(?) where mno=?");
        ) {
      stmt.setString(1, member.getName());
      stmt.setString(2, member.getTel());
      stmt.setString(3, member.getEmail());
      stmt.setString(4, member.getPassword());
      stmt.setInt(5, member.getNo());
      return stmt.executeUpdate();
    } finally {
      conPool.returnConnection(con);
    }
  }
}

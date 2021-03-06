/* 회원관리 만들기 : 회원삭제하기
 * => MemberDao를 이용하여 클라이언트로부터 받은 회원 정보를 삭제한다.
 */
package step03;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns="/step03/LectureDel")
public class LectureDel extends GenericServlet {
  private static final long serialVersionUID = 1L;

  @Override
  public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    res.setContentType("text/html;charset=UTF-8"); // 위에 정의해주기때문에 아래 <meta> 는 안해줘도 된다.
    PrintWriter out = res.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("  <meta charset='UTF-8'>");
    out.println("  <title>게시판 관리</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시글 삭제</h1>");

    String jdbcDriver = "com.mysql.jdbc.Driver";
    String jdbcUrl = "jdbc:mysql://localhost:3306/webappdb";
    String jdbcUsername = "webapp";
    String jdbcPassword = "1111";

    // DB 커넥션을 관리할 객체를 만든다.
    try {
      DBConnectionPool conPool = new DBConnectionPool(jdbcDriver, jdbcUrl, jdbcUsername, jdbcPassword);

      // DAO에 DB 커넥션 풀을 전달한다.
      LectureDao lectureDao = new LectureDao(conPool);

      int no = Integer.parseInt(req.getParameter("no"));
      int count = lectureDao.delete(no);
      if (count < 1) {
        throw new Exception(no + "번 게시글을 찾을 수 없습니다.");
      }
      out.println("<p>삭제 성공입니다.</p>");
      
    } catch (Exception e) {
      out.println("오류 발생");
      out.println("<pre>");
      e.printStackTrace(out);
      out.println("</pre>");
    }

    out.println("<a href='LectureList'>목록</a>");
    out.println("</body>");
    out.println("</html>");
  }
}












//

/* 회원관리 만들기 : 회원등록하기
 * => MemberDao를 이용하여 클라이언트로부터 받은 회원 정보를 저장한다.
 */
package step03;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns="/step03/LectureInsert")
public class LectureInsert extends GenericServlet {
  private static final long serialVersionUID = 1L;

  @Override
  public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    Lecture l = new Lecture();
    
    l.setTitle(req.getParameter("titl"));
    l.setDscp(req.getParameter("dscp"));
    l.setStartDate(req.getParameter("sdt"));
    l.setEndDate(req.getParameter("edt"));
    l.setQty(Integer.parseInt(req.getParameter("qty")));
    l.setPrice(Integer.parseInt(req.getParameter("pric")));
    l.setTotalHours(Integer.parseInt(req.getParameter("thrs")));
    
    // 이렇게 출력 스트림을 얻기 전에 먼저 호출해야 한다.
    res.setContentType("text/html;charset=UTF-8"); // 위에 정의해주기때문에 아래 <meta> 는 안해줘도 된다.
    PrintWriter out = res.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("  <meta charset='UTF-8'>");
    out.println("  <title>게시판 관리</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시글 등록</h1>");

    String jdbcDriver = "com.mysql.jdbc.Driver";
    String jdbcUrl = "jdbc:mysql://localhost:3306/webappdb";
    String jdbcUsername = "webapp";
    String jdbcPassword = "1111";

    // DB 커넥션을 관리할 객체를 만든다.
    try {
      DBConnectionPool conPool = new DBConnectionPool(jdbcDriver, jdbcUrl, jdbcUsername, jdbcPassword);

      // DAO에 DB 커넥션 풀을 전달한다.
      LectureDao lectureDao = new LectureDao(conPool);

      lectureDao.insert(l);
      out.println("<p>등록 성공입니다.</p>");
      
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

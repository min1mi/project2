/* 게시판 List 출력
 */
package step03;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns="/step03/LectureList")
public class LectureList extends GenericServlet {
  private static final long serialVersionUID = 1L;

  @Override
  public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    /* 페이지 번호와 페이지당 출력 개수 파라미터 받기 */
    int pageNo = 1;
    int pageSize = 5;

    try { // pageNo 파라미터 값이 있다면 그 값으로 설정한다.
      pageNo = Integer.parseInt(req.getParameter("pageNo"));
    } catch (Exception e) {}

    try { // pageSize 파라미터 값이 있다면 그 값으로 설정한다.
      pageSize = Integer.parseInt(req.getParameter("pageSize"));
    } catch (Exception e) {}

    // 이렇게 출력 스트림을 얻기 전에 먼저 호출해야 한다.
    res.setContentType("text/html;charset=UTF-8"); // 위에 정의해주기때문에 아래 <meta> 는 안해줘도 된다.
    PrintWriter out = res.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("  <meta charset='UTF-8'>");
    out.println("  <title>강의 관리</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>강의 목록</h1>");

    String jdbcDriver = "com.mysql.jdbc.Driver";
    String jdbcUrl = "jdbc:mysql://localhost:3306/webappdb";
    String jdbcUsername = "webapp";
    String jdbcPassword = "1111";

    // DB 커넥션을 관리할 객체를 만든다.
    try {
      DBConnectionPool conPool = new DBConnectionPool(jdbcDriver, jdbcUrl, jdbcUsername, jdbcPassword);

      // DAO에 DB 커넥션 풀을 전달한다.
      LectureDao lectureDao = new LectureDao(conPool);

      List<Lecture> list = lectureDao.selectList(pageNo, pageSize);

      out.println("<a href='lecture.html'>새 강의</a>");

      out.println("<table border='1'>");
      out.println("<thead>");
      out.println("  <tr><th>번호</th><th>제목</th><th>시작일</th><th>종료일</th><th>강의시간</th><th>수업료</th></tr>");
      out.println("</thead>");
      out.println("<tbody>");
      for (Lecture l : list) {
        out.println("<tr>");
        out.printf("  <td>%d</td>\n", l.getLno());
        out.printf("  <td><a href='LectureView?no=%d'>%s</a></td>\n", l.getLno(), l.getTitle());
        out.printf("  <td>%s</td>\n", l.getStartDate());
        out.printf("  <td>%s</td>\n", l.getEndDate());
        out.printf("  <td>%s</td>\n", l.getTotalHours());
        out.printf("  <td>%s</td>\n", l.getPrice());
        out.println("</tr>");
      }
      out.println("</tbody>");
      out.println("</table>");
    } catch (Exception e) {
      out.println("오류 발생");
      out.println("<pre>");
      e.printStackTrace(out);
      out.println("</pre>");
    }

    out.println("</body>");
    out.println("</html>");
  }
}












//

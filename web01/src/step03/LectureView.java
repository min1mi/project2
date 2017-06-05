/* 게시글 조회
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

@WebServlet(urlPatterns="/step03/LectureView")
public class LectureView extends GenericServlet {
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
    out.println("<h1>게시글 조회</h1>");

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

      Lecture lecture = lectureDao.selectOne(no);
      if (lecture == null) {
        throw new Exception(no + "번 게시글이 없습니다.");
      }
      
      ClassroomDao classroomDao = new ClassroomDao(conPool);
      List<Classroom> classroomList = classroomDao.selectList();

      ManagerDao managerDao = new ManagerDao(conPool);
      List<Manager> managerList = managerDao.selectList();


      out.println("<form action='Servlet05' method='POST'>\n");
      System.out.println(lecture.getLno());
      out.printf("번호: <input type='text' name='lno' value='%d' readonly><br>\n", lecture.getLno());
      out.printf("강의명: <input type='text' name='title' value='%s'><br>\n", lecture.getTitle());
      out.printf("설명: <input type='text' name='dscp' value='%s'><br>\n", lecture.getDscp());
      out.printf("시작일: <input type='text' name='sdt' value='%s'><br>\n", lecture.getStartDate());
      out.printf("종료일: <input type='text' name='edt' value='%s'><br>\n", lecture.getEndDate());
      out.printf("수강가능인원: <input type='test' name='qty' value='%d'><br>\n", lecture.getQty());
      out.printf("수업료: <input type='text' name='price' value='%s'><br>\n", lecture.getPrice());
      out.printf("총시간: <input type='text' name='thrs' value='%s'><br>\n", lecture.getTotalHours());
      out.println("강의실: <select name='classroom'><option value='0'>강의실을 선택하세요</option>");
      for (Classroom c : classroomList) {
        out.printf("<option value='%s'>%s</option>\n", c.getNo(), c.getName());
      }
      out.println("</select><br>");
      out.println("매니저: <select name='manager'><option value='0'>매니저를 선택하세요</option><br>");
      for (Manager m : managerList) {
        out.printf("<option value='%d'>%s</option>\n", m.getNo(), m.getName());
      }
      out.println("</select><br>");
      out.println("<button>변경</button>");
      out.println("<button type='button' onclick='doDelete()'>삭제</button>");
      out.println("<button type='button' onclick='doList()'>목록</button>");
      out.println("</form>");


    } catch (Exception e) {
      out.println("오류 발생");
      out.println("<pre>");
      e.printStackTrace(out);
      out.println("</pre>");
      out.println("<a href='LectureList'>목록</a>");
    }

    out.println("<script>");
    out.println("function doDelete() {");
    out.printf("  location.href='LectureDel?no=%s'\n", req.getParameter("no"));
    out.println("}");

    out.println("function doList() {");
    out.println("  location.href='LectureList'");
    out.println("}");
    out.println("</script>");

    out.println("</body>");
    out.println("</html>");
  }
}












//

package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import dao.StudentDao;

@WebServlet("/studentinsert")
public class StudentInsert extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            // フォームからデータ取得
            String no = request.getParameter("no");
            String name = request.getParameter("name");
            int entYear = Integer.parseInt(request.getParameter("ent_year"));
            String classNum = request.getParameter("class_no");
            boolean isAttend = request.getParameter("is_attend") != null;

            // 学生オブジェクト作成
            Student student = new Student();
            student.setNo(no);
            student.setName(name);
            student.setEntYear(entYear);
            student.setClassNum(classNum);
            student.setIsAttend(isAttend);

            // DAOでDB登録
            StudentDao studentDao = new StudentDao();
            studentDao.insert(student);

            // 一覧にリダイレクト
            response.sendRedirect("studentlist");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "登録中にエラーが発生しました");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}

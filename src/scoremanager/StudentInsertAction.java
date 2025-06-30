package scoremanager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentInsertAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        if (teacher == null) {
            return "../login.jsp";  // ログインしていなければログイン画面へ
        }

        String schoolCd = teacher.getSchool_cd();
        StudentDao dao = new StudentDao();

        // ▼ 入学年度・クラスの一覧を取得してセット（JSPで選択肢に使う）
        List<Integer> entYears = dao.getEntYears(schoolCd);
        List<String> classNums = dao.getClassNums(schoolCd);
        request.setAttribute("entYears", entYears);
        request.setAttribute("classNums", classNums);

        // ▼ 登録処理：POST の場合だけ実行（method 判定）
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            try {
                String no = request.getParameter("no");
                String name = request.getParameter("name");
                int entYear = Integer.parseInt(request.getParameter("ent_year"));
                String classNum = request.getParameter("class_no");
                boolean isAttend = request.getParameter("is_attend") != null;

                Student student = new Student();
                student.setNo(no);
                student.setName(name);
                student.setEntYear(entYear);
                student.setClassNum(classNum);
                student.setIsAttend(isAttend);
                student.setSchoolcd(schoolCd); // メソッド名注意

                dao.insert(student);

                return "scoremanager/StudentList.action";  // 一覧画面に遷移

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "登録中にエラーが発生しました");
                return "/error.jsp";
            }
        }

        // ▼ GET（最初の画面表示）時は studentinsert.jsp にフォワード
        return "/disp/studentinsert.jsp";
    }
}

package scoremanager;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentListAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        String entYear = request.getParameter("ent_year");
        String classNo = request.getParameter("class_no");
        String isAttend = request.getParameter("is_attend");

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        if (teacher == null) {
            return "../login.jsp";
        }

        String schoolCd = teacher.getSchool_cd();


        StudentDao dao = new StudentDao();

        List<Integer> entYears = dao.getEntYears(schoolCd);
        List<String> classNums = dao.getClassNums(schoolCd);
        List<Map<String, Object>> students = dao.filterStudents(schoolCd, entYear, classNo, isAttend);

        // デバッグ出力（サーバーコンソール）
        System.out.println("---- entYears ----");
        for (Integer y : entYears) System.out.println("入学年度: " + y);
        System.out.println("→ 件数: " + entYears.size());

        System.out.println("---- classNums ----");
        for (String c : classNums) System.out.println("クラス: " + c);
        System.out.println("→ 件数: " + classNums.size());

        System.out.println("---- students ----");
        for (Map<String, Object> s : students) {
            System.out.println("学生番号: " + s.get("no") + ", 氏名: " + s.get("name"));
        }
        System.out.println("→ 件数: " + students.size());


        request.setAttribute("entYears", entYears);
        request.setAttribute("classNums", classNums);
        request.setAttribute("students", students);

        return "/disp/student_list.jsp";
    }
}

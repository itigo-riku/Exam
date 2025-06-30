package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;

public class StudentDao extends DAO {

    // 入学年度一覧を取得
    public List<Integer> getEntYears(String schoolCd) throws Exception {
        List<Integer> entYears = new ArrayList<>();
        Connection con = getConnection();

        String sql = "SELECT DISTINCT ent_year FROM STUDENT WHERE school_cd = ? ORDER BY ent_year";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, schoolCd);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            entYears.add(rs.getInt("ent_year"));
        }
        rs.close();
        st.close();
        con.close();
        return entYears;
    }

    // クラス番号一覧を取得
    public List<String> getClassNums(String schoolCd) throws Exception {
        List<String> classNums = new ArrayList<>();
        Connection con = getConnection();

        String sql = "SELECT DISTINCT class_num FROM STUDENT WHERE school_cd = ? ORDER BY class_num";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, schoolCd);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            classNums.add(rs.getString("class_num"));
        }
        rs.close();
        st.close();
        con.close();
        return classNums;
    }

    // 学生情報を条件で取得
    public List<java.util.Map<String, Object>> filterStudents(String schoolCd, String entYear, String classNo, String isAttend) throws Exception {
        List<java.util.Map<String, Object>> students = new ArrayList<>();
        Connection con = getConnection();

        StringBuilder sql = new StringBuilder("SELECT * FROM STUDENT WHERE school_cd = ?");
        if (entYear != null && !entYear.isEmpty()) {
            sql.append(" AND ent_year = ?");
        }
        if (classNo != null && !classNo.isEmpty()) {
            sql.append(" AND class_num = ?");
        }
        if ("1".equals(isAttend)) {
            sql.append(" AND is_attend = TRUE");
        }

        PreparedStatement st = con.prepareStatement(sql.toString());

        int index = 1;
        st.setString(index++, schoolCd);
        if (entYear != null && !entYear.isEmpty()) {
            st.setInt(index++, Integer.parseInt(entYear));
        }
        if (classNo != null && !classNo.isEmpty()) {
            st.setString(index++, classNo);
        }

        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            java.util.Map<String, Object> student = new java.util.HashMap<>();
            student.put("no", rs.getString("no"));
            student.put("name", rs.getString("name"));
            student.put("ent_year", rs.getInt("ent_year"));
            student.put("class_num", rs.getString("class_num"));
            student.put("is_attend", rs.getBoolean("is_attend"));
            students.add(student);
        }

        rs.close();
        st.close();
        con.close();
        return students;
    }

    public void insert(Student student) throws Exception {
        Connection con = getConnection();

        String sql = "INSERT INTO STUDENT (no, name, ent_year, class_num, is_attend, school_cd) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, student.getNo());
        st.setString(2, student.getName());
        st.setInt(3, student.getEntYear());
        st.setString(4, student.getClassNum());
        st.setBoolean(5, student.isIsAttend());  // booleanのgetter名に注意
        st.setString(6, student.getSchoolcd());

        st.executeUpdate();

        st.close();
        con.close();
    }

}

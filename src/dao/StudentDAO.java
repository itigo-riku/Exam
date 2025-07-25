package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;

public class StudentDAO extends DAO {

    // 学生リストをフィルターして取得
    public List<Student> filter(String f1, String f2, String f3, String schoolCd) throws Exception {
        List<Student> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM student WHERE school_cd = ?");
        List<Object> params = new ArrayList<>();
        params.add(schoolCd);

        if (f1 != null && !f1.equals("0")) {
            sql.append(" AND ent_year = ?");
            params.add(Integer.parseInt(f1));
        }
        if (f2 != null && !f2.equals("0")) {
            sql.append(" AND class_num = ?");
            params.add(Integer.parseInt(f2));
        }
        if (f3 != null && f3.equals("t")) {
            sql.append(" AND is_attend = true");
        }

        sql.append(" ORDER BY ent_year, class_num, no");

        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Student s = new Student();
                s.setNo(rs.getInt("no"));
                s.setName(rs.getString("name"));
                s.setEntYear(rs.getInt("ent_year"));
                s.setClassNum(rs.getInt("class_num"));
                s.setAttend(rs.getBoolean("is_attend"));
                s.setSchoolCd(rs.getString("school_cd"));
                list.add(s);
            }
        }

        return list;
    }

    // 入学年度一覧
    public List<Integer> getEntYears(String schoolCd) throws Exception {
        List<Integer> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(
                 "SELECT DISTINCT ent_year FROM student WHERE school_cd = ? ORDER BY ent_year")) {
            st.setString(1, schoolCd);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("ent_year"));
                }
            }
        }
        return list;
    }

    // クラス番号一覧
    public List<Integer> getClassNums(String schoolCd) throws Exception {
        List<Integer> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(
                 "SELECT DISTINCT class_num FROM student WHERE school_cd = ? ORDER BY class_num")) {
            st.setString(1, schoolCd);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("class_num"));
                }
            }
        }
        return list;
    }

    // 全学生取得
    public List<Student> findAll(String schoolCd) throws Exception {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student WHERE school_cd = ? ORDER BY ent_year, class_num, no";

        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, schoolCd);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Student s = new Student();
                    s.setNo(rs.getInt("no"));
                    s.setName(rs.getString("name"));
                    s.setEntYear(rs.getInt("ent_year"));
                    s.setClassNum(rs.getInt("class_num"));
                    s.setAttend(rs.getBoolean("is_attend"));
                    s.setSchoolCd(rs.getString("school_cd"));
                    list.add(s);
                }
            }
        }
        return list;
    }

    // 学生追加
    public boolean insert(Student s) throws Exception {
        String sql = "INSERT INTO student (no, name, ent_year, class_num, is_attend, school_cd) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, s.getNo());
            st.setString(2, s.getName());
            st.setInt(3, s.getEntYear());
            st.setInt(4, s.getClassNum());
            st.setBoolean(5, s.isAttend());
            st.setString(6, s.getSchoolCd());

            int result = st.executeUpdate();
            return result == 1;
        }
    }

    // 学生存在確認
    public boolean exists(int no, String schoolCd) throws Exception {
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(
                 "SELECT COUNT(*) FROM student WHERE no = ? AND school_cd = ?")) {
            st.setInt(1, no);
            st.setString(2, schoolCd);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    // 学生更新
    public boolean update(Student s) throws Exception {
        String sql = "UPDATE student SET name = ?, class_num = ?, is_attend = ? WHERE no = ? AND school_cd = ?";

        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, s.getName());
            st.setInt(2, s.getClassNum());
            st.setBoolean(3, s.isAttend());
            st.setInt(4, s.getNo());
            st.setString(5, s.getSchoolCd());

            int result = st.executeUpdate();
            return result == 1;
        }
    }

    // ★ 成績参照：条件検索用に追加
    public Student findByNo(int no, String schoolCd) throws Exception {
        String sql = "SELECT * FROM student WHERE no = ? AND school_cd = ?";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, no);
            st.setString(2, schoolCd);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Student s = new Student();
                    s.setNo(rs.getInt("no"));
                    s.setName(rs.getString("name"));
                    s.setEntYear(rs.getInt("ent_year"));
                    s.setClassNum(rs.getInt("class_num"));
                    s.setAttend(rs.getBoolean("is_attend"));
                    s.setSchoolCd(rs.getString("school_cd"));
                    return s;
                }
            }
        }
        return null;
    }

    public List<Student> findByEntYearAndClass(int entYear, int classNum, String schoolCd) throws Exception {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student WHERE school_cd = ? AND ent_year = ? AND class_num = ? ORDER BY no";

        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, schoolCd);
            st.setInt(2, entYear);
            st.setInt(3, classNum);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Student s = new Student();
                    s.setNo(rs.getInt("no"));
                    s.setName(rs.getString("name"));
                    s.setEntYear(rs.getInt("ent_year"));
                    s.setClassNum(rs.getInt("class_num"));
                    s.setAttend(rs.getBoolean("is_attend"));
                    s.setSchoolCd(rs.getString("school_cd"));
                    list.add(s);
                }
            }
        }
        return list;
    }
    public void delete(int studentNo, String schoolCd) throws Exception {
        try (Connection con = getConnection()) {
            String sql = "DELETE FROM student WHERE no = ? AND school_cd = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, studentNo);
            st.setString(2, schoolCd);
            st.executeUpdate();
        }
    }


}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import bean.Student;

public class StudentDao extends DAO {

    // 学生登録メソッド
    public void insert(Student student) throws Exception {
        Connection connection = getConnection();

        String sql = "INSERT INTO students (no, name, ent_year, class_num, is_attend) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, student.getNo());
        stmt.setString(2, student.getName());
        stmt.setInt(3, student.getEntYear());
        stmt.setString(4, student.getClassNum());
        stmt.setBoolean(5, student.isIsAttend());

        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }
}

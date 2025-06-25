package bean;

/**
 * 学生情報を表すJavaBeansクラス
 */
public class Student {
    // フィールド（メンバ変数）
    private String no;         // 学生番号
    private String name;       // 氏名
    private int entYear;       // 入学年度
    private String classNum;   // クラス番号
    private boolean isAttend;  // 在学中かどうか

    // ゲッターとセッター

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEntYear() {
        return entYear;
    }

    public void setEntYear(int entYear) {
        this.entYear = entYear;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public boolean isIsAttend() {
        return isAttend;
    }

    public void setIsAttend(boolean isAttend) {
        this.isAttend = isAttend;
    }
}

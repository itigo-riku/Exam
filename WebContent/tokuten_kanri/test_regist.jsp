<!-- スタイル追加 -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="base.jsp" %>

<style>
.form-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  align-items: center;
  padding: 15px;
  background: #fdfdfd;
  border: 1px solid #ccc;
  border-radius: 10px;
  max-width: 900px;
  margin: 0 auto;
}

.form-group {
  display: flex;
  align-items: center;
  gap: 8px;
}

.form-group label {
  white-space: nowrap;
}

.form-container select,
.form-container input[type="number"] {
  padding: 4px 8px;
  font-size: 14px;
  width: 140px;
  box-sizing: border-box;
}

.form-container button {
  padding: 5px 15px;
  font-size: 14px;
  cursor: pointer;
}
/*ボタンの位置を変更したり見た目を変える*/
.submit {
  background-color: #007bff
  color: white;
  padding: 8px 20px;
  border: none;
  border-radius: 5px;
  font-size: 14px;
  cursor: pointer;
}

.submit:hover {
  background-color: #007bff
}
</style>


<section style="flex:1;">
  <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4" style="text-align:center;">成績登録</h2>

  <c:if test="${not empty message}">
    <div class="alert-box">⚠️ ${message}</div>
  </c:if>

  <!-- 条件フォーム -->
	<!-- 条件フォーム全体 -->
<form method="get" action="TestRegist.action" class="form-container" style="flex-wrap: wrap; justify-content: center;">
  <div class="form-group">
    <label>入学年度:</label>
    <select name="ent_year" required> ... </select>
  </div>
  <div class="form-group">
    <label>クラス番号:</label>
    <select name="class_num" required> ... </select>
  </div>
  <div class="form-group">
    <label>科目:</label>
    <select name="subject_cd" required> ... </select>
  </div>
  <div class="form-group">
    <label>回数:</label>
    <input type="number" name="no" value="${selectedNo}" min="1" required/>
  </div>

  <!--  ボタンを別行にして中央配置にする -->
  <div style="width: 100%; text-align: center; margin-top: 10px;">
    <button type="submit" class="submit">検索</button>
  </div>
</form>


  <!-- 結果表示 -->
  <c:if test="${not empty testList}">
    <form action="TestRegistDone.action" method="post" class="px-4">
      <table class="result-table">
        <tr>
          <th>入学年度</th>
          <th>クラス番号</th>
          <th>学生番号</th>
          <th>氏名</th>
          <th>点数</th>
        </tr>
        <c:forEach var="t" items="${testList}">
          <tr>
            <td>${selectedEntYear}</td>
            <td>${selectedClassNum}</td>
            <td>${t.studentNo}</td>
            <td>${t.studentName}</td>
            <td>
              <input type="hidden" name="student_no" value="${t.studentNo}" />
              <input type="number" name="point" value="${t.point != null ? t.point : ''}" min="0" max="100" required/>
            </td>
          </tr>
        </c:forEach>
      </table>

      <!-- 条件保持 -->
      <input type="hidden" name="subject_cd" value="${selectedSubjectCd}"/>
      <input type="hidden" name="no" value="${selectedNo}"/>
      <input type="hidden" name="class_num" value="${selectedClassNum}"/>

      <div style="text-align:center; margin-top:20px;">
        <button type="submit" class="submit">登録して終了</button>
      </div>
    </form>
  </c:if>
</section>

<%@ include file="../footer.html" %>

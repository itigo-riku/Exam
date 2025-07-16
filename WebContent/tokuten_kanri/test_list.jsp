<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="base.jsp" %>

<style>
/* セクションの幅を明示的に広げる */
.main-section {
  width: 95%;
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
  box-sizing: border-box;
  /*text-align: center;*/
}

/* フォーム全体を横並びにして均等に広げる */
.form-container {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  gap: 24px; /*フォーム間の感覚のこと*/
  flex-wrap: wrap; /*小さい画面でも崩れないようにする*/
}

/* 各フォームに十分な幅を確保 */
.search-box {
  flex: 1 1 45%; /*画面に応じて幅を調整*/
  min-width: 300px;
  max-width: 600px;
  padding: 20px;
  border: 1px solid #ccc;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-sizing: border-box;
}
</style>

	<section class="main-section">
	  <h2 class="title">成績参照</h2>

	  <div class="form-container">
	    <!-- 科目情報フォーム -->
	    <form method="get" action="TestList.action" class="search-box">
	      <div class="form-title">科目情報</div>

	      <div class="form-row">
	        <label for="ent_year">入学年度:</label>
	        <select name="ent_year" id="ent_year" required>
	          <option value="">選択してください</option>
	          <c:forEach var="y" items="${entYears}">
	            <option value="${y}">${y}</option>
	          </c:forEach>
	        </select>
	      </div>

	      <div class="form-row">
	        <label for="class_num">クラス番号:</label>
	        <select name="class_num" id="class_num" required>
	          <option value="">選択してください</option>
	          <c:forEach var="c" items="${classNums}">
	            <option value="${c}">${c}</option>
	          </c:forEach>
	        </select>
	      </div>

	      <div class="form-row">
	        <label for="subject_cd">科目:</label>
	        <select name="subject_cd" id="subject_cd" required>
	          <option value="">選択してください</option>
	          <c:forEach var="s" items="${subjects}">
	            <option value="${s.cd}">${s.name}</option>
	          </c:forEach>
	        </select>
	      </div>

	      <button type="submit" class="form-button">科目別検索</button>
	    </form>

	    <!-- 学生情報フォーム -->
	    <form method="get" action="TestList.action" class="search-box">
	      <div class="form-title">学生情報</div>

	      <div class="form-row">
	        <label for="student_no">学生番号:</label>
	        <input type="number" name="student_no" id="student_no" required />
	      </div>

	      <button type="submit" class="form-button">学生別検索</button>
	    </form>
	  </div>

	  <!-- エラーメッセージ -->
	  <c:if test="${not empty noScoresMessage}">
	    <p class="alert-box">${noScoresMessage}</p>
	  </c:if>

	  <!-- 科目別結果表示 -->
	  <c:if test="${not empty testListByClass and not empty studentsWithScores and not empty selectedSubject}">
	    <h4 class="subtitle">科目別 成績一覧</h4>
	    <p>科目: ${selectedSubject.name} (${selectedSubject.cd})</p>

	    <table>
	      <thead>
	        <tr>
	          <th>入学年度</th>
	          <th>クラス番号</th>
	          <th>学生番号</th>
	          <th>氏名</th>
	          <c:forEach begin="1" end="${maxNo}" var="i">
	            <th>${i}回</th>
	          </c:forEach>
	        </tr>
	      </thead>
	      <tbody>
	        <c:forEach var="student" items="${studentsWithScores}">
	          <tr>
	            <td>${student.entYear}</td>
	            <td>${student.classNum}</td>
	            <td>${student.no}</td>
	            <td>${student.name}</td>
	            <c:forEach begin="1" end="${maxNo}" var="i">
	              <td><c:out value="${student.points[i]}" default="-" /></td>
	            </c:forEach>
	          </tr>
	        </c:forEach>
	      </tbody>
	    </table>
	  </c:if>

	  <!-- 学生別結果表示 -->
	  <c:if test="${not empty student and not empty testListByStudent and not empty subjectMap}">
	    <h4 class="subtitle">学生別 成績一覧</h4>
	    <p>学生番号: ${student.no}</p>
	    <p>氏名: ${student.name}</p>

	    <table>
	      <thead>
	        <tr>
	          <th>科目コード</th>
	          <th>科目名</th>
	          <th>回数</th>
	          <th>点数</th>
	        </tr>
	      </thead>
	      <tbody>
	        <c:forEach var="t" items="${testListByStudent}">
	          <tr>
	            <td>${t.subjectCd}</td>
	            <td>${subjectMap[t.subjectCd]}</td>
	            <td>${t.no}</td>
	            <td>${t.point}</td>
	          </tr>
	        </c:forEach>
	      </tbody>
	    </table>
	  </c:if>
	</section>
<%@ include file="../footer.html" %>
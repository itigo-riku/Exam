<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="base.jsp" %>

<style>
/* 全体の幅・配置を調整 */
.main-section {
  width: 95%;
  max-width: 1000px;
  margin: 20px auto;
  padding: 20px;
  box-sizing: border-box;
}

/* 横並びでラップ対応するフォーム */
.form-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: flex-start;
  align-items: flex-end;
  margin-bottom: 20px;
}

/* 各検索ボックス */
.search-box {
  flex: 1 1 220px;
  min-width: 220px;
  max-width: 300px;
  padding: 15px;
  background-color: #f9f9f9;
  border: 1px solid #ccc;
  border-radius: 8px;
}

/* 共通フォーム要素スタイル */
.form-label {
  display: block;
  font-weight: bold;
  margin-bottom: 6px;
}

.form-select,
.form-check-input,
.btn {
  width: 100%;
  padding: 6px;
  box-sizing: border-box;
}

.checkbox-inline {

  align-items: center;

}

</style>

<section style="flex: 1;">
  <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4" style="text-align: center;">学生管理</h2>
  <div class="my-2 text-end px-4">
    <a href="StudentCreate.action">新規登録</a>
  </div>

  <form method="get" action="StudentList.action">
    <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
      <div class="col-4">
        <label class="form-label" for="student-f1-select">入学年度</label>
        <select class="form-select" id="student-f1-select" name="f1">
          <option value="0" <c:if test="${f1 == '0' || f1 == null}">selected</c:if>>------</option>
          <c:forEach var="year" items="${ent_year_set}">
            <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
          </c:forEach>
        </select>
      </div>

      <div class="col-4">
        <label class="form-label" for="student-f2-select">クラス</label>
        <select class="form-select" id="student-f2-select" name="f2">
          <option value="0" <c:if test="${f2 == '0' || f2 == null}">selected</c:if>>------</option>
          <c:forEach var="num" items="${class_num_set}">
            <option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
          </c:forEach>
        </select>
      </div>

		<!-- これは在学中の所のチェックボックスの配置・サイズなど決めるところ-->
	<div class="col-2 d-flex align-items-center justify-content-start">
		<label for="student-f3-check" class="mb-0 me-2">在学中</label>
	 	<input type="checkbox" class="form-check-input" id="student-f3-check" name="f3" value="t"
		  <c:if test="${f3 == 't'}">checked</c:if> />
	</div>



      <div class="col-2 text-center">
        <button class="btn btn-secondary" id="filter-button">絞り込み</button>
      </div>

      <div class="mt-2 text-warning">${errors["f1"]}</div>
    </div>
  </form>

  <c:choose>
    <c:when test="${not empty students and fn:length(students) > 0}">
      <div>検索結果: ${fn:length(students)} 件</div>
      <table class="table table-hover">
        <thead>
          <tr>
            <th>入学年度</th>
            <th>学生番号</th>
            <th>氏名</th>
            <th>クラス</th>
            <th class="text-center">在学中</th>
            <th></th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="student" items="${students}">
            <tr>
              <td>${student.entYear}</td>
              <td>${student.no}</td>
              <td>${student.name}</td>
              <td>${student.classNum}</td>
              <td class="text-center">
                <c:choose>
                  <c:when test="${student.attend}">○</c:when>
                  <c:otherwise>X</c:otherwise>
                </c:choose>
              </td>
              <td><a href="StudentUpdate.action?no=${student.no}">変更</a></td>
              <td><a href="StudentDelete.action?no=${student.no}">削除</a></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:when>
    <c:otherwise>
      <c:if test="${filterExecuted}">
        <div class="text-danger">学生情報が見つかりませんでした。</div>
      </c:if>
    </c:otherwise>
  </c:choose>
</section>

</div>

<%@ include file="../footer.html" %>

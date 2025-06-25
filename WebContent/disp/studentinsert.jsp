<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.*" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.html" %>
<%@include file="menu.jsp" %>

<div class="content">
  <h2 class="menu-title">学生新規登録</h2>

  <!-- 登録フォーム -->
  <form action="studentinsert" method="post">
    <table>
      <tr>
        <td>学生番号：</td>
        <td><input type="text" name="no" required></td>
      </tr>
      <tr>
        <td>氏名：</td>
        <td><input type="text" name="name" required></td>
      </tr>
      <tr>
        <td>入学年度：</td>
        <td>
          <select name="ent_year" required>
            <option value="">--選択--</option>
            <c:forEach var="year" items="${entYears}">
              <option value="${year}">${year}</option>
            </c:forEach>
          </select>
        </td>
      </tr>
      <tr>
        <td>クラス：</td>
        <td>
          <select name="class_no" required>
            <option value="">--選択--</option>
            <c:forEach var="cls" items="${classNums}">
              <option value="${cls}">${cls}</option>
            </c:forEach>
          </select>
        </td>
      </tr>
      <tr>
        <td>在学中：</td>
        <td>
          <label><input type="checkbox" name="is_attend" value="1"> 在学中</label>
        </td>
      </tr>
    </table>

    <div style="margin-top: 10px;">
      <button type="submit">登録する</button>
      <a href="<%= request.getContextPath() %>/action/studentlist">戻る</a>
    </div>
  </form>
</div>

<%@include file="../footer.html" %>

<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>

<div class="ログイン">
  <form action="Login.action" method="post">
    <a>ログイン</a>

    <div class="id_input">
      <p>　　　　　ID
        <input class="ef" type="text" name="id" placeholder="半角でご入力ください" value="${id}" required>
      </p>
    </div>

    <div class="pass_input">
      <p>パスワード
        <input class="ef" type="password" name="password" id="Show" placeholder="30文字以内の半角英数字でご入力ください" maxlength="30" required>
      </p>
    </div>

    <p>
      <input type="checkbox" name="chk_d_ps" onclick="myFunc()">パスワードを表示
    </p>

    <p>
      <input type="submit" name="login" value="ログイン">
    </p>
  </form>
</div>

<script>
  function myFunc() {
    var show = document.getElementById('Show');
    show.type = (show.type === 'password') ? 'text' : 'password';
  }
</script>

<%@include file="../footer.html" %>

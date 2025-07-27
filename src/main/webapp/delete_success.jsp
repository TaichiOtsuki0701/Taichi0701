<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <title>削除成功</title>
</head>
<body>
    <h2 style="color: blue;">商品が正常に削除されました！</h2>
    <p>削除された商品名: <%= request.getParameter("name") != null ? request.getParameter("name") : "不明" %></p>
    <button type="button" class="button-link"
        onclick="location.href='ProductListServlet'">商品一覧に戻る</button>
</body>
</html>
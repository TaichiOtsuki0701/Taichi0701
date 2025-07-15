<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>商品登録</title>
</head>
<body>
	<h2>商品登録</h2>
	<from action="RegistrationServlet" method="post"> 商品名: <input
		type="text" name="name" required>
	<br>
	価格: <input type="number" name="price"  required>
	<br>
	在庫数: <input type="number" name="stock" required>
	<br>
	カテゴリ: <select name="categoryId" required>
	</select>
	<br>
	<input type="submit" value="登録">
	</form>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>商品登録</title>
</head>
<body>
	<h2>商品登録</h2>
	<c:if test="${not empty errorMessage}">
		<p style="color: red;">${errorMessage}</p>
	</c:if>
	<form action="RegistrationServlet" method="post">
		商品名: <input type="text" name="name" required value="${param.name}"><br>
		価格: <input type="number" name="price" required value="${param.price}"><br>
		在庫数: <input type="number" name="stock" required value="${param.stock}"><br>
		カテゴリ: <select name="categoryId" required>
			<c:forEach var="category" items="${categories}">
				<option value="${category.categoryId}"
					${category.categoryId == param.categoryId ? 'selected' : ''}>${category.categoryName}</option>
			</c:forEach>
		</select> <br> <input type="submit" value="登録">
	</form>
</body>
</html>

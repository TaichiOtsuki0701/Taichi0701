<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>編集完了</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
	crossorigin="anonymous">
</head>
<body>
	<div class="container mt-4">
		<h2 class="mb-4">商品編集完了</h2>

		<div class="alert alert-success" role="alert">
			商品「
			<c:out value="${param.name }" />
			」を編集しました。

		</div>
		<a href="ProductListServlet" class="btn btn-primary">商品一覧へ戻る</a>
	</div>
</body>
</html>
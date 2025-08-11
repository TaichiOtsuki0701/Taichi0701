<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品編集</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
	crossorigin="anonymous">
</head>
<body>
	<div class="mx-auto" style="width: 50%;">
		<h2 class="text-center mb-4">商品編集</h2>

		<c:if test="${not empty errorMessage}">
			<div class="alert alert-danger" role="alert">${errorMessage}</div>
		</c:if>

		<form action="UpdateServlet" method="post">
			<input type="hidden" name="id" value="${product.id}">

			<div class="mb-3">
				<label for="name" class="form-label">商品名</label> <input type="text"
					class="form-control" id="name" name="name" value="${product.name}"
					required>
			</div>
			<div class="mb-3">
				<label for="price" class="form-label">価格</label> <input
					type="number" class="form-control" id="price" name="price"
					value="${product.price}" required>
			</div>
			<div class="mb-3">
				<label for="stock" class="form-label">在庫数</label> <input
					type="number" class="form-control" id="stock" name="stock"
					value="${product.stock}" required>
			</div>
			<div class="mb-3">
				<label for="categoryId" class="form-label">カテゴリ</label> <select
					class="form-select" id="categoryId" name="categoryId" required>
					<c:forEach var="category" items="${categories}">
						<option value="${category.categoryId}"
							<c:if test="${category.categoryId == product.category.categoryId}">selected</c:if>>
							<c:out value="${category.categoryName}" /></option>
					</c:forEach>
				</select>
			</div>

			<button type="submit" class="btn btn-primary">更新する</button>
			<button type="button" class="btn btn-secondary"
				onclick="location.href='ProductListServlet'">商品一覧に戻る</button>
		</form>
	</div>
</body>
</html>
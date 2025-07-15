<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>商品一覧</title>
</head>
<body>
    <h1>商品一覧</h1>
    <a href="product_registration.jsp">商品登録</a>
    <table border="1">
        <tr>

            <th>商品名</th>
            <th>価格</th>
            <th>在庫数</th>
            <th>カテゴリID</th>
        </tr>
        <c:forEach var="product" items="${products}">
            <tr>
               
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.stock}</td>
                <td>${product.categoryId}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>

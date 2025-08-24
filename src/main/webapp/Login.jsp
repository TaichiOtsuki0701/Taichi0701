<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>ログイン画面</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
          crossorigin="anonymous">
</head>
<body>
    <div class="container mt-4">
        <h1 class="mb-4">ログイン</h1>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">
                ${errorMessage}
            </div>
        </c:if>

        <form action="LoginServlet" method="post">
            <table class="table">
                <tr>
                    <td>ユーザー名</td>
                    <td><input type="text" name="userName" maxlength="32" class="form-control" /></td>
                </tr>
                <tr>
                    <td>パスワード</td>
                    <td><input type="password" name="password" maxlength="32" class="form-control" /></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" name="submit" value="ログイン" class="btn btn-primary" />
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>商品一覧</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <style>
        /* 必要に応じて、Bootstrapのデフォルトを上書きするスタイル */
        /* ボタンを横並びにするための .action-buttons スタイルは不要になりますが、念のため残しておきます */
        /* .action-buttons {
            display: flex;
            gap: 5px;
            justify-content: flex-start;
            align-items: center;
        } */
    </style>
</head>
<body>
    <div class="container mt-4">
        <h1 class="mb-4">商品一覧</h1>

        <button type="button" class="btn btn-primary mb-3"
                onclick="location.href='RegistrationServlet'">商品登録</button>

        <table class="table table-striped table-bordered table-hover">
            <thead class="table-dark">
                <tr>
                    <th scope="col">商品名</th>
                    <th scope="col">価格</th>
                    <th scope="col">在庫数</th>
                    <th scope="col">カテゴリ名</th>
                    <th scope="col">編集</th> <%-- 編集ボタン用の列 --%>
                    <th scope="col">削除</th> <%-- 削除ボタン用の列 --%>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td>${product.name}</td>
                        <td>${product.price}</td>
                        <td>${product.stock}</td>
                        <td>${product.category.categoryName}</td>
                        <td> <%-- 編集ボタンのセル --%>
                            <form action="UpdateServlet" method="get" style="display: inline;">
                                <input type="hidden" name="id" value="${product.id}">
                                <button type="submit" class="btn btn-info btn-sm">編集</button>
                            </form>
                        </td>
                        <td> <%-- 削除ボタンのセル --%>
                            <form action="DeleteServlet" method="post" style="display: inline;">
                                <input type="hidden" name="id" value="${product.id}">
                                <button type="submit" class="btn btn-danger btn-sm"
                                    onclick="return confirm('本当に削除しますか？');">削除</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <%-- BootstrapのJSバンドルを読み込む（必要に応じて） --%>
    <%-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7zrmLbddk+b+LwR+Q/b+M/B" crossorigin="anonymous"></script> --%>
</body>
</html>
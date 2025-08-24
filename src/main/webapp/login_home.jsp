<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>認証画面</title>
</head>
<body>
	<table>
		<tr>
			<td>ログインに成功しました。</td>
		</tr>
		<tr>
			<td>
				<form action="LogoutServlet" method="post">
					<input type="submit" name="submit" value="ログアウト" />
				</form>
			</td>
		</tr>
	</table>

</body>
</html>
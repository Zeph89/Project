<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Computers database</title>
<link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" media="screen" href="css/main.css">
</head>
<body>
	<header class="topbar">
		<h1 class="fill">Computer Database</h1>
	</header>

	<section id="main">
		<h1>Edit computer</h1>
			<form action="/projectComputer/UpdateComputerServlet" method="POST">
				<%@ include file="infoComputer.jsp" %>
			</form>
		<form action="/projectComputer/DeleteComputerServlet" method="GET" class="topRight">
			<input type="hidden" name="id" value="${id}" >
			<input type="submit" value="Delete this computer" class="btn danger">
		</form>
	</section>

</body>
</html>
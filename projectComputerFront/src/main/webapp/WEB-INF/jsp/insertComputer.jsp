<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
		<h1>Add a computer</h1>
            <form:form method="POST" action="/projectComputer/insertComputer.html" commandName="computerForm" modelAttribute="computerForm">
				<%@ include file="infoComputer.jsp" %>
            </form:form>
	</section>

</body>
</html>
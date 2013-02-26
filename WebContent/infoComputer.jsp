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
			<fieldset>
				<div class="clearfix ">
					<label for="name">Computer name</label>
					<div class="input">

						<input type="text" id="name" name="name" value="${computer.name}"> <span
							class="help-inline">Required</span>
					</div>
				</div>
				<div class="clearfix ">
					<label for="introduced">Introduced date</label>
					<div class="input">
						<c:choose>
							<c:when test="${computer.introducedDate != null}"> 
								<input type="text" id="introduced" name="introduced" value="${computer.introducedDate}">
							</c:when>
							<c:otherwise>
								<input type="text" id="introduced" name="introduced" value="">
							</c:otherwise>
						</c:choose>
						<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
					</div>
				</div>
				<div class="clearfix ">
					<label for="discontinued">Discontinued date</label>
					<div class="input">
						<c:choose>
							<c:when test="${computer.discontinuedDate != null}"> 
								<input type="text" id="discontinued" name="discontinued" value="${computer.discontinuedDate}">
							</c:when>
							<c:otherwise>
								<input type="text" id="discontinued" name="discontinued" value="">
							</c:otherwise>
						</c:choose>
						<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
					</div>
				</div>
				<div class="clearfix ">
					<label for="company">Company</label>
					<div class="input">
						<select id="company" name="company">
							<option class="blank" value="">-- Choose a company --</option>
							<c:forEach var="company" items="${companies}" >
								<c:choose>
									<c:when test="${computer.company != null and computer.company.id == company.id}"> 
										<option selected="selected" value="${company.id}">${company.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${company.id}">${company.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select> <span class="help-inline"></span>
					</div>
				</div>
			</fieldset>
			<div class="actions">
				<input type="submit" value="Save this computer" class="btn primary">
				or <a href="/projectComputer/InitServlet" class="btn">Cancel</a>
			</div>
		</form>
		<form action="/projectComputer/DeleteComputerServlet?id=${computer.id}" method="POST" class="topRight">
			<input type="submit" value="Delete this computer" class="btn danger">
		</form>
	</section>

</body>
</html>
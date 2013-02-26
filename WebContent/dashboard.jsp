<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Computers database</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="css/main.css">
</head>
<body>
	<header class="topbar">
		<h1 class="fill">Computer Database</h1>
	</header>

	<section id="main">
		<h1>574 computers found</h1>
		<c:choose>
			<c:when test="${message == 1}">
				<div class="alert-message warning">
					<strong>Done!</strong> Computer <strong>${name}</strong> has been
					created
				</div>
			</c:when>
			<c:when test="${message == 2}">
				<div class="alert-message warning">
					<strong>Done!</strong> Computer <strong>${name}</strong> has been
					updated
				</div>
			</c:when>
			<c:when test="${message == 3}">
				<div class="alert-message warning">
					<strong>Done!</strong> Computer <strong>${name}</strong> has been
					deleted
				</div>
			</c:when>
		</c:choose>
		<div id="actions">
			<form action="/projectComputer/InitServlet" method="GET">
				<input type="search" id="searchbox" name="search" value=""
					placeholder="Filter by computer name..."> <input
					type="submit" id="searchsubmit" value="Filter by name"
					class="btn primary">
			</form>
			<a class="btn success" id="add"
				href="/projectComputer/AddComputerServlet">Add a new computer</a>
		</div>

		<table class="computers zebra-striped">
			<thead>
				<tr>
					<th class="col2 header headerSortUp">Computer name</th>
					<th class="col3 header ">Introduced</th>
					<th class="col4 header ">Discontinued</th>
					<th class="col5 header ">Company</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="computer" items="${computers}">
					<tr>
						<td><a
							href="/projectComputer/InfoComputerServlet?id=${computer.id}">${computer.name}</a></td>
						<td><c:choose>
								<c:when test="${computer.introducedDate != null}"> ${computer.introducedDate} </c:when>
								<c:otherwise>
									<em>-</em>
								</c:otherwise>
							</c:choose></td>
						<td><c:choose>
								<c:when test="${computer.discontinuedDate != null}"> ${computer.discontinuedDate} </c:when>
								<c:otherwise>
									<em>-</em>
								</c:otherwise>
							</c:choose></td>
						<td><c:choose>
								<c:when test="${computer.company.id != 0}"> ${computer.company.name} </c:when>
								<c:otherwise>
									<em>-</em>
								</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div id="pagination" class="pagination">
			<ul>
				<li class="prev"><a href="/computers">&larr; Previous</a></li>
				<li class="current"><a>Displaying 1 to 10 of 575</a></li>
				<li class="next"><a href="/computers?p=2">Next &rarr;</a></li>
			</ul>
		</div>
	</section>
</body>
</html>



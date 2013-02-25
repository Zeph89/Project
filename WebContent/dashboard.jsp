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
		<h1 class="fill">
			Computer Database
		</h1>
	</header>

	<section id="main">
		<h1>574 computers found</h1>
		<div id="actions">
			<form action="/computers" method="GET">
				<input type="search" id="searchbox" name="f" value=""
					placeholder="Filter by computer name..."> <input
					type="submit" id="searchsubmit" value="Filter by name"
					class="btn primary">
			</form>
			<a class="btn success" id="add" href="/computers/new">Add a new computer</a>
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
				<c:forEach var="computer" items="${computers}" >
					<tr>
						<td>${computer.name}</td>
						<td>
							<c:choose>
								<c:when test="${computer.introducedDate != null}"> ${computer.introducedDate} </c:when>
								<c:otherwise>
									<em>-</em>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${computer.discontinuedDate != null}"> ${computer.discontinuedDate} </c:when>
								<c:otherwise>
									<em>-</em>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${computer.company != null}"> ${computer.company.name} </c:when>
								<c:otherwise>
									<em>-</em>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>		
				</c:forEach>
			</tbody>
		</table>
	</section>

</body>
</html>



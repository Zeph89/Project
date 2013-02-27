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
		<h1>${nbComputer} computers found</h1>
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
				<input type="search" id="searchbox" name="search" value="<c:if test="${param.search != null}">${param.search}</c:if>"
					placeholder="Filter by computer name..."> 
				<input type="submit" id="searchsubmit" value="Filter by name"
					class="btn primary">
			</form>
			<a class="btn success" id="add"
				href="/projectComputer/AddComputerServlet">Add a new computer</a>
		</div>

		<table class="computers zebra-striped">
			<thead>
				<tr>
					<th class="col2 header 
						<c:if test="${sort == null}">headerSortDown</c:if>
						<c:if test="${sort > 0}">headerSortUp</c:if>
						<c:if test="${sort < 0}">headerSortDown</c:if>
					">
						<a href="InitServlet?
							<c:if test="${sort == null}">sort=-1</c:if>
							<c:if test="${sort > 0}">sort=1</c:if>
							<c:if test="${sort < 0}">sort=-1</c:if>
							<c:if test="${param.search != null}">&search=${param.search}</c:if>">Computer name
	        			</a>
    				</th>
    				<th class="col3 header 
						<c:if test="${sort == null}">headerSortDown</c:if>
						<c:if test="${sort > 0}">headerSortUp</c:if>
						<c:if test="${sort < 0}">headerSortDown</c:if>
					">
						<a href="InitServlet?
							<c:if test="${sort == null}">sort=-2</c:if>
							<c:if test="${sort > 0}">sort=2</c:if>
							<c:if test="${sort < 0}">sort=-2</c:if>
							<c:if test="${param.search != null}">&search=${param.search}</c:if>">Introduced
	        			</a>
    				</th>
    				<th class="col4 header 
						<c:if test="${sort == null}">headerSortDown</c:if>
						<c:if test="${sort > 0}">headerSortUp</c:if>
						<c:if test="${sort < 0}">headerSortDown</c:if>
					">
						<a href="InitServlet?
							<c:if test="${sort == null}">sort=-3</c:if>
							<c:if test="${sort > 0}">sort=3</c:if>
							<c:if test="${sort < 0}">sort=-3</c:if>
							<c:if test="${param.search != null}">&search=${param.search}</c:if>">Discontinued
	        			</a>
    				</th>
    				<th class="col5 header 
						<c:if test="${sort == null}">headerSortDown</c:if>
						<c:if test="${sort > 0}">headerSortUp</c:if>
						<c:if test="${sort < 0}">headerSortDown</c:if>
					">
						<a href="InitServlet?
							<c:if test="${sort == null}">sort=-4</c:if>
							<c:if test="${sort > 0}">sort=4</c:if>
							<c:if test="${sort < 0}">sort=-4</c:if>
							<c:if test="${param.search != null}">&search=${param.search}</c:if>">Company
	        			</a>
    				</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="computer" items="${computers}">
					<tr>
						<td><a
							href="/projectComputer/InfoComputerServlet?id=${computer.id}">${computer.name}</a></td>
						<td><c:choose>
								<c:when test="${computer.introducedDate != null}"> ${computer.introducedDateFormat} </c:when>
								<c:otherwise>
									<em>-</em>
								</c:otherwise>
							</c:choose></td>
						<td><c:choose>
								<c:when test="${computer.discontinuedDate != null}"> ${computer.discontinuedDateFormat} </c:when>
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
			<c:choose>
				<c:when test="${page == 0}">
					<li class="prev disabled"><a>&larr; Previous</a></li>
				</c:when>
				<c:otherwise>
					<li class="prev"><a href="InitServlet?page=${page - 1}
					<c:if test="${param.search != null}">&search=${param.search}</c:if>">&larr;
							Previous</a></li>
				</c:otherwise>
			</c:choose>
			<li class="current"><a>Displaying ${page * PAGE_SIZE + 1} to
					<c:choose>
						<c:when test="${page!= 0 && (page+1)*PAGE_SIZE>nbComputer}">
					${nbComputer}
				</c:when>
						<c:otherwise>
					${page * PAGE_SIZE + PAGE_SIZE} 
				</c:otherwise>
					</c:choose> of ${nbComputer}
			</a></li>

			<c:choose>
				<c:when test="${page!= 0 && (page+1)*PAGE_SIZE>nbComputer}">
					<li class="next disabled"><a>Next &rarr;</a></li>
				</c:when>
				<c:otherwise>
					<li class="next"><a href="InitServlet?page=${page + 1}
					<c:if test="${param.search != null}">&search=${param.search}</c:if>">Next
							&rarr;</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
	</section>
</body>
</html>



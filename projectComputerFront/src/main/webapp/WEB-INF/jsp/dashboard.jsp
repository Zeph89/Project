<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="pag" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
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
		<h1>
			${nbComputer}
			<c:choose>
				<c:when test="${nbComputer < 2}"> 
					computer found
				</c:when>
				<c:otherwise>
					computers found
				</c:otherwise>
			</c:choose>
		</h1>
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
			<form action="/projectComputer/dashboard.html" method="GET">
				<input type="search" id="searchbox" name="search" value="<c:if test="${param.search != null}">${param.search}</c:if>"
					placeholder="Filter by computer name..."> 
				<input type="submit" id="searchsubmit" value="Filter by name"
					class="btn primary">
			</form>
			<a class="btn success" id="add"
				href="/projectComputer/insertComputer.html">Add a new computer</a>
		</div>

		<table class="computers zebra-striped">
			<thead>
				<tr>
					<th class="col2 header 
						<c:if test="${sort == null}">headerSortDown</c:if>
						<c:if test="${sort > 0}">headerSortUp</c:if>
						<c:if test="${sort < 0}">headerSortDown</c:if>
					">
						<a href="dashboard.html?
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
						<a href="dashboard.html?
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
						<a href="dashboard.html?
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
						<a href="dashboard.html?
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
							href="/projectComputer/infoComputer.html?id=${computer.id}">${computer.name}</a></td>
						<td><c:choose>
								<c:when test="${computer.introducedDate != null}">
                                    <joda:format value="${computer.introducedDate}" style="M-" />
                                </c:when>
								<c:otherwise>
									<em>-</em>
								</c:otherwise>
							</c:choose></td>
						<td><c:choose>
								<c:when test="${computer.discontinuedDate != null}">
                                    <joda:format value="${computer.discontinuedDate}" style="M-" />
                                </c:when>
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
        <pag:pagination page="${page}" nbComputers="${nbComputers}" PAGE_SIZE="${PAGE_SIZE}" search="${param.search}" />
	</section>
</body>
</html>



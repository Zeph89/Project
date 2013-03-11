<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag isELIgnored="false" %>
<%@ attribute name="page" required="true" type="java.lang.Integer" %>
<%@ attribute name="nbComputers" required="true" type="java.lang.Integer" %>
<%@ attribute name="PAGE_SIZE" required="true" type="java.lang.Integer" %>
<%@ attribute name="search" required="true" type="java.lang.String" %>
<div id="pagination" class="pagination">
    <ul>
        <c:choose>
            <c:when test="${page == 0}">
                <li class="prev disabled"><a>&larr; Previous</a></li>
            </c:when>
            <c:otherwise>
                <li class="prev"><a href="dashboard.html?page=${page - 1}
					<c:if test="${search != null}">&search=${search}</c:if>">&larr;
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
            <c:when test="${(page+1)*PAGE_SIZE>nbComputer}">
                <li class="next disabled"><a>Next &rarr;</a></li>
            </c:when>
            <c:otherwise>
                <li class="next"><a href="dashboard.html?page=${page + 1}
					<c:if test="${search != null}">&search=${search}</c:if>">Next
                    &rarr;</a></li>
            </c:otherwise>
        </c:choose>
    </ul>
</div>
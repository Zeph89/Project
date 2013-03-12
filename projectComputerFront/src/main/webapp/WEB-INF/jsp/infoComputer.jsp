<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<input type="hidden" name="id" value="${id}" >
<fieldset>
    <c:choose>
        <c:when test="${nameE == 1}">
            <div class="clearfix error">
                <label for="name">Computer name</label>
                <div class="input">
                    <form:input path="name" id="name" value="${name}"/> <span
                            class="help-inline">Required</span>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="clearfix ">
                <label for="name">Computer name</label>
                <div class="input">
                    <form:input path="name" id="name" value="${name}"/> <span
                        class="help-inline">Required</span>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
    <c:choose>
		<c:when test="${introducedE == 1}"> 
			<div class="clearfix error">
				<label for="introduced">Introduced date</label>
				<div class="input">
                    <form:input path="introduced" id="introduced" value="${introduced}"/>
					<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
				</div>	
			</div>
		</c:when>	
		<c:otherwise>
			<div class="clearfix ">
				<label for="introduced">Introduced date</label>
				<div class="input">
                    <form:input path="introduced" id="introduced" value="${introduced}"/>
					<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
				</div>	
			</div>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${discontinuedE == 1}"> 
			<div class="clearfix error">
				<label for="discontinued">Discontinued date</label>
				<div class="input">
                    <form:input path="discontinued" id="discontinued" value="${discontinued}"/>
					<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
				</div>
			</div>
		</c:when>	
		<c:otherwise>
			<div class="clearfix ">
				<label for="discontinued">Discontinued date</label>
				<div class="input">
                    <form:input path="discontinued" id="discontinued" value="${discontinued}"/>
					<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
	
	<div class="clearfix ">
		<label for="company">Company</label>
		<div class="input">
            <form:select path="company" id="company">
				<option class="blank" value="">-- Choose a company --</option>
				<c:forEach var="company" items="${companies}" >
					<c:choose>
						<c:when test="${companyId == company.id}">
                            <form:option selected="selected" value="${company.id}">${company.name}</form:option>
						</c:when>
						<c:otherwise>
                            <form:option value="${company.id}">${company.name}</form:option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</form:select> <span class="help-inline"></span>
		</div>
	</div>
</fieldset>
<div class="actions">
	<input type="submit" value="Save this computer" class="btn primary">
	or <a href="/projectComputer/dashboard.html" class="btn">Cancel</a>
</div>

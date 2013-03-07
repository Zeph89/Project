<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<input type="hidden" name="id" value="${id}" >
<fieldset>
	<div class="clearfix ">
		<label for="name">Computer name</label>
		<div class="input">
			<input type="text" id="name" name="name" value="${name}"> <span
				class="help-inline">Required</span>
		</div>
	</div>
	<c:choose>
		<c:when test="${introducedE == 1}"> 
			<div class="clearfix error">
				<label for="introduced">Introduced date</label>
				<div class="input">
					<input type="text" id="introduced" name="introduced" value="${introducedDate}">
					<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
				</div>	
			</div>
		</c:when>	
		<c:otherwise>
			<div class="clearfix ">
				<label for="introduced">Introduced date</label>
				<div class="input">
					<input type="text" id="introduced" name="introduced" value="${introducedDate}">	
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
					<input type="text" id="discontinued" name="discontinued" value="${discontinuedDate}">
					<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
				</div>
			</div>
		</c:when>	
		<c:otherwise>
			<div class="clearfix ">
				<label for="discontinued">Discontinued date</label>
				<div class="input">
					<input type="text" id="discontinued" name="discontinued" value="${discontinuedDate}">
					<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
	
	<div class="clearfix ">
		<label for="company">Company</label>
		<div class="input">
			<select id="company" name="company">
				<option class="blank" value="">-- Choose a company --</option>
				<c:forEach var="company" items="${companies}" >
					<c:choose>
						<c:when test="${companyId == company.id}"> 
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
	or <a href="/projectComputer/dashboard.html" class="btn">Cancel</a>
</div>

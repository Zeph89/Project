<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <form method="post" id="loginForm" action="j_spring_security_check">
    	<input type="text" name="j_username" id="j_username" />
    	<input type="password" name="j_password" id="j_password" />
    	<input type="submit" value="Valider" />
    </form>
</body>
</html>
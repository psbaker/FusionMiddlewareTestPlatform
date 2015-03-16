<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>

<html>

	<head>
	
		<title>ABC FMW Test Server </title>
		
	</head>
	
	<body>
	
		<mytags:navbar/> 
	
		<h2>Result:</h2>
		
		<c:choose>
			
			<c:when test="${result == 'Passed'}">
				<h3><font color="green">Pass</font></h3>			
			</c:when>
			
			<c:when test="${result == 'Failed'}">
				<h3><font color="red">Fail</font></h3>			
			</c:when>
		
		</c:choose>
		
		
		
		
	</body>
</html>
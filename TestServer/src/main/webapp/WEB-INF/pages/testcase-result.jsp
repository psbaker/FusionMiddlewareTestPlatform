<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

	<head>
	
		<title>ABC FMW Test Server </title>
		
	</head>
	
	<body>
	
		<h1 style="display:inline">Test Server</h1><h2 style="display:inline"> - ABC Fusion Middleware</h2> 
	
		<h2>Testcase Results</h2>
		
		<c:choose>
			
			<c:when test="${result == 'Passed'}">
				<h3><font color="green">${result}</font></h3>			
			</c:when>
			
			<c:when test="${result == 'Failed'}">
				<h3><font color="red">${result}</font></h3>			
			</c:when>
		
		</c:choose>
		
		
		
		
	</body>
</html>
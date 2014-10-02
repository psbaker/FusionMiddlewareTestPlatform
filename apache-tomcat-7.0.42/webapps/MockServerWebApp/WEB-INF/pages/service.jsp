<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

	<head>
	
		<title>ABC FMW Mock Server </title>
		
	</head>
	
	<body>
	
		<h1 style="display:inline">Mock Server</h1><h2 style="display:inline"> - ABC Fusion Middleware</h2>
	
		<h2>${model.serviceId}</h2>
		
		<p><a href="addoperation-form?sysId=${model.sysId}&serviceId=${model.serviceId}">Add Operation</a></p>
		
		<h4>Existing Operations</h4>

		<c:forEach items="${model.operationList}" var="operationId" varStatus="i" begin="0" >

			<c:url var="thisURL" value="operationconfig-form">
				<c:param name="sysId" value="${model.sysId}"/>
				<c:param name="serviceId" value="${model.serviceId}"/>
  				<c:param name="operationId" value="${operationId}"/>
			</c:url>

			<a href="<c:out value="${thisURL}"/>"> <c:out value="${operationId}"/> </a>

		</c:forEach> 		
		
	</body>
	
</html>
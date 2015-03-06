<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>

<html>

	<head>
	
		<title>ABC FMW Mock Server </title>
		
	</head>
	
	<body>
	
		<mytags:navbar/>
	
		<h2>${model.sysId}</h2>
		
		<p><a href="addservice-form?sysId=${model.sysId}">Add Service</a></p>
		
		<h4>Existing Services</h4>

		<c:forEach items="${model.serviceList}" var="serviceId" varStatus="i" begin="0" >

			<c:url var="thisURL" value="service">
				<c:param name="sysId" value="${model.sysId}"/>
  				<c:param name="serviceId" value="${serviceId}"/>
			</c:url>

			<p><a href="<c:out value="${thisURL}"/>"> <c:out value="${serviceId}"/> </a> </p>

		</c:forEach> 		
		
	</body>
	
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
           
<html>
	
	<head>
	
		<title>ABC FMW Mock Server </title>
		
	</head>
	
	<body>
	
		<mytags:navbar/>
		
		<p><a href="addsystem-form">Add System</a></p>
				
		<h4>Systems</h4>

			<c:forEach items="${model.systemList}" var="sysId" varStatus="i" begin="0" >
	
				<c:url var="thisURL" value="edit-system">
  					<c:param name="sysId" value="${sysId}"/>
				</c:url>

				<p><a href="<c:out value="${thisURL}"/>"> <c:out value="${sysId}"/> </a></p>

			</c:forEach> 
		
	</body>
	
</html>
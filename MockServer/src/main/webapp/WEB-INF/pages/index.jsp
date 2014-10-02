<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
<html>
	
	<head>
	
		<title>ABC FMW Mock Server </title>
		
	</head>
	
	<body>
	
		<h1 style="display:inline">Mock Server</h1><h2 style="display:inline"> - ABC Fusion Middleware</h2>
		
		<p><a href="mockserverconfig-form">System Configuration</a></p>
		
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
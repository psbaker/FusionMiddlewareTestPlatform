<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	
	<head>
	
		<title>ABC FMW Test Server </title>
		
	</head>
	
	<body>
	
		<h1 style="display:inline">Test Server</h1><h2 style="display:inline"> - ABC Fusion Middleware</h2> 
	
		<h2>${model.projectId}</h2>
		
		<p><a href="addtestcase-form?projectId=${model.projectId}"/>Add Testcase</a></p> 
		
		<h4>Testcases</h4>

		<c:forEach items="${model.testcases}" var="testcaseId" varStatus="i" begin="0" >

			<c:url var="thisURL" value="testcase">
				<c:param name="projectId" value="${model.projectId}"/>
				<c:param name="testcaseId" value="${testcaseId}"/>
			</c:url>

			<p><a href="<c:out value="${thisURL}"/>"> <c:out value="${testcaseId}"/> </a></p>

		</c:forEach> 		
		
	</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
           
<html>
	
	<head>
	
		<title>ABC FMW Test Server </title>
		
	</head>
	
	<body>
	
		<mytags:navbar/>
		
		<p><a href="addtestsuite-form"/>Add Testsuite</a></p> 
		
<!-- 		<p><a href="addscheduledjob-form"/>Add Scheduled Job</a></p> -->
		
		<h4>Testsuites</h4>

			<c:forEach items="${model.projectList}" var="projectId" varStatus="i" begin="0" >
	
				<c:url var="thisURL" value="project">
  					<c:param name="projectId" value="${projectId}"/>
				</c:url>

				<p><a href="<c:out value="${thisURL}"/>"> <c:out value="${projectId}"/> </a></p>

			</c:forEach> 	
		
		<!--  <h4>Scheduled Jobs</h4>
		
		<c:forEach items="${model.scheduledJobsList}" var="scheduledJobId" varStatus="i" begin="0" >
	
				<c:url var="scheduledJobURL" value="scheduledJob">
  					<c:param name="scheduledJobId" value="${scheduledJobId}"/>
				</c:url>

				<p><a href="<c:out value="${scheduledJobURL}"/>"> <c:out value="${scheduledJobId}"/> </a></p>

			</c:forEach> -->	
		
	</body>
	
</html>
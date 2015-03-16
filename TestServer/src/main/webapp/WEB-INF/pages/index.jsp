<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
           
<html>
	
	<head>
	
		<title>ABC FMW Test Server </title>
		
		<style type="text/css">
			
			table, th, td 
			{
    			border: 1px solid black;
    			border-collapse: collapse;
			}
			
			th, td 
			{
    			padding: 15px;
			}
			
			/*  Define the background color for all the ODD background rows  */
			tr:nth-child(odd)
			{ 
				background: #b8d1f3;
			}
			
			/*  Define the background color for all the EVEN background rows  */
			tr:nth-child(even)
			{
				background: #dae5f4;
			}
		
		</style>
		
	</head>
	
	<body>
	
		<mytags:navbar/>
		
		<p><a href="addtestsuite-form"/>Add Testsuite</a></p> 
		
		<table>
			<tr>
				<th>TestSuites</th>
			</tr>		
			
			<c:forEach items="${model.projectList}" var="projectId" varStatus="i" begin="0" >
							
				<c:url var="thisURL" value="project">
  					<c:param name="projectId" value="${projectId}"/>
				</c:url>

				<tr>
					<td><a href="<c:out value="${thisURL}"/>"> <c:out value="${projectId}"/> </a></td>
				</tr>

			</c:forEach>			
		
		</table>
		
	</body>
	
</html>
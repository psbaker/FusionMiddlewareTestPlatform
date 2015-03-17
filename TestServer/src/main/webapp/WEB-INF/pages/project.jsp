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
	
		<h2>${model.projectId}</h2>
		
		<p><a href="addtestcase-form?domain=${model.domain}&projectId=${model.projectId}"/>Add TestCase</a></p> 
		
		<table>
			<tr>
				<th>TestCases</th>
			</tr>
			
			<c:forEach items="${model.testcases}" var="testcaseId" varStatus="i" begin="0" >

				<c:url var="thisURL" value="testcase">
					<c:param name="domain" value="${model.domain}"/>
					<c:param name="projectId" value="${model.projectId}"/>
					<c:param name="testcaseId" value="${testcaseId}"/>
				</c:url>
	
				<tr>
					<td><a href="<c:out value="${thisURL}"/>"> <c:out value="${testcaseId}"/> </a></td>
				</tr>

			</c:forEach> 		
				
		</table>
				
	</body>
</html>
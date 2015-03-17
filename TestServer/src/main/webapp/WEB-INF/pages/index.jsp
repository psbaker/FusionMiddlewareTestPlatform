<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
           
<html>
	
	<head>
	
		<title>ABC FMW Test Server </title>
		
		<style type="text/css">
		
			table
			{
				float: left;
				margin: 10px;
			}
			
			table, th, td 
			{
    			border: 1px solid black;
    			border-collapse: collapse;    			
    			padding: 15px;
    			text-align: center;    			
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
				<th>BMS TestSuites</th>				
			</tr>		
			
			<c:forEach items="${model.bmsProjectList}" var="bmsProjectId" varStatus="i" begin="0" >
							
				<c:url var="thisURL" value="project">
					<c:param name="domain" value="bms"/>
  					<c:param name="projectId" value="${bmsProjectId}"/>
				</c:url>

				<tr>
					<td><a href="<c:out value="${thisURL}"/>"><c:out value="${bmsProjectId}"/></a></td>
				</tr>

			</c:forEach>			
		
		</table>
		
		<table>
			<tr>
				<th>WCMS TestSuites</th>				
			</tr>		
			
			<c:forEach items="${model.wcmsProjectList}" var="wcmsProjectId" varStatus="i" begin="0" >
							
				<c:url var="thisURL" value="project">
					<c:param name="domain" value="wcms"/>
  					<c:param name="projectId" value="${wcmsProjectId}"/>
				</c:url>

				<tr>
					<td><a href="<c:out value="${thisURL}"/>"> <c:out value="${wcmsProjectId}"/> </a></td>
				</tr>

			</c:forEach>	
		
		</table>
		
		<table>
			<tr>
				<th>Generic TestSuites</th>				
			</tr>		
			
			<c:forEach items="${model.genericProjectList}" var="genericProjectId" varStatus="i" begin="0" >
							
				<c:url var="thisURL" value="project">
					<c:param name="domain" value="generic"/>
  					<c:param name="projectId" value="${genericProjectId}"/>
				</c:url>

				<tr>
					<td><a href="<c:out value="${thisURL}"/>"> <c:out value="${genericProjectId}"/> </a></td>
				</tr>

			</c:forEach>			
		
		</table>
		
	</body>
	
</html>
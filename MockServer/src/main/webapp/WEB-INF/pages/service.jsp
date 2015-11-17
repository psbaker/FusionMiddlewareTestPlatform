<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>

<html>

	<head>
	
		<title>ABC FMW Mock Server </title>
		
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
	
		<h2>${model.serviceId}</h2>
		
		<p><a href="addoperation-form?sysId=${model.sysId}&serviceId=${model.serviceId}">Add Operation</a></p>
		
		<table>
			<tr>
				<th colspan="2">Operations</th>				
			</tr>

			<c:forEach items="${model.operationList}" var="operationId" varStatus="i" begin="0" >
				
				<c:url var="thisURL" value="operationconfig-form">
					<c:param name="sysId" value="${model.sysId}"/>
					<c:param name="serviceId" value="${model.serviceId}"/>
	  				<c:param name="operationId" value="${operationId}"/>
				</c:url>
				
				<c:url var="deleteOperationURL" value="delete-operation">
					<c:param name="sysId" value="${model.sysId}"/>
					<c:param name="serviceId" value="${model.serviceId}"/>
	  				<c:param name="operationId" value="${operationId}"/>
				</c:url>
	
				<tr>
					<td><a href="<c:out value="${thisURL}"/>"> <c:out value="${operationId}"/> </a></td>
					<td><a href="<c:out value="${deleteOperationURL}"/>"> <c:out value="Delete"/> </a></td>
				</tr>
	
			</c:forEach> 		
		</table>
		
	</body>
	
</html>
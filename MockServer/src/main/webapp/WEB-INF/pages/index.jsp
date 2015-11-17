<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		
		<p><a href="addsystem-form">Add System</a></p>
				
		<table>
			<tr>
				<th>Systems</th>				
			</tr>
			
			<c:forEach items="${model.systemList}" var="sysId" varStatus="i" begin="0" >
	
				<c:url var="thisURL" value="edit-system">
  					<c:param name="sysId" value="${sysId}"/>
				</c:url>

				<tr>
					<td><a href="<c:out value="${thisURL}"/>"> <c:out value="${sysId}"/> </a></td>
				</tr>

			</c:forEach>
			
		</table>
		
	</body>
	
</html>
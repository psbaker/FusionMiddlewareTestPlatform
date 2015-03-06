<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>

<html>

	<head>
	
		<title>ABC FMW Mock Server </title>
		
	</head>
	
	<body>
	
		<mytags:navbar/>
	
		<p>Add Service</p>
		
		<form:form method="post" commandName="mockservice" action="process-addservice?sysId=${mockservice.sysId}">
			
			<table>
				<tr>
					<td>Name:</td>
					<td><form:input path="id" size="75"></form:input></td>
				</tr>
				<tr>
					<td><input type="submit" value="Save"/></td>
				</tr>
			</table>
		
		</form:form>
				
	</body>
	
</html>
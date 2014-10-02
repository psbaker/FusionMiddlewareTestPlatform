<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>

	<head>
	
		<title>ABC FMW Test Server </title>
		
	</head>
	
	<body>
	
		<h1 style="display:inline">Test Server</h1><h2 style="display:inline"> - ABC Fusion Middleware</h2>
	
		<p>Add Testsuite</p>
		
		<form:form method="post" commandName="testsuite-entity" action="process-addtestsuite">
			
			<table>
				<tr>
					<td>Name:</td>
					<td><form:input path="id" size="75"></form:input></td>
				</tr>
				<tr>
					<td><form:checkbox path="scheduledJob"></form:checkbox></td>
				</tr>
				<tr>
					<td><input type="submit" value="Save"/></td>
				</tr>
			</table>
			
		</form:form>
				
	</body>
	
</html>
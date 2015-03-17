<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>

<html>
	
	<head>
	
		<title>ABC FMW Test Server </title>
		
		<link rel="stylesheet" href="<c:url value='/js/codemirror/codemirror.css'/>">
				
		<script type="text/javascript" src="<c:url value='/js/codemirror/codemirror.js'/>"></script>
		
		<script type="text/javascript" src="<c:url value='/js/codemirror/xml.js'/>"></script>
		
		<style type="text/css">
      		.CodeMirror {border: 1px solid black; font-size:13px; width:100%; height:750}
    	</style>	
		
	</head>

	<body>
	
		<mytags:navbar/> 
	
		<h2>${testcaseId}</h2>
		
		<form:form method="post" commandName="testcase" action="process-testcase">
		
			<table>
				<tr>
					<td><form:textarea path="testcaseId" style="display:none"></form:textarea></td>
				</tr>							
				<tr>
					<td><form:textarea path="projectId" style="display:none"></form:textarea></td>
				</tr>
				<tr>
					<td><form:textarea path="domain" style="display:none"></form:textarea></td>
				</tr>
				<tr>					
					<td><form:textarea path="testXml" id="code" name="code"></form:textarea></td>
				</tr>				
				<tr>
					<td><input type="submit" name="action" value="Save"/><input type="submit" name="action" value="Execute"/></td>
				</tr>
			</table>			
		
		</form:form>

	</body>
	
	<script>		
		var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
   			lineNumbers: true,
   			styleActiveLine: true,
  			matchBrackets: true
		});			
	</script>	
		
</html>
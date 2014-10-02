<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>

	<head>
	
		<title>ABC FMW Mock Server </title>
		
		<link rel="stylesheet" href="<c:url value='/js/codemirror/codemirror.css'/>">
				
		<script type="text/javascript" src="<c:url value='/js/codemirror/codemirror.js'/>"></script>
		
		<script type="text/javascript" src="<c:url value='/js/codemirror/xml.js'/>"></script>
		
		<style type="text/css">
      		.CodeMirror {border: 1px solid black; font-size:13px; width:750; height:750}
    	</style>
		
	</head>
	
	<body>
	
		<h1 style="display:inline">Mock Server</h1><h2 style="display:inline"> - ABC Fusion Middleware</h2>
		
		<p>Edit Operation </p>
		
			<form:form method="post" commandName="operationconfig" action="process-operationconfig?sysId=${operationconfig.sysId}&serviceId=${operationconfig.serviceId}&operationId=${operationconfig.operationId}">
			
			<p><form:textarea path="config" id="code"></form:textarea></p>
			
			<p><input type="submit" value="Save"/></p>
		
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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>

<html>

	<head>
	
		<title>ABC FMW Mock Server </title>
		
		<link rel="stylesheet" href="<c:url value='/js/codemirror/codemirror.css'/>">
				
		<script type="text/javascript" src="<c:url value='/js/codemirror/codemirror.js'/>"></script>
		
		<script type="text/javascript" src="<c:url value='/js/codemirror/xml.js'/>"></script>
		
		<style type="text/css">
      		.CodeMirror {border: 1px solid black; font-size:13px; height:750}
    	</style>
		
	</head>
	
	<body>
	
		<mytags:navbar/>
	
		<p>Add Operation</p>
		
		<form:form method="post" commandName="mockoperation" action="process-addoperation?sysId=${mockoperation.sysId}&serviceId=${mockoperation.serviceId}">
		
			<table style="table-layout:fixed">
				<tr>
					<td>Name:</td>
					<td style="width:750"><form:input path="id" style="width:100%"></form:input></td>
				</tr>
				<tr>
					<td valign="top">Response:</td>
					<td style="width:750"><form:textarea id="code" path="config"></form:textarea></td>
				</tr>
				<tr>
					<td><input type="submit" value="Save"/></td>
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
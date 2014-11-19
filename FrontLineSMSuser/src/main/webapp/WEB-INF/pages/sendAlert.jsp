<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register to receive an Alert</title>
</head>
<body>
	<div align="center">
		<form:form action="user" method="post" commandName="userForm">
			<table border="0">
				<tr>
					<td colspan="2" align="center"><h2>Welcome.Send a server status alert to:</h2></td>
				</tr>
				<tr>
					<td>First Name:</td>
					<td><form:input path="firstname" /></td>
				</tr>
				<tr>
					<td>Last Name:</td>
					<td><form:input path="lastName" /></td>
				</tr>
				<tr>
					<td>Phone Number:</td>
					<td><form:input path="phoneNumber" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="Send Server Status" /></td>
				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>
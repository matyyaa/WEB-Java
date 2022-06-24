<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Patient List</title>
</head>
<body>

<jsp:include page="_header.jsp"></jsp:include>
<jsp:include page="_menu.jsp"></jsp:include>

<h3>Patient List</h3>

<p style="color: red;">${errorString}</p>

<table border="1" cellpadding="5" cellspacing="1" >
    <tr>
        <th>Name</th>
        <th>Birthday</th>
        <th>Diagnosis</th>
        <th>Status</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${patientList}" var="patient" >
        <tr>
            <td>${patient.name}</td>
            <td>${patient.birthday}</td>
            <td>${patient.diagnosis}</td>
            <td>${patient.status}</td>
            <td>
                <a href="editPatient?id=${patient.id}">Edit</a>
            </td>
            <td>
                <a href="deletePatient?id=${patient.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>
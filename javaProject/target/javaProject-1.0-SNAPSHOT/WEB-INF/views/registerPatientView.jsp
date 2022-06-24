<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
</head>
<body>
<jsp:include page="_header.jsp"></jsp:include>

<h3>Patient Register Page</h3>
<p style="color: red;">${errorString}</p>


<form method="POST" action="${pageContext.request.contextPath}/registerPatient">
    <table border="0">
        <tr>
            <td>Name</td>
            <td><input type="text" name="name"/> </td>
        </tr>
        <tr>
            <td>birthday</td>
            <td><input type="date" name="birthday"/> </td>
        </tr>
        <tr>
            <td>Diagnosis</td>
            <td><input type="text" name="diagnosis"/> </td>
        </tr>
        <tr>
            <td>Status</td>
            <td><input type="text" name="status"/> </td>
        </tr>
        <tr>
            <td colspan ="2">
                <input type="submit" value= "Submit" />
                <a href="${pageContext.request.contextPath}/">Cancel</a>
            </td>
        </tr>
    </table>
</form>
<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>
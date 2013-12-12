<%-- 
    Document   : index
    Created on : Dec 10, 2013, 3:08:03 PM
    Author     : fmiranda
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login</h1>
        <form method="post" action="webresources/user/login" enctype="application/x-www-form-urlencoded">
            Email: <input type="text" name="email"><br>
            Senha: <input type="password" name="senha"><br>
            <input type="submit" value="Enviar">
        </form>
    </body>
</html>

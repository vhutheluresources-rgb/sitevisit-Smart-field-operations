<%-- 
    Document   : outcome
    Created on : 08 Mar 2024, 10:20:47 AM
    Author     : MemaniV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Outcome Page</title>
    </head>
    <body>
        <h1>Outcome</h1>
        <%
            Integer number = (Integer)request.getAttribute("number");
            boolean isPalindrome = (Boolean)request.getAttribute("isPalindrome");
            int reverseForm = (Integer)request.getAttribute("reverseForm");
            String computerName = (String)pageContext.getServletContext().getInitParameter("computer_name");
            String name = (String)session.getAttribute("name");
        %>
        <p>
            Hi <b><%=name%></b>. Below is the outcome from <b><%=computerName%></b>.
        </p>
        <table>
            <tr>
                <td>Entered number </td>
                <td><b><%=number%></b></td>
            </tr>
            <tr>
                <td>Reverse form </td>
                <td><b><%=reverseForm%></b></td>
            </tr>
            <tr>
                <td>Is the number a palindrome? </td>
                <td><b><%=isPalindrome%></b></td>
            </tr>
        </table>
        <p>
            Please click <a href="number_entry.html">here</a> to enter another 
            number or <a href="summary.jsp">here</a> to display summary.
        </p>            
    </body>
</html>


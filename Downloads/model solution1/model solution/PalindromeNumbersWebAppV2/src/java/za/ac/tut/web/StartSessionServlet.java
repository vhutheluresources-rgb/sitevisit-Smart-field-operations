/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.web;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.ac.tut.model.bl.LoginSBLocal;

/**
 *
 * @author MemaniV
 */
public class StartSessionServlet extends HttpServlet {
    @EJB LoginSBLocal lsl;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String correctUsername = getServletContext().getInitParameter("correct_username");
        String correctPassword = getServletContext().getInitParameter("correct_password");
        
        if(lsl.areLoginCredentialsValid(username, password, correctUsername, correctPassword)){
            HttpSession session = request.getSession(true);
            initializeSession(session, name);
            RequestDispatcher disp = request.getRequestDispatcher("session_started.jsp");
            disp.forward(request, response);
        } else {
            response.sendRedirect("login.html");
        }
    }

    private void initializeSession(HttpSession session, String name) {
        int cnt = 0, cntNonPalindromes = 0, tot = 0;
        session.setAttribute("cnt", cnt);
        session.setAttribute("tot", tot);
        session.setAttribute("cntNonPalindromes", cntNonPalindromes);
        session.setAttribute("name", name);
    }
}


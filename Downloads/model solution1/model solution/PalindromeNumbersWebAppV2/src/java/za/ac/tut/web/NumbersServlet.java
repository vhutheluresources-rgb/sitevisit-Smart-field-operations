package za.ac.tut.web;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.ac.tut.model.NumbersCheckerInterface;


/**
 *
 * @author MemaniV
 */
public class NumbersServlet extends HttpServlet {
    @EJB NumbersCheckerInterface nci;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer number = Integer.parseInt(request.getParameter("number")); 
       
        //perform some business logic here through Stateless Session Beans
        if(nci.isNumberValid(number)){
            boolean isPalindrome = nci.isNumberPalindrome(number);
            int reverseForm = nci.setInReverse(number);
            updateSession(session, isPalindrome);
            
            request.setAttribute("isPalindrome", isPalindrome);
            request.setAttribute("number", number);
            request.setAttribute("reverseForm", reverseForm);
            
            RequestDispatcher disp = request.getRequestDispatcher("outcome.jsp");
            disp.forward(request, response); 
        } 
    }
    
    private void updateSession(HttpSession session, boolean isPalindrome) {
        int tot = (Integer)session.getAttribute("tot");
        tot++;
        session.setAttribute("tot", tot); 
        
        if(isPalindrome){
            int cnt = (Integer)session.getAttribute("cnt");
            cnt++;
            session.setAttribute("cnt", cnt);  
        } else {
            int cntNonPalindromes = (Integer)session.getAttribute("cntNonPalindromes");
            cntNonPalindromes++;
            session.setAttribute("cntNonPalindromes", cntNonPalindromes);
        }       
    }    
}



package ro.ase.dis;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/Client"})
public class Client extends HttpServlet {

    MessageChecker messageChecker;


    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<br/>");
            out.println("<br/>");
            out.println("Messages received:");
            out.println("<br/>");
            out.println("<br/>");
            
//            for(Message m: responseReceiverAsync.messageChecker.getFinalMessages()){
//                out.println(m.getDecryptedText()+" from "+m.getHost()+" at "+m.getTime()+" using password "+m.getPassword());
//                out.println("<br/>");
//            }
            
            out.println("<br/>");
            out.println("<br/>");
        }
    }

    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<br/>");
            out.println("<br/>");
            out.println("Not implemented...");
            out.println("<br/>");
            out.println("<br/>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processGetRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processPostRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
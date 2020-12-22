package moviebuddy.servlet.provider.ticketprice;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import moviebuddy.dao.TheatreDAO;
import moviebuddy.util.Validation;
import moviebuddy.util.S;

@WebServlet("/TicketPriceAdd")
public class TicketPriceAddServlet extends HttpServlet {
    private static final long serialVersionUID = 5817039034625632748L;

    private TheatreDAO theatreDAO;

    public void init() {
        theatreDAO = new TheatreDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Object role = session.getAttribute(S.ROLE);
            // Check authorized access as admin
            if (role != null && role.equals(S.ADMIN)) {
                // Sanitize user inputs
                String theatreId = Validation.sanitize(request.getParameter("theatreId"));
                String startTime = Validation.sanitize(request.getParameter("startTime"));
                String price = Validation.sanitize(request.getParameter("price"));

                // Validate user inputs
                String errorMessage = Validation.validateTicketPriceForm(startTime, price);
                if (errorMessage.isEmpty() && theatreDAO.getTicketPrice(theatreId, startTime) != null) {
                    errorMessage = "Ticket price already existed";
                }

                // Add ticket price
                if (errorMessage.isEmpty()) {
                    errorMessage = theatreDAO.addTicketPrice(theatreId, startTime, price);
                }

                // Return previous inputs
                if (!errorMessage.isEmpty()) {
                    session.setAttribute(S.ERROR_MESSAGE, errorMessage);
                    session.setAttribute(S.TICKET_START_TIME_CREATE, startTime);
                    session.setAttribute(S.TICKET_PRICE_CREATE, price);
                }

                // Redirect to Manage Ticket Price page
                response.sendRedirect(S.MANAGE_TICKET_PRICE_PAGE);
            } else {
                // Redirect to Home page for unauthorized access
                response.sendRedirect(S.HOME_PAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(S.ERROR_PAGE);
        }
    }
}

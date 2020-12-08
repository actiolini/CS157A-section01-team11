package moviebuddy.servlet.provider;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import moviebuddy.dao.TheatreDAO;
import moviebuddy.util.Validation;

@WebServlet("/TicketPriceAdd")
public class TicketPriceAddServlet extends HttpServlet {
    private static final long serialVersionUID = 5817039034625632748L;

    private static final String START_TIME = "ticketPriceStartTimeUpload";
    private static final String PRICE = "ticketPricePriceUpload";

    private TheatreDAO theatreDAO;

    public void init() {
        theatreDAO = new TheatreDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int theatreId = Integer.parseInt(request.getParameter("theatreId"));
            String startTime = Validation.sanitize(request.getParameter("startTime"));
            String priceInput = Validation.sanitize(request.getParameter("price"));
            String errorMessage = Validation.validateTime(startTime);
            if (errorMessage.isEmpty()) {
                errorMessage = Validation.validateDouble(priceInput);
            }
            double price = 0;
            if (errorMessage.isEmpty()) {
                price = Double.parseDouble(priceInput);
            }
            if (errorMessage.isEmpty() && theatreDAO.getTicketPrice(theatreId, startTime) != null) {
                errorMessage = "Ticket price already existed";
            }
            if (errorMessage.isEmpty()) {
                errorMessage = theatreDAO.addTicketPrice(theatreId, startTime, price);
            }
            if (!errorMessage.isEmpty()) {
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", errorMessage);
                session.setAttribute(START_TIME, startTime);
                session.setAttribute(PRICE, priceInput);
            }
            response.sendRedirect("ticketprice.jsp");
        } catch (Exception e) {
            response.sendRedirect("error.jsp");
            e.printStackTrace();
        }
    }
}
package moviebuddy.servlet.provider.room;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import moviebuddy.dao.TheatreDAO;
import moviebuddy.util.Validation;

@WebServlet("/RoomCreate")
public class RoomCreateServlet extends HttpServlet {
    private static final long serialVersionUID = -4836590538356344837L;

    private static final String ROOM_NUMBER = "roomNumberUpload";
    private static final String SECTIONS = "roomSectionsUpload";
    private static final String SEATS = "roomSeatsUpload";

    private TheatreDAO theatreDAO;

    public void init() {
        theatreDAO = new TheatreDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Object role = session.getAttribute("role");
            if (role != null && role.equals("admin")) {
                String theatreId = Validation.sanitize(request.getParameter("theatreId"));
                String roomNumber = Validation.sanitize(request.getParameter("roomNumber"));
                String errorMessage = "";
                if (theatreDAO.getRoomById(theatreId, roomNumber) != null) {
                    errorMessage = "Room number already existed.";
                }
                String sections = Validation.sanitize(request.getParameter("sections"));
                String seats = Validation.sanitize(request.getParameter("seats"));
                if (errorMessage.isEmpty()) {
                    errorMessage = theatreDAO.createRoom(theatreId, roomNumber, sections, seats);
                }
                if (errorMessage.isEmpty()) {
                    response.sendRedirect("manageroom.jsp");
                } else {
                    session.setAttribute("errorMessage", errorMessage);
                    session.setAttribute(ROOM_NUMBER, roomNumber);
                    session.setAttribute(SECTIONS, sections);
                    session.setAttribute(SEATS, seats);
                    response.sendRedirect("roomcreate.jsp");
                }
            } else {
                response.sendRedirect("home.jsp");
            }
        } catch (Exception e) {
            response.sendRedirect("error.jsp");
            e.printStackTrace();
        }
    }
}

package moviebuddy.servlet.provider.room;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import moviebuddy.dao.TheatreDAO;
import moviebuddy.util.Validation;

@WebServlet("/RoomDelete")
public class RoomDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 5778241877398137416L;

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
                String errorMessage = theatreDAO.deleteRoom(theatreId, roomNumber);
                if (!errorMessage.isEmpty()) {
                    session.setAttribute("errorMessage", errorMessage);
                }
                response.sendRedirect("manageroom.jsp");
            } else {
                response.sendRedirect("home.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

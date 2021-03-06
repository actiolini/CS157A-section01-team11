package moviebuddy.servlet.provider.theatre;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import moviebuddy.dao.TheatreDAO;
import moviebuddy.model.Theatre;

@WebServlet("/TheatreGet")
public class TheatreGetServlet extends HttpServlet {
    private static final long serialVersionUID = -4869640868654901643L;

    private static final String THEATRES = "theatreList";

    private TheatreDAO theatreDAO;

    public void init() {
        theatreDAO = new TheatreDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Theatre> theatres = new ArrayList<>();
            HttpSession session = request.getSession();
            Object role = session.getAttribute("role");
            if (role != null && role.equals("admin")) {
                theatres = theatreDAO.listTheatres();
            }
            if (role != null && role.equals("manager")) {
                String employTheatreId = session.getAttribute("employTheatreId").toString();
                Theatre theatre = theatreDAO.getTheatreById(employTheatreId);
                theatres.add(theatre);
            }
            request.setAttribute(THEATRES, theatres);
        } catch (Exception e) {
            response.sendRedirect("error.jsp");
            e.printStackTrace();
        }
    }
}

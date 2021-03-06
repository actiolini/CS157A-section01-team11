package moviebuddy.servlet.provider.schedule;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import moviebuddy.dao.ScheduleDAO;
import moviebuddy.util.Validation;

@WebServlet("/ScheduleDelete")
public class ScheduleDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = -8764893949095966660L;

    private ScheduleDAO scheduleDAO;

    public void init() {
        scheduleDAO = new ScheduleDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Object role = session.getAttribute("role");
            if (role != null && (role.equals("admin") || role.equals("manager"))) {
                String scheduleId = Validation.sanitize(request.getParameter("scheduleId"));
                String errorMessage = scheduleDAO.deleteSchedule(scheduleId);
                if (!errorMessage.isEmpty()) {
                    session.setAttribute("errorMessage", errorMessage);
                }
                response.sendRedirect("manageschedule.jsp");
            } else {
                response.sendRedirect("home.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

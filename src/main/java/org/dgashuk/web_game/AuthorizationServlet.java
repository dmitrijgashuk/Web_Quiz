package org.dgashuk.web_game;

import org.dgashuk.web_game.model.User;
import org.dgashuk.web_game.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/authorization")
public class AuthorizationServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(AuthorizationServlet.class);

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        userService = (UserService) servletContext.getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession oldSession = request.getSession();
        oldSession.invalidate();
        log.debug("Before start a new game, invalidate old game session ");

        String userName = request.getParameter("name");

        if (userName.isBlank()) {
            String errorMessage = "Имя игрока не должно быть пустым";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp")
                    .forward(request, response);
            log.debug("User entered invalid player name, add 'errorMessage' to the request " +
                    "and returns to register.jsp ");
            return;
        }

        User user = userService.get(userName);
        if (user == null) {
            user = userService.createNewUser(userName);
            userService.save(user);
        }

        HttpSession newSession = request.getSession();
        newSession.setAttribute("user", user);
        log.debug("Add 'user' - " + userName + " to the session");

        response.sendRedirect("location");
        log.debug("Move to '/location' servlet");
    }

}

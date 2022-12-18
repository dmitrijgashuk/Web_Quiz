package org.dgashuk.web_game;

import org.dgashuk.web_game.model.*;
import org.dgashuk.web_game.service.LocationService;
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

@WebServlet(name = "locationServlet", value = "/location")
public class LocationServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(LocationServlet.class);
    private LocationService locationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        locationService = (LocationService) servletContext.getAttribute("locationService");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        log.debug("Get user: " + user.getName() + " from session.getAttribute: ");

        locationService.putLocationDataToRequest(user,request);

        request.getRequestDispatcher("/WEB-INF/jsp/location.jsp")
                .forward(request, response);

        log.debug("forward to /WEB-INF/jsp/location.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String nextLocation = request.getParameter("nextLocation");

        log.debug("Jsp send parameter nextLocation: " + nextLocation);
        if (nextLocation != null) {
            user.setCurrentLocation(nextLocation);
            log.debug("Set user location " + nextLocation);
        }

        if(user.getCurrentLocation().equals("Exit")){
            response.sendRedirect("finish");
            return;
        }

        String itemId = request.getParameter("itemId");
        if (itemId != null) {
            locationService.pickUpItem(itemId,user);
        }

        locationService.putLocationDataToRequest(user,request);
        request.getRequestDispatcher("/WEB-INF/jsp/location.jsp")
                .forward(request, response);

        log.debug("forward to /WEB-INF/jsp/location.jsp");
    }

}

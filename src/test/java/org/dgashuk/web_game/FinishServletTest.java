package org.dgashuk.web_game;

import org.dgashuk.web_game.model.User;
import org.dgashuk.web_game.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class FinishServletTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    UserService userService;
    @Mock
    HttpSession session;
    @Mock
    RequestDispatcher requestDispatcher;
    @Mock
    ServletConfig servletConfig;
    @Mock
    ServletContext servletContext;
    @Mock
    User user;

    FinishServlet finishServlet;

    @BeforeEach
    void test_initialization() throws ServletException {
        doReturn(servletContext).when(servletConfig).getServletContext();
        doReturn(userService).when(servletContext).getAttribute("userService");
        finishServlet = new FinishServlet();
    }
    @Test
    void test_init_userServlet() throws ServletException {
        finishServlet.init(servletConfig);

        Mockito.verify(servletContext, Mockito.times(1))
                .getAttribute("userService");
    }

    @Test
    void test_doGetMethod() throws ServletException, IOException {
        doReturn(session).when(request).getSession();
        doReturn(requestDispatcher).when(request).getRequestDispatcher("/WEB-INF/jsp/finish.jsp");

        doReturn(user).when(session).getAttribute("user");

        finishServlet.init(servletConfig);
        finishServlet.doGet(request,response);

        Mockito.verify(requestDispatcher,Mockito.times(1))
                .forward(request,response);

    }
}
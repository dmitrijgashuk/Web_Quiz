package org.dgashuk.web_game;

import org.dgashuk.web_game.model.User;
import org.dgashuk.web_game.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class FinishServletTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
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
    @Mock
    UserService userService;

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

        Mockito.verify(servletContext, times(1))
                .getAttribute("userService");
    }

    @Test
    void test_doGetMethod() throws ServletException, IOException {
        doReturn(session).when(request).getSession();
        doReturn(requestDispatcher).when(request).getRequestDispatcher("/WEB-INF/jsp/finish.jsp");

        doReturn(user).when(session).getAttribute("user");

        finishServlet.init(servletConfig);
        finishServlet.doGet(request, response);

        Mockito.verify(session, times(1)).getAttribute("user");

        Mockito.verify(user, times(1)).incrementGameCounter();

        Mockito.verify(user, times(1)).reset();

        Mockito.verify(userService, times(1)).updateUser(user);

        Mockito.verify(requestDispatcher, times(1))
                .forward(request, response);
    }

    /*@Test
    void test_check_user_get_update(){
        User testUser = User.builder()
                .id(1)
                .name("testUserName")
                .currentLocation("Enter")
                .build();

        Mockito.verify(userService, times(1)).updateUser(user);

    }*/

}
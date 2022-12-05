package org.dgashuk.web_game;

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
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationServletTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    ServletConfig servletConfig;
    @Mock
    ServletContext context;
    @Mock
    RequestDispatcher requestDispatcher;
    @Mock
    UserService userService;

    private AuthorizationServlet authorizationServlet;

    @BeforeEach
    void configTest() throws ServletException {
        doReturn(context).when(servletConfig).getServletContext();
        doReturn(userService).when(context).getAttribute("userService");
        doReturn(session).when(request).getSession();

        authorizationServlet = new AuthorizationServlet();
        authorizationServlet.init(servletConfig);
    }

    @Test
    void test_doPost_Return_ErrorMessage_When_NameOfUser_isBlank() throws ServletException, IOException {
        String errorMessage = "Имя игрока не должно быть пустым";

        doReturn("").when(request).getParameter(anyString());
        doReturn(requestDispatcher).when(request).getRequestDispatcher(eq("/WEB-INF/jsp/register.jsp"));

        authorizationServlet.doPost(request, response);


        verify(request, times(1))
                .setAttribute(eq("errorMessage"), eq(errorMessage));

        verify(requestDispatcher, times(1))
                .forward(request, response);

    }


}
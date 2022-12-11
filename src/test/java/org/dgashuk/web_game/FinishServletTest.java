package org.dgashuk.web_game;

import org.dgashuk.web_game.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class FinishServletTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    UserService userService;
    @Mock
    ServletConfig servletConfig;
    @Mock
    ServletContext servletContext;

    FinishServlet finishServlet;

    @BeforeEach
    void test_initialization() throws ServletException {
        finishServlet = new FinishServlet();
    }
    @Test
    void test_init_userServlet() throws ServletException {
        /*finishServlet.init(servletConfig);

        Mockito.verify(servletContext, Mockito.times(1))
                .getAttribute("userService");*/
    }

    @Test
    void doGet() {
    }
}
package org.dgashuk.web_game;

import org.dgashuk.web_game.model.*;
import org.dgashuk.web_game.service.DialogService;
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

@WebServlet(name = "dialogLogicServlet", value = "/dialog")
public class DialogServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(DialogServlet.class);
    private DialogService dialogService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        dialogService = (DialogService) servletContext.getAttribute("dialogService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        Npc currentNpc = (Npc) session.getAttribute("npc");
        if (currentNpc == null) {
            String currentNpcName = request.getParameter("npcName");
            currentNpc = dialogService.getCurrentNpc(currentNpcName);
            session.setAttribute("npc", currentNpc);
        }
        log.debug("Get current 'Npc' + " + currentNpc.getNpcName());

        String nextDialogId = request.getParameter("dialogId");
        Dialog currentDialog = dialogService.getNpcDialog(currentNpc,nextDialogId);
        log.debug("Get nextDialog from id parameter " + nextDialogId) ;

        String questId = request.getParameter("questId");
        dialogService.processingQuestDialog(user,currentNpc,questId);

        String endDialog = request.getParameter("andDialog");
        log.debug("jsp return parameter - endDialog: "+ endDialog );
        if (endDialog != null && endDialog.equals("exit")) {
            session.setAttribute("npc", null);
            response.sendRedirect("location");
            log.debug("if jsp return parameter - endDialog: " + endDialog + " method calls sendRedirect or forward to /start");
            log.debug("if block call 'return' ");
            return;
        }

        request.setAttribute("currentNpc", currentNpc);
        request.setAttribute("dialog", currentDialog);

        request.getRequestDispatcher("/WEB-INF/jsp/dialog.jsp")
                .forward(request, response);
        log.debug("return forward to /WEB-INF/jsp/dialog.jsp ");
    }

}

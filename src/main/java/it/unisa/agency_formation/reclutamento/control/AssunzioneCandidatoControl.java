package it.unisa.agency_formation.reclutamento.control;

import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.reclutamento.domain.Candidatura;
import it.unisa.agency_formation.reclutamento.domain.StatiCandidatura;
import it.unisa.agency_formation.reclutamento.manager.ReclutamentoManager;
import it.unisa.agency_formation.reclutamento.manager.ReclutamentoManagerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AssunzioneCandidatoControl")
public class AssunzioneCandidatoControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente user = (Utente) request.getSession().getAttribute("user");
        if (user == null || user.getRole() != RuoliUtenti.HR) {
            response.sendRedirect("./static/Login.html");
        } else {
            int idCandidato = Integer.parseInt(request.getParameter("idCandidato"));
            try {
                Candidatura candidatura = getCandidatura(idCandidato);
                if (candidatura == null) {
                    response.getWriter().write("1"); //errore Candidatura
                    response.sendRedirect("./static/Login.html");
                } else {
                    boolean esito = setStato(candidatura.getIdCandidatura());
                    if (esito) {
                        response.getWriter().write("2"); // assunzione
                    } else {
                        response.getWriter().write("3"); //errore assunzione
                        response.sendRedirect("./static/Error.html");
                        return;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/HomeHR.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    public static Candidatura getCandidatura(int idCandidato) throws SQLException {
        ReclutamentoManager reclutamentoManager = new ReclutamentoManagerImpl();
        return reclutamentoManager.getCandidaturaById(idCandidato);
    }
    public static boolean setStato(int idCandidatura) throws SQLException {
        ReclutamentoManager reclutamentoManager = new ReclutamentoManagerImpl();
       return reclutamentoManager.modificaStatoCandidatura(idCandidatura, StatiCandidatura.Assunzione);
    }
}
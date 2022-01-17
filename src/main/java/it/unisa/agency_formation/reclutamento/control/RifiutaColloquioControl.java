package it.unisa.agency_formation.reclutamento.control;

import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.reclutamento.domain.Candidatura;
import it.unisa.agency_formation.reclutamento.manager.ReclutamentoManager;
import it.unisa.agency_formation.reclutamento.manager.ReclutamentoManagerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static java.util.Objects.requireNonNull;

@WebServlet("/RifiutaColloquioControl")
public class RifiutaColloquioControl extends HttpServlet {
    private static final String path = "\\AgencyFormationFile\\Candidature\\";
    private static final String pathAbsolute = System.getProperty("user.home") + path;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente user = (Utente) request.getSession().getAttribute("user");
        if (user != null && user.getRole() == RuoliUtenti.HR) {
            int idCandidato = Integer.parseInt(request.getParameter("idCandidato"));
            try {
                Candidatura candidatura = getCandidaturaFromManager(idCandidato);
                File toDelete = new File(pathAbsolute + "IdUtente-" + candidatura.getIdCandidato());
                delete(toDelete);
                if (rejectCandidaturaFromManager(candidatura.getIdCandidatura(), user.getId())) {
                    response.getWriter().write("1"); // rifiuto avvenuto
                } else {
                    response.getWriter().write("2"); //rifiuto non avvenuto
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/HomeHR.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("./static/Login.html");
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public static Candidatura getCandidaturaFromManager(int idCandidato) throws SQLException {
        ReclutamentoManager reclutamentoManager = new ReclutamentoManagerImpl();
        return reclutamentoManager.getCandidaturaById(idCandidato);
    }

    public static boolean rejectCandidaturaFromManager(int idCandidatura, int idHR) throws SQLException {
        ReclutamentoManager reclutamentoManager = new ReclutamentoManagerImpl();
        return reclutamentoManager.rifiutaCandidatura(idCandidatura, idHR);
    }

    public static void delete(File file) {
        for (File subFile : requireNonNull(file.listFiles())) {
            if (subFile.isDirectory()) {
                delete(subFile);
            } else {
                subFile.delete();
            }
        }
        file.delete();
    }
}
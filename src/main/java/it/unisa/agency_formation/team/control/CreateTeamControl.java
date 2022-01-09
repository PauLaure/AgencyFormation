package it.unisa.agency_formation.team.control;

import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.team.domain.Team;
import it.unisa.agency_formation.team.manager.TeamManagerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/CreateTeamControl")
public class CreateTeamControl extends HttpServlet {
    private TeamManagerImpl teamManager = new TeamManagerImpl();
    //da raffinare
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Team team = new Team();
        RequestDispatcher dispatcher;
        String action = req.getParameter("action");
        Utente userTM = (Utente) req.getSession().getAttribute("user");
        int idTM = userTM.getId();
        try {
            if (action.equalsIgnoreCase("crea")) {
                String nomeProgetto = req.getParameter("lname");
                int numeroDipendenti = Integer.parseInt(req.getParameter("quantity"));
                if(numeroDipendenti > 8){
                    resp.sendRedirect("/static/CreaTeam.jsp");
                }
                String nomeTeam = req.getParameter("fname");
                String descrizione = req.getParameter("teamDescr");
                resp.getWriter().write("2");
                if (userTM.getRole() == RuoliUtenti.TM) {//ruolo dell'utente è uguale a 3 può salvare
                    team.setNomeProgetto(nomeProgetto);
                    team.setDescrizione(descrizione);
                    team.setNomeTeam(nomeTeam);
                    team.setNumeroDipendenti(numeroDipendenti);
                    teamManager.creaTeam(team,idTM);
                    int idTeam = teamManager.viewLastIdTeam();
                    req.setAttribute("idTeam",idTeam);
                    resp.getWriter().write("3");
                    dispatcher = req.getServletContext().getRequestDispatcher("/TeamControl");
                    dispatcher.forward(req, resp);
                }
                else{
                    resp.getWriter().write("4");
                    // Errore Non sei Tm
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
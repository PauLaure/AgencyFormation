package it.unisa.agency_formation.team.control;

import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManager;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManagerImpl;
import it.unisa.agency_formation.formazione.domain.Skill;
import it.unisa.agency_formation.formazione.manager.FormazioneManager;
import it.unisa.agency_formation.formazione.manager.FormazioneManagerImpl;
import it.unisa.agency_formation.team.domain.Team;
import it.unisa.agency_formation.team.manager.TeamManager;
import it.unisa.agency_formation.team.manager.TeamManagerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/ListaDipendentiControl")
public class ListaDipendentiControl extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Utente user = (Utente) req.getSession().getAttribute("user");
        if (user != null && user.getRole() == RuoliUtenti.TM) {
            RequestDispatcher dispatcher;
            /*visualizzo tutti i dipendenti*/
            try {
                ArrayList<Dipendente> dipendenti = getTuttiDipendentiFromManager();
                if (dipendenti != null && dipendenti.size() > 0) {
                    for (Dipendente dipendente : dipendenti) {
                        ArrayList<Skill> skills;
                        if (dipendente.getTeam() != null && dipendente.getTeam().getIdTeam() > 0) {
                            dipendente.setTeam(getTeamIdFromManager(dipendente.getTeam().getIdTeam()));
                        }
                        if (getSkillDipendenteFromManager(dipendente.getIdDipendente()) != null && getSkillDipendenteFromManager(dipendente.getIdDipendente()).size() > 0) {
                            skills = getSkillDipendenteFromManager(dipendente.getIdDipendente());
                            dipendente.setSkills(skills);
                        }
                    }
                    //else messo io
                req.setAttribute("dipendenti", dipendenti);
                resp.getWriter().write("2");
                dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/VisualizzaDipendenti.jsp");
                dispatcher.forward(req, resp);
                }
                else{
                    resp.getWriter().write("1");
                    resp.sendRedirect("./static/Error.html");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            resp.getWriter().write("3");
            resp.sendRedirect("./static/Login.html");
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public static Team getTeamIdFromManager(int idTeam) throws SQLException {
        TeamManager teamManager = new TeamManagerImpl();
        return teamManager.getTeamById(idTeam);
    }

    public static ArrayList<Skill> getSkillDipendenteFromManager(int idDip) throws SQLException {
        FormazioneManager formazioneManager = new FormazioneManagerImpl();
        return formazioneManager.recuperoSkillConIdDipendente(idDip);
    }
    public static ArrayList<Dipendente> getTuttiDipendentiFromManager() throws SQLException {
        AutenticazioneManager autenticazioneManager = new AutenticazioneManagerImpl();
        return autenticazioneManager.getTuttiDipendenti();
    }

}

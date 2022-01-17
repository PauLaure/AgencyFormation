package it.unisa.agency_formation.formazione.control;

import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManager;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManagerImpl;
import it.unisa.agency_formation.formazione.domain.Documento;
import it.unisa.agency_formation.formazione.manager.FormazioneManager;
import it.unisa.agency_formation.formazione.manager.FormazioneManagerImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

@WebServlet("/DownloadMaterialeControl")
public class DownloadMaterialeControl extends HttpServlet {

    private static final String directory = System.getProperty("user.home");



    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente user = (Utente) request.getSession().getAttribute("user");
        if (user != null && user.getRole() == RuoliUtenti.DIPENDENTE) {
            ServletContext context = request.getServletContext();
            Documento documento = null;
            try {
                Dipendente dipendente = getDipendentefromManager(user.getId());
                if (dipendente == null) {
                    response.getWriter().write("1"); //dipendente null
                } else {
                    documento = getDocumentofromManager(dipendente.getTeam().getIdTeam());
                    if (documento == null) {
                        response.getWriter().write("2"); //documento null
                        //si può rimandare all'homepage?
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (documento != null) {
                String pathMateriale = directory + documento.getMaterialeDiFormazione();
                File file = new File(pathMateriale);
                FileInputStream fileIn = new FileInputStream(file);
                String mimeType = context.getMimeType(pathMateriale);
                if (mimeType == null) {
                    mimeType = "application/octet-stream";
                }
                response.setContentType(mimeType);
                response.setContentLength((int) file.length());

                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
                response.setHeader(headerKey, headerValue);
                OutputStream outStream = response.getOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileIn.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
                fileIn.close();
                outStream.close();
                response.getWriter().write("3"); //documento scaricato
            } else {
                response.getWriter().write("4"); //documento non scaricato
            }
        } else {
            response.getWriter().write("5");
            response.sendRedirect("./static/Login.html");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    public static Dipendente getDipendentefromManager(int idUtente) throws SQLException {
        AutenticazioneManager autenticazioneManager = new AutenticazioneManagerImpl();
        return autenticazioneManager.getDipendente(idUtente);
    }
    public static Documento getDocumentofromManager(int idTeam) throws SQLException {
        FormazioneManager formazioneManager = new FormazioneManagerImpl();
        return formazioneManager.getMaterialeByIdTeam(idTeam);
    }
}
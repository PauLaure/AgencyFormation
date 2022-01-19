package it.unisa.agency_formation.autenticazione.manager;

import it.unisa.agency_formation.autenticazione.DAO.DipendenteDAO;
import it.unisa.agency_formation.autenticazione.DAO.UtenteDAO;
import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.autenticazione.domain.StatiDipendenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;

import java.sql.SQLException;
import java.util.ArrayList;

public class AutenticazioneManagerImpl implements AutenticazioneManager {


    /**
     * Questa funzionalità permette di far registrare un utente,l'utente non deve essere già registrato
     * @throws SQLException
     * @param user l'utente che verrà registrato
     * @return boolean true se l'utente viene registrato, false altrimenti
     * */
    @Override
    public boolean registrazione(Utente user) throws SQLException {
        if (!alreadyRegisteredUser(user)) {
            return UtenteDAO.salvaUtente(user);
        } else {
            return false;
        }
    }

    /**
     * Questa funzionalità permette di far eseguire il login ad un utente già registrato in precedenza
     * @param email dell'utente
     * @param password dell'utente
     * @return utente se il login va a buon fine, null altrimenti
     * */
    @Override
    public Utente login(String email, String password) throws SQLException {
        return UtenteDAO.login(email, password);
    }
    /**
     * Questa funzionalità permette di recuperare un dipendente
     * @param idDip, rappresenta l'id del dipendente
     * @throws SQLException
     * @return Dipendente se il dipendente esiste, null altrimenti
     * */
    @Override
    public Dipendente getDipendente(int idDip) throws SQLException {
        return DipendenteDAO.recuperoDipendenteById(idDip);
    }
    /**
     * Questa funzionalità permette di recuperare i candidati con la candidatura
     * @throws SQLException
     * @return ArrayList<Utente> se ci sono candidati con la candidatura, null altrimenti*/
    @Override
    public ArrayList<Utente> getCandidatiConCandidatura() throws SQLException {
        return UtenteDAO.doRetrieveCandidatoConCandidatura();
    }
    /**
     * Questa funzionalità permette di recuperare i dipendenti
     * @throws SQLException
     * @return ArrayList<Dipendente> se ci sono dipendenti, null altrimenti*/
    @Override
    public ArrayList<Dipendente> getTuttiDipendenti() throws SQLException {
        return DipendenteDAO.recuperaDipendenti();
    }

    /**
     * Questa funzionalità permette di assumere un candidato
     * @throws SQLException
     * @return boolean true se l'assunzione va a buon fine, false altrimenti
     * */

    @Override
    public boolean assumiCandidato(Dipendente dipendente) throws SQLException {
        DipendenteDAO.modificaRuoloUtente(dipendente.getIdDipendente());
        return (DipendenteDAO.salvaDipendente(dipendente));
    }

    /**
     * Questa funzionalità permette di recuperare i candidati che dovranno svolgere il colloquio
     * @return ArrayList<Utente> se ci sono candidati per il colloquio, null altrimenti
     * @throws SQLException
     */

    @Override
    public ArrayList<Utente> getCandidatiColloquio() throws SQLException {
        return UtenteDAO.recuperoCandidatiColloquio();
    }

    /**
     * Questa funzionalità permette di settare l'idTeam di un dipendente
     * @param idDip rappresenta l'id del dipendente
     * @param idTeam rappresenta l'id del team
     * @return boolean true se il set è andato a buon fine, false altrimenti
     * @throws SQLException
     */
    @Override
    public boolean setTeamDipendente(int idDip, int idTeam) throws SQLException {
        return DipendenteDAO.setIdTeamDipendente(idDip, idTeam);
    }

    private boolean alreadyRegisteredUser(Utente user) throws SQLException {
        if (UtenteDAO.login(user.getEmail(), user.getPwd()) == null) {
            return false;
        }
            return true;
    }
}

package it.unisa.agency_formation.formazione.manager;

import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.formazione.domain.Documento;
import it.unisa.agency_formation.formazione.domain.Skill;

import java.sql.SQLException;

public interface FormazioneManager {
    void creaCompetenza(int idTeam, String competenza)throws SQLException;
    boolean salvaDocumento(Documento documento) throws SQLException;
    String visualizzaCompetenza(int idTeam);

    void caricaDocumenti(String MaterialeDiFormazione);
    Documento getMaterialeByIdTeam(int idTeam) throws SQLException;
    //   Documento viewDocument(int idDocument);     Fare Entita Documento
    boolean aggiungiSkill(Skill skill) throws SQLException;

    void remuoviSkill(Skill skill);

    void visualizzaSkill(int idSkill);

    int getLastIdSkillCreated() throws SQLException;

    boolean addSkillDipendente(int idSkill, Dipendente dip) throws SQLException;

    Dipendente getIdDipendente(int idDip) throws  SQLException;
}

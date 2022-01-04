package it.unisa.agency_formation.reclutamento.manager;

import it.unisa.agency_formation.reclutamento.DAO.CandidaturaDAO;
import it.unisa.agency_formation.reclutamento.domain.Candidatura;

import java.sql.SQLException;

public class ReclutamentoManagerImpl implements ReclutamentoManager {


    @Override
    public boolean uploadCandidatureWithoutDocument(Candidatura candidatura) throws SQLException {
        if(candidatura == null){
            return false;
        }
        else if (!alreadyLoaded(candidatura.getIdCandidato())) {
            return CandidaturaDAO.doSaveCandidaturaWithoutDocument(candidatura);

        } else {
            return false;
        }
    }

    @Override
    public boolean uploadDocument(String document, int idUtente) throws SQLException {
        if (CandidaturaDAO.addDocument(document, idUtente)) {
            return true;
        }
        return false;
    }

    @Override
    public Candidatura getCandidaturaById(int idCandidato) throws SQLException {
        Candidatura result = CandidaturaDAO.doRetrieveById(idCandidato);
        if (result != null) {
            return result;
        } else {
            return null;
        }
    }

    @Override
    public boolean acceptCandidature(int idCandidatura) {
        //TODO
        return false;
    }

    @Override
    public boolean rejectCandidature(int idCandidatura) throws SQLException{
        if(CandidaturaDAO.doRejectCandidatura(idCandidatura)){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public boolean hiringCandidate(int idUtente) throws SQLException{
        //TODO
        return false;
    }
//DA CONTROLLARE
    @Override
    public boolean rejectCandidate(int idCandidatura) throws SQLException{
        if(CandidaturaDAO.doRemoveCandidatura(idCandidatura)==true){
            CandidaturaDAO.updateState(idCandidatura, "Rifiutato");
            return true;
        } else{
            return false;
        }
    }

    private boolean alreadyLoaded(int idUtente) throws SQLException {
        if (CandidaturaDAO.doRetrieveById(idUtente) == null) {
            return false;
        } else {
            return true;
        }

    }
}

package agency_formation.formazione.DAO;


import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.formazione.DAO.SkillDAO;
import it.unisa.agency_formation.formazione.domain.Skill;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Questa classe racchiude tutti i casi di test riguardante SkillDAO
 */
public class SkillDAOTest {
    //
    @Test
    public void saveSkillFailSkillNull() throws SQLException{
        Skill skill = null;
        assertFalse(SkillDAO.doSaveSkill(skill));
    }

    @Test
    public void saveSkillOK() throws SQLException{
        Skill skill = new Skill("C","Conoscenze base");
        assertTrue(SkillDAO.doSaveSkill(skill));
    }

    @Test
    public void removeSkillOK() throws SQLException{
        int idSkill= 2;
        assertTrue(SkillDAO.doRemoveSkill(2));
    }
    @Test
    public void removeSkillFail() throws SQLException{
        int idSkill= 0;
        assertFalse(SkillDAO.doRemoveSkill(0));
    }

    // Funziona con il db vuoto
    @Test
    public void doRetrieveAllFail() throws SQLException{
        assertNull(SkillDAO.doRetrieveAll());
    }
    @Test
    public void doRetrieveAllPass() throws SQLException{
        assertNotNull(SkillDAO.doRetrieveAll());
    }

    @Test
    public void doRetrieveByNameNull() throws SQLException{
        String nomeSkill = null;
        assertNull(SkillDAO.doRetrieveByName(nomeSkill));
    }
    @Test
    public void doRetrieveByNamePass() throws SQLException{
        String nomeSkill = "CSS";
        assertNotNull(SkillDAO.doRetrieveByName(nomeSkill));
    }

    @Test//not pass idSkill<1
    public void saveSkillDip1() throws SQLException {

    }
    @Test//not pass dip==null
    public void saveSkillDip2() throws SQLException {

    }
    @Test//not pass dip==null && idSkill<1
    public void saveSkillDip3() throws SQLException {

    }

    @Test//pass
    public void saveSkillDipPass() throws SQLException {

    }
    @Test
    public void RetrieveLastIdPass() throws  SQLException{
        assertNotNull(SkillDAO.doRetrieveLastId());
    }
    @Test//there aren't skill
    public void RetrieveLastIdFail() throws  SQLException{
        assertNotNull(SkillDAO.doRetrieveLastId());
    }
}

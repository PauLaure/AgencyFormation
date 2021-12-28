package agency_formation.autenticazione.DAO;

import it.unisa.agency_formation.autenticazione.DAO.UtenteDAO;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;

/*
* Questa classe racchiude tutti i casi di test riguardante UtenteDAO
*/
public class UtenteDAOTest {
    private UtenteDAO dao = new UtenteDAO();
    @Test
    public void saveUserFail() throws SQLException {
        Utente user = null;
        Assert.assertFalse(dao.doSaveUser(user));
    }
    @Test
    public void saveUserOK() throws SQLException {
        Utente user = new Utente("Gennaro","Cecco","genny@libero.it","lol",1);
        Assert.assertTrue(dao.doSaveUser(user));
    }

    @Test
    public void loginEmailNull() throws SQLException{
        String email = null;
        String pwd = "lol";
        Assert.assertNull(dao.login(email,pwd));
    }
    @Test
    public void loginPwdNull() throws SQLException{
        String email = "genny@libero.it";
        String pwd = null;
        Assert.assertNull(dao.login(email,pwd));
    }

    @Test
    //da rivedere
    public void loginPass() throws SQLException{
       Utente user = new Utente("Gennaro","Cecco","gennaro@gmail.com","lol",1);
        String email = "gennaro@gmail.com";
        String pwd = "lol";
        Utente result = dao.login(email,pwd);
        Assert.assertEquals(user.toString(),result.toString());
    }

    @Test
    public void loginFail() throws SQLException{
        String email = "genny@libero.it";
        String pwd = "lol";
        Assert.assertNull(dao.login(email,pwd));
    }

    @Test
    public void doRetrieveByIDLessZero()throws SQLException{
        int id = -1;
        Assert.assertNull(dao.doRetrieveByID(id));
    }

    @Test
    public void doRetrieveByIDPass()throws SQLException{
        int id = 5;
        Assert.assertNotNull(dao.doRetrieveByID(id));
    }

    @Test
    public void retrieveByRuoloLessZero() throws SQLException{
        int ruolo = -1;
        Assert.assertNull(dao.doRetrieveUserByRuolo(ruolo));
    }
    @Test
    public void retrieveByRuoloMoreFour() throws SQLException{
        int ruolo = 5;
        Assert.assertNull(dao.doRetrieveUserByRuolo(ruolo));
    }
    @Test
    public void retrieveByRuoloSizeLessOne()throws SQLException{
        ArrayList<Utente> utenti = new ArrayList<>();
        int ruolo = 1;
        UtenteDAO mock = Mockito.mock(UtenteDAO.class);
        Mockito.when(mock.doRetrieveUserByRuolo(ruolo)).thenReturn(utenti);
        Assert.assertEquals(0,mock.doRetrieveUserByRuolo(ruolo).size());

    }
    @Test
    public void retrieveByRuoloPass()throws SQLException{
        ArrayList<Utente> utenti = new ArrayList<>();
        Utente user1 = new Utente("Gennaro","Cecco","gennaro@gmail.com","lol",1);
        Utente user2 = new Utente("Manuel","Nocerino","manuel@gmail.com","lol",1);
        Utente user3 = new Utente("Domenico","Pagliuca","domenico@gmail.com","lol",1);
        int ruolo = 1;
        utenti.add(user1);
        utenti.add(user2);
        utenti.add(user3);
        UtenteDAO mock = Mockito.mock(UtenteDAO.class);
        Mockito.when(mock.doRetrieveUserByRuolo(ruolo)).thenReturn(utenti);
        Assert.assertEquals(3,mock.doRetrieveUserByRuolo(ruolo).size());

    }
}

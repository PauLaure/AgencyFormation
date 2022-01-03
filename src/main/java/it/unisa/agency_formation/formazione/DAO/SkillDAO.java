package it.unisa.agency_formation.formazione.DAO;

import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.formazione.domain.Skill;
import it.unisa.agency_formation.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SkillDAO {
    private static final String TABLE_SKILL = "skill";
    private static final String TABLE_SKILLDIPENDENTE = "skillsdipendenti";

    /**
     * Questa funzionalità permette di salvare una nuova skill
     *
     * return verifica
     * @param skill
     * @throws SQLException
     * @pre skill!=null && dip!=null
     */
    public static boolean doSaveSkill(Skill skill) throws SQLException {
        Connection connection = DatabaseManager.getInstance().getConnection();
        if (skill != null) {
            PreparedStatement save = null;

            String query = "insert into " + TABLE_SKILL + " (NomeSkill, DescrizioneSkill)" +
                    " values(?,?)";

            try {
                save = connection.prepareStatement(query);
                save.setString(1, skill.getNomeSkill());
                save.setString(2, skill.getDescrizioneSkill());
                save.executeUpdate();
                return true;
            } finally {
                try {
                    if (save != null)
                        save.close();

                } finally {
                    if (connection != null)
                        connection.close();
                }
            }
        }
        return false;
    }

    /**
     * Questa funzionalità permette di rimuovere una skill persa
     *
     * @param idSkill
     * @param idDipendente
     * @throws SQLException
     * @pre idSkill>1 && idDipendente>1
     */
    public static boolean doRemoveSkill(int idSkill, int idDipendente) throws SQLException {
        if(idSkill<1 || idDipendente<0){return false;}
        Connection connection = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = null;
        PreparedStatement stmt1 = null;
        ResultSet result1;
        ResultSet result2;
        String query = "DELETE FROM " + TABLE_SKILL + " WHERE IdSkill=?";
        String q2 = "DELETE FROM " + TABLE_SKILLDIPENDENTE + " WHERE IdSkill=? AND IdDipendente=?";
        try {
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, idSkill);
            result1=stmt.executeQuery();

            stmt1 = connection.prepareStatement(q2);
            stmt1.setInt(1, idSkill);
            stmt1.setInt(2, idDipendente);
            result2=stmt1.executeQuery();
            if(result1.next() && result2.next()){
                return true;
            }else{return false;}
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (stmt1 != null) stmt1.close();
            } finally {
                if (connection != null) connection.close();
            }
        }
    }

    /**
     * Questa funzionalità permette di recuperare tutte le skill
     *
     * @return arraylist di skill
     * @throws SQLException
     * @post skills.size()>0
     */
    public static ArrayList<Skill> doRetrieveAll() throws SQLException {
        Connection connection = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = null;
        String query = "Select From " + TABLE_SKILL;
        ArrayList<Skill> skills = new ArrayList<>();
        ResultSet result;
        try {
            stmt = connection.prepareStatement(query);
            result = stmt.executeQuery();
            while (result.next()) {
                Skill skill = new Skill();
                skill.setNomeSkill(result.getString("NomeSkill"));
                skill.setDescrizioneSkill(result.getString("DescrizioneSkill"));
                skills.add(skill);
            }
            if (skills.size() > 0) return skills;
            else return null;
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }
    }

    /**
     *
     * @param nomeSkill
     * @return skill
     * @throws SQLException
     * @pre nomeSKill!=null
     */
    public static Skill doRetrieveByName(String nomeSkill) throws SQLException {
        if(nomeSkill==null){
            return null;
        }
        Connection connection = DatabaseManager.getInstance().getConnection();
        ResultSet result;
        PreparedStatement stmt = null;
        String query = "Select * From " + TABLE_SKILL + " where NomeSkill=?";

        Skill skill = null;


        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, nomeSkill);
            result = stmt.executeQuery();
            if (result.next()) {
                skill = new Skill();
                skill.setNomeSkill(result.getString("NomeSkill"));
                skill.setDescrizioneSkill(result.getString("DescrizioneSkill"));
                skill.setIdSkill(result.getInt("IdSkill"));
            }
            if(skill!=null){
                return skill;
            }else{
                return null;
            }

        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }
    }

    /**
     *
     * @param idSkill
     * @param dip
     * @throws SQLException
     * @pre idSkill>0 && dip!=null
     */
  public static boolean doSaveSkillDip(int idSkill, Dipendente dip) throws SQLException {
        Connection connection = DatabaseManager.getInstance().getConnection();
        if(idSkill<1 || dip == null){
            return false;
        }
            PreparedStatement save = null;
            String query = "insert into " + TABLE_SKILLDIPENDENTE + " (idDipendente, idSkill, Livello) " +
                    " values(?,?,?)";
            try {
                save = connection.prepareStatement(query);
                save.setInt(1, dip.getIdDipendente());
                save.setInt(2, idSkill);
                save.setInt(3, 1);
                int result=save.executeUpdate();
                if(result!=-1){
                    return true;
                }else{
                    return false;
                }
            } finally {
                try {
                    if (save != null)
                        save.close();
                } finally {
                    if (connection != null)
                        connection.close();
                }
            }
        }


    /**
     *
     * @return ultima skill
     * @throws SQLException
     */
  public static int doRetrieveLastId() throws SQLException {
      Connection connection = DatabaseManager.getInstance().getConnection();
      ResultSet result;
      PreparedStatement stmt = null;
      String query = " SELECT max(IdSkill)  FROM " + TABLE_SKILL;
      int n = 0;
      try {
          stmt = connection.prepareStatement(query);
          result = stmt.executeQuery();
          if (result.next()) {
              n = result.getInt(1);
          }
          if(n>0){
              return n;
          }else{
              return -1;
          }

      } finally {
          try {
              if (stmt != null)
                  stmt.close();
          } finally {
              if (connection != null)
                  connection.close();
          }
      }
  }

}
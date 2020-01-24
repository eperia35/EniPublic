package fr.eni.parking.dao.Implementation;

import fr.eni.parking.ExceptionPerso.ExceptionDao;
import fr.eni.parking.bo.Formateur;
import fr.eni.parking.bo.Voiture;
import fr.eni.parking.dao.Connexion.Connexion;
import fr.eni.parking.dao.Interface.IRepository;
import javafx.collections.ObservableList;
import logger.LoggerPerso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RepositoryVoiture implements IRepository<Voiture> {

    //region propriété
    private static Logger LOGGER = LoggerPerso.getLogger("VoitureDao");

    protected static String RQT_SELECT_ALL = "SELECT * FROM Voitures" ;
    protected static String RQT_SELECT_BY_ID = "SELECT * FROM Voitures  WHERE [id] = ?";

    protected  static String RQT_UPDATE = "UPDATE [dbo].[Voitures]\n" +
            "   SET [Nom] = ?" +
            "       ,[Pi] = ? \n" +
            "      ,[IdFormateur] = ? \n" +
            " WHERE Id = ?";

    protected  static String RQT_INSERT ="INSERT INTO [dbo].[Voitures]\n" +
            "           ([Nom]\n" +
            "           ,[Pi]\n" +
            "           ,[IdFormateur])\n" +
            "     VALUES\n" +
            "           (? \n" +
            "           ,? \n" +
            "           ,?)";
    protected static String RQT_DELETE="DELETE FROM [dbo].[Voitures]\n" +
            "      WHERE [Id] = ?";

    //endregion

    //region fonction
    /**
     * retourne la voiture lié à l'id fournis
     * @param id
     * @return
     * @throws ExceptionDao
     */
    @Override
    public Voiture getById(int id)throws ExceptionDao {
        Voiture voiture = null;
        try (Connection cnx = Connexion.getConnection() ){

            PreparedStatement requete = cnx.prepareStatement(this.RQT_SELECT_BY_ID);
            requete.setInt(1,id);

            ResultSet rs = requete.executeQuery();
            if (rs.next()){
                voiture  = itemBuilder(rs);

            }else {
                LOGGER.severe("getById(int id) : Aucun enregistrement trouvé id = " + id);
                throw  new ExceptionDao("Aucun enregistrement trouvé id = " + id );
            }


        } catch (Exception e) {
            LOGGER.severe("Erreur dans getById(int id) : " + e.getMessage());
            throw new ExceptionDao(e.getMessage(),e);
        }

        return voiture;
    }

    /**
     * retourne toutes les voitures de la base de donnée
     * @return
     * @throws ExceptionDao
     */
    @Override
    public List<Voiture> getAll()throws ExceptionDao {

        List<Voiture> voitures = new ArrayList<Voiture>();
        try (Connection cnx = Connexion.getConnection()) {

            PreparedStatement requete = cnx.prepareStatement(this.RQT_SELECT_ALL);

            ResultSet rs = requete.executeQuery();

            while (rs.next()) {
                voitures.add(itemBuilder(rs));
            }
        } catch (Exception e) {
            LOGGER.severe("Erreur dans getById(int id) : " + e.getMessage());
            throw new ExceptionDao(e.getMessage(), e);
        }
        return voitures;
    }

    /**
     * Mets à jour la voiture donnée en paramètre
     * @param voiture
     * @throws ExceptionDao
     */
    @Override
    public void update(Voiture voiture) throws ExceptionDao {
        try (Connection cnx = Connexion.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(RQT_UPDATE);
            requete.setString(1,voiture.getNom());
            requete.setString(2,voiture.getPi());
            if (voiture.getFormateur().getId() !=0)
                requete.setInt(3, voiture.getFormateur().getId());
            else
                requete.setNull(3, Types.INTEGER);
            requete.setInt(4, voiture.getId());

            requete.executeUpdate();

        }catch(Exception ex)
        {
            LOGGER.severe("Erreur dans Update(Voiture voiture) : " + ex.getMessage());
            throw new ExceptionDao(ex.getMessage(),ex);
        }
    }

    /**
     * supprime la voiture lié a l'id fournis en paramètre
     * @param id
     * @throws ExceptionDao
     */
    @Override
    public void delete(int id) throws ExceptionDao {
        try (Connection cnx = Connexion.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(RQT_DELETE);
            requete.setInt(1,id);
            requete.executeUpdate();

        }catch(Exception ex)
        {
            LOGGER.severe("Erreur dans Delete(int id) : " + ex.getMessage());
            throw new ExceptionDao(ex.getMessage(),ex,1);
        }
    }

    /**
     * insert la voiture donnée en paramètre
     * @param voiture
     * @return
     * @throws ExceptionDao
     */
    @Override
    public int insert(Voiture voiture) throws ExceptionDao {
        int idVoiture = 0;
        try (Connection cnx = Connexion.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(RQT_INSERT, Statement.RETURN_GENERATED_KEYS);

            requete.setString(1,voiture.getNom());
            requete.setString(2,voiture.getPi());
            if (voiture.getFormateur().getId() !=0)
                requete.setInt(3, voiture.getFormateur().getId());
            else
                requete.setNull(3, Types.INTEGER);

            requete.executeUpdate();

            try (ResultSet generatedKeys = requete.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idVoiture = generatedKeys.getInt(1);
                }
                else {
                    LOGGER.severe("Erreur dans insert(Voiture voiture) : Aucun Id retourné");
                    throw new ExceptionDao("Aucun Id retourné.");
                }
            }
        }catch(Exception ex)
        {
            LOGGER.severe("Erreur dans insert(Voiture voiture) : " + ex.getMessage());
            throw new ExceptionDao(ex.getMessage(),ex);
        }
        return idVoiture;
    }

    /**
     * récupère les données d'un formateur selon la query fournis
     * @param rs
     * @return
     * @throws ExceptionDao
     */
    private static Voiture itemBuilder(ResultSet rs) throws ExceptionDao {
        try {

            return new Voiture(rs.getInt("Id"),rs.getString("Nom").trim(),rs.getString("Pi").trim(), new Formateur(rs.getInt("IdFormateur")));

        } catch (SQLException e) {
            LOGGER.severe("Erreur dans itemBuilder(ResultSet rs) : " + e.getMessage());
            throw new ExceptionDao(e.getMessage(),e);
        }
    }
    //endregion


}

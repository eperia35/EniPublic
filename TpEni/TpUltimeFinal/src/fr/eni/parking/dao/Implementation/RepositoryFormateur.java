package fr.eni.parking.dao.Implementation;

import fr.eni.parking.ExceptionPerso.ExceptionDao;
import fr.eni.parking.bo.Formateur;
import fr.eni.parking.dao.Connexion.Connexion;
import fr.eni.parking.dao.Interface.IRepository;
import logger.LoggerPerso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RepositoryFormateur implements IRepository<Formateur> {

    //region propriété
    private static Logger LOGGER = LoggerPerso.getLogger("FormateurDao");

    protected static String RQT_SELECT_ALL = "SELECT * FROM Formateurs" ;
    protected static String RQT_SELECT_BY_ID = "SELECT * FROM Formateurs  WHERE [id] = ?";
    protected static String RQT_UPDATE = "UPDATE [dbo].[Formateurs]\n" +
            "   SET [Nom] = ?" +
            "       ,[Prenom] = ? \n" +
            " WHERE Id = ?";

    protected static String RQT_INSERT ="INSERT INTO [dbo].[Formateurs]\n" +
            "           ([Nom]\n" +
            "           ,[Prenom])\n" +
            "     VALUES\n" +
            "           (? \n" +
            "           ,? )\n";

    protected static String RQT_DELETE="DELETE FROM [dbo].[Formateurs]\n" +
            "      WHERE [Id] = ?";

    //endregion

    //region function
    /**
     * retourne le formateur lié à l'id fournis
     * @param id
     * @return
     * @throws ExceptionDao
     */
    @Override
    public Formateur getById(int id)throws ExceptionDao {
        Formateur formateur = null;
        try (Connection cnx = Connexion.getConnection() ){

            PreparedStatement requete = cnx.prepareStatement(this.RQT_SELECT_BY_ID);
            requete.setInt(1,id);

            ResultSet rs = requete.executeQuery();
            if (rs.next()){
                formateur  = itemBuilder(rs);
            }else {
                LOGGER.severe("Erreur dans getById(int id) : Aucun enregistrement a été trouvé avec l'id = " + id);
                throw  new ExceptionDao("Aucun enregistrement trouvé id = " + id );
            }


        } catch (Exception e) {
            LOGGER.severe("Erreur dans getById(int id) : " + e.getMessage());
            throw new ExceptionDao(e.getMessage(),e);
        }

        return formateur;
    }

    /**
     * retourne tous les Fromateurs de la base de donnée
     * @return
     * @throws ExceptionDao
     */
    @Override
    public List<Formateur> getAll()throws ExceptionDao {

        List<Formateur> formateurs  = new ArrayList<Formateur>();
        try (Connection cnx = Connexion.getConnection()) {

            PreparedStatement requete = cnx.prepareStatement(this.RQT_SELECT_ALL);

            ResultSet rs = requete.executeQuery();

            while (rs.next()) {
                formateurs.add(itemBuilder(rs));
            }
        } catch (Exception e) {
            LOGGER.severe("Erreur dans getAll() : " + e.getMessage());
            throw new ExceptionDao(e.getMessage(), e);
        }
        return formateurs;
    }

    /**
     * Mets à jour le formateur donné en paramètre
     * @param formateur
     * @throws ExceptionDao
     */
    @Override
    public void update(Formateur formateur) throws ExceptionDao {
        try (Connection cnx = Connexion.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(RQT_UPDATE);
            requete.setString(1,formateur.getNom());
            requete.setString(2,formateur.getPrenom());
            requete.setInt(3, formateur.getId());
            requete.executeUpdate();

        }catch(Exception ex)
        {
            LOGGER.severe("Erreur dans Update(Formateur formateur) : " + ex.getMessage());
            throw  new ExceptionDao(ex.getMessage(),ex);
        }
    }

    /**
     * supprime le formateur lié a l'id fournis en paramètre
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
            throw  new ExceptionDao(ex.getMessage(),ex,1);
        }
    }

    /**
     * insert le formateur donnée en paramètre
     * @param formateur
     * @return
     * @throws ExceptionDao
     */
    @Override
    public int insert(Formateur formateur) throws ExceptionDao {
        int idFormateur = 0;
        try (Connection cnx = Connexion.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(RQT_INSERT, Statement.RETURN_GENERATED_KEYS);

            requete.setString(1,formateur.getNom());
            requete.setString(2,formateur.getPrenom());

            requete.executeUpdate();

            try (ResultSet generatedKeys = requete.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idFormateur = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("No ID obtained.");
                }
            }
        }catch(Exception ex)
        {
            LOGGER.severe("Erreur dans insert(Voiture voiture) : " + ex.getMessage());
            throw new ExceptionDao(ex.getMessage(),ex);
        }
        return idFormateur;
    }

    /**
     * récupère les données d'un formateur selon la query fournis
     * @param rs
     * @return
     * @throws ExceptionDao
     */
    private static Formateur itemBuilder(ResultSet rs) throws SQLException {
        return new Formateur(rs.getInt("Id"),rs.getString("Nom"),rs.getString("Prenom"));
    }
    //endregion

}

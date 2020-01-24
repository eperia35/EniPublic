package fr.eni.parking.bll.manager;

import fr.eni.parking.ExceptionPerso.ExceptionBll;
import fr.eni.parking.ExceptionPerso.ExceptionDao;
import fr.eni.parking.bo.Formateur;
import fr.eni.parking.bo.Voiture;
import fr.eni.parking.dao.Implementation.DaoFactory;
import fr.eni.parking.dao.Interface.IRepository;
import fr.eni.parking.service.ExportationPerso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logger.LoggerPerso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FormateurManager {
    //region propriété
    private static FormateurManager FORMATEUR_MANAGER = null;
    private static Logger LOGGER = LoggerPerso.getLogger("FormateurManager");

    private IRepository<Formateur> repositoryFormateur = DaoFactory.getFormateurDao();
    private ObservableList<Formateur> formateurs = FXCollections.observableArrayList();

    //endregion

    //region Constructeur
    private FormateurManager() throws ExceptionBll {
        this.formateurs.addAll(this.getFormateurs());
    }

    /**
     * Créer une instance de FormateurManager si aucune instance existe
     * et la retourne
     * @return
     */
    public static FormateurManager getInstance() throws ExceptionBll {
        if (FORMATEUR_MANAGER == null)
            FORMATEUR_MANAGER = new FormateurManager();
        return  FORMATEUR_MANAGER;
    }

    //endregion

    //region fonction
    /**
     * retourne la liste des formateurs
     * @return
     */
    public ObservableList<Formateur> getListFormateurs() {
        return formateurs;
    }

    /**
     * retourne une List de formateurs
     * @return
     */
    public List<Formateur> getFormateurs() throws ExceptionBll {
        List<Formateur> formateurs = new ArrayList<>();
        try {
            formateurs = repositoryFormateur.getAll();
        } catch (ExceptionDao exceptionDao) {
            LOGGER.severe("getFormateur() :  "+exceptionDao.getMessage());
            throw new ExceptionBll(exceptionDao.getMessage(),exceptionDao);
        }
        return formateurs;
    }

    /**
     * retourne le formateur correspondant a l'id fournis
     * @return
     */
    public Formateur getFormateur(int id) throws ExceptionBll {
        Formateur formateur = null;
        try {
            formateur = repositoryFormateur.getById(id);
        } catch (ExceptionDao exceptionDao) {
            LOGGER.severe("getFormateur(int id) : "+exceptionDao.getMessage());
            throw new ExceptionBll(exceptionDao.getMessage(),exceptionDao);
        }
        return formateur;
    }

    /**
     * Mets à jour le formateur
     * @param formateur
     */
    public void update(Formateur formateur,String nom,String prenom) throws ExceptionBll {
        Formateur formateurUpdate = formateur;
        formateurUpdate.setNom(nom);
        formateurUpdate.setPrenom(prenom);
        try {
            controleItems(formateurUpdate);
            repositoryFormateur.update(formateurUpdate);
            this.formateurs.set(this.formateurs.indexOf(formateur),formateurUpdate);
        } catch (ExceptionDao exceptionDao) {
            LOGGER.severe("update(Formateur formateur) : " + exceptionDao.getMessage());
            throw  new ExceptionBll(exceptionDao.getMessage(),exceptionDao);
        }
    }

    /**
     * supprime le formateur avec l'id correspondant sur le stockage instancié
     * @param formateur
     */
    public void delete(Formateur formateur) throws ExceptionBll {
        try {
            repositoryFormateur.delete(formateur.getId());
            this.formateurs.remove(formateur);
        } catch (ExceptionDao exceptionDao) {
            LOGGER.severe("delete(int id) : " + exceptionDao.getMessage());
            throw new ExceptionBll(exceptionDao.getMessage(),exceptionDao);

        }
    }

    /**
     * Insert le formateur donné en paramètre
     * @param formateur
     */
    public void insert(Formateur formateur) throws ExceptionBll {
        Formateur formateurInsert = formateur;
        try {
            controleItems(formateurInsert);
            int idFormateur = repositoryFormateur.insert(formateurInsert);
            formateurInsert.setId(idFormateur);
            this.formateurs.add(formateurInsert);
        } catch (ExceptionDao exceptionDao) {
            LOGGER.severe("insert(Formateur formateur) : " + exceptionDao.getMessage());
            throw new ExceptionBll(exceptionDao.getMessage(),exceptionDao);
        }
    }

    /**
     * Sérialise la liste des formateurs
     */
    public void serialiseur() throws ExceptionBll {
        try {
            ExportationPerso.encodeToFileXml(formateurs.toArray(), "formateur.xml");
        } catch (IOException e) {
            LOGGER.severe("serialiseur() : " + e.getMessage());
            throw new ExceptionBll(e.getMessage(),e);
        }
    }

    /**
     * Vérifie si les champ son vide
      * @param formateur
     * @throws ExceptionBll
     */
    private void controleItems(Formateur formateur) throws ExceptionBll {
        if (formateur.getNom().trim().equals("") || formateur.getPrenom().trim().equals("")){
            throw new ExceptionBll("un des champs est vide");
        }
    //endregion




    }


}

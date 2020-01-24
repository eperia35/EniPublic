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

public class VoitureManager {

    //region propriété

    private static VoitureManager VOITURE_MANAGER = null;
    private static Logger LOGGER = LoggerPerso.getLogger("VoitureManager");

    private IRepository<Voiture> repositoryVoiture = DaoFactory.getVoitureDao();
    private IRepository<Formateur> repositoryFormateur = DaoFactory.getFormateurDao();
    private ObservableList<Voiture> voitures = FXCollections.observableArrayList();

    //endregion

    //region constructeur

    private VoitureManager() throws ExceptionBll {
        this.voitures.addAll(this.getVoitures());
    }

    /**
     * Créer une instance de VoitureManager si aucune instance existe
     * et la retourne
     * @return
     */
    public static VoitureManager getInstance() throws ExceptionBll {
        if (VOITURE_MANAGER == null)
            VOITURE_MANAGER = new VoitureManager();
        return  VOITURE_MANAGER;
    }

    //endregion

    //region fonction

    /**
     * Retourne la liste de voiture
     * @return
     */
    public ObservableList<Voiture> getListVoitures() {
        return voitures;
    }

    /**
     * retourne une List de voitures
     * @return
     */
    public List<Voiture> getVoitures() throws ExceptionBll {
        List<Voiture> voitures = new ArrayList();
        try {
            voitures = repositoryVoiture.getAll();
            for (Voiture voiture : voitures ) {
                if (voiture.getFormateur().getId() != 0)
                    voiture.setFormateur(repositoryFormateur.getById(voiture.getFormateur().getId()));
            }
        } catch (ExceptionDao exceptionDao) {
            LOGGER.severe("getVoitures() : " + exceptionDao.getMessage());
            throw new ExceptionBll(exceptionDao.getMessage(), exceptionDao);
        }
        return voitures;
    }

    /**
     * retourne la voiture correspondant a l'id fournis
     * @return
     */
    public Voiture getVoiture(int id) throws ExceptionBll {
        Voiture voiture = null;
        try {
            voiture = repositoryVoiture.getById(id);
            voiture.setFormateur(repositoryFormateur.getById(voiture.getFormateur().getId()));
        } catch (ExceptionDao exceptionDao) {
            LOGGER.severe("getVoiture(int id)" +exceptionDao.getMessage() );
            throw new ExceptionBll(exceptionDao.getMessage(),exceptionDao);
        }
        return voiture;
    }

    /**
     * Mets à jour la voiture
     * @param voiture
     */
    public void update(Voiture voiture,String nom,String pi,Formateur formateur) throws ExceptionBll {
        Voiture voitureUpdate = voiture;
        voitureUpdate.setNom(nom);
        voitureUpdate.setPi(pi);
        voitureUpdate.setFormateur(formateur);
        try {
            controleItems(voitureUpdate);
            repositoryVoiture.update(voitureUpdate);
            this.voitures.set(this.voitures.indexOf(voiture),voitureUpdate);
        } catch (ExceptionDao exceptionDao) {
            LOGGER.severe("update(Voiture voiture) : " + exceptionDao.getMessage());
            throw new ExceptionBll(exceptionDao.getMessage(),exceptionDao);
        }
    }

    /**
     * supprime la voiture avec l'id correspondant sur le stockage instancié
     * @param voiture
     */
    public void delete(Voiture voiture) throws ExceptionBll {
        try {
            repositoryVoiture.delete(voiture.getId());
            this.voitures.remove(voiture);
        } catch (ExceptionDao exceptionDao) {
            LOGGER.severe("delete(int id) : " + exceptionDao.getMessage());
            throw new ExceptionBll(exceptionDao.getMessage(),exceptionDao);
        }
    }

    /**
     * Insert la voiture donnée en paramètre
     * @param voiture
     * @throws ExceptionBll
     */
    public void insert(Voiture voiture) throws ExceptionBll {
        Voiture voitureInsert = voiture;
        try {
            controleItems(voitureInsert);
            int idVoiture = repositoryVoiture.insert(voitureInsert);
            voitureInsert.setId(idVoiture);
            this.voitures.add(voitureInsert);
        } catch (ExceptionDao exceptionDao) {
            LOGGER.severe("insert(Voiture voiture) : " + exceptionDao.getMessage());
            throw new ExceptionBll(exceptionDao.getMessage(),exceptionDao);
        }
    }

    /**
     *  Sérialise la liste des voitures
     */
    public void serialiseur() throws ExceptionBll {
        try {
            ExportationPerso.encodeToFileXml(voitures.toArray(), "voitures.xml");

        } catch (IOException e) {
            LOGGER.severe("serialiseur() : " + e.getMessage());
            throw new ExceptionBll(e.getMessage(),e);
        }
    }

    /**
     * rafraichie le tableau Voiture
     */
    public void refresh() throws ExceptionBll {

        this.voitures.removeAll(this.voitures);
       this.voitures.addAll(this.getVoitures());
    }

    /**
     * Vérifie si un des champs est vide
     * @param voiture
     * @throws ExceptionBll
     */
    private void controleItems(Voiture voiture) throws ExceptionBll {
        if (voiture.getNom().trim().equals("") || voiture.getPi().trim().equals("")) {
            throw new ExceptionBll("un des champs est vide");
        }
    }
    //endregion



}

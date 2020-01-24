package fr.eni.parking.dao.Implementation;

import fr.eni.parking.dao.Interface.IRepository;

public class DaoFactory {

    /**
     * retourne le répository formateur
     * @return
     */
    public static IRepository getFormateurDao(){

        return new RepositoryFormateur();
    }

    /**
     * retourne le répository voiture
     * @return
     */
    public static IRepository getVoitureDao(){

        return new RepositoryVoiture();
    }
}

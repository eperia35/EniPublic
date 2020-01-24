package fr.eni.parking.dao.Implementation.Test;

import com.sun.deploy.security.SelectableSecurityManager;
import fr.eni.parking.ExceptionPerso.ExceptionDao;
import fr.eni.parking.bo.Formateur;
import fr.eni.parking.bo.Voiture;
import fr.eni.parking.dao.Implementation.DaoFactory;
import fr.eni.parking.dao.Implementation.RepositoryVoiture;
import fr.eni.parking.dao.Interface.IRepository;
import org.junit.jupiter.api.*;

import java.text.Format;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class  RepositoryVoitureTest {
    static IRepository<Voiture> voitureRepository;
    static IRepository<Formateur> formateurRepository;
    static List<Voiture> voitures = new ArrayList<Voiture>();
    @BeforeAll
    static void setUp() {
        voitureRepository = DaoFactory.getVoitureDao();
        formateurRepository = DaoFactory.getFormateurDao();
        Formateur formateur1 =new Formateur("michelle","huer");
        Formateur formateur2 = new Formateur("testNom","TestPrenom");
        try {
            formateur1.setId(formateurRepository.insert(formateur1));
            formateur2.setId(formateurRepository.insert(formateur2));

            voitures.add(new Voiture("Renault","AVI053",formateur1));
            voitures.add(new Voiture("Renault","AVI053",formateur2));

            voitures.get(0).setId(voitureRepository.insert(voitures.get(0)));

        } catch (ExceptionDao exceptionDao) {
            fail(exceptionDao.getMessage());
        }
    }

    @AfterAll
    static void  tearDown() {
        for (Voiture voiture: voitures) {

            try {
                formateurRepository.delete(voiture.getFormateur().getId());
                voitureRepository.delete(voiture.getId());
            } catch (ExceptionDao exceptionDao) {
                fail(exceptionDao.getMessage());
            }


        }
    }

    @Test
    void testGetById() {
        try {
            Voiture voitureInsert = voitureRepository.getById(voitures.get(0).getId());
            voitureInsert.setFormateur(formateurRepository.getById(voitureInsert.getFormateur().getId()));
          assertEquals(voitureInsert, voitures.get(0));  voitureRepository.getById(voitures.get(0).getId());
        } catch (ExceptionDao exceptionDao) {
            fail(exceptionDao.getMessage());
        }
    }

    @Test
    void testGetAll() {
        try {
            assertTrue(voitureRepository.getAll().size() > 0);
        } catch (ExceptionDao exceptionDao) {
            fail(exceptionDao.getMessage());
        }
    }

    @Test
    void testUpdate() {
        try {
            Voiture ancienneValeur = voitures.get(0);
            voitures.get(0).setNom("ChangeUpdate");
            voitureRepository.update(voitures.get(0));
            assertEquals( voitureRepository.getById(voitures.get(0).getId()), voitures.get(0));
            assertNotEquals(voitureRepository.getById(voitures.get(0).getId()), ancienneValeur);
            voitureRepository.getById(voitures.get(0).getId());
        } catch (ExceptionDao exceptionDao) {
            fail(exceptionDao.getMessage());
        }
    }

    @Test
    void testDelete() {
        try {
            voitureRepository.delete(voitures.get(0).getId());

        } catch (ExceptionDao exceptionDao) {
                assertEquals(exceptionDao.getId(),1);
        }
    }

    @Test
    void testInsert() {
        try {
            voitures.get(1).setId(voitureRepository.insert(voitures.get(1)));
            Voiture voitureInsert = voitureRepository.getById(voitures.get(1).getId());
            voitureInsert.setFormateur(formateurRepository.getById(voitureInsert.getFormateur().getId()));

            assertEquals(voitureInsert, voitures.get(1));
        } catch (ExceptionDao exceptionDao) {
            fail(exceptionDao.getMessage());
        }
    }
}
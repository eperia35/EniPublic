package fr.eni.parking.dao.Implementation.Test;

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

class  RepositoryFormateurTest {
    static IRepository<Formateur> formateurRepository;
    static List<Formateur> formateurs = new ArrayList<Formateur>();
    @BeforeAll
    static void setUp() {
        formateurRepository = DaoFactory.getFormateurDao();
        formateurs.add(new Formateur("michelle","huer"));
        formateurs.add(new Formateur("testNomInsert","TestPrenomInsert"));
        formateurs.add(new Formateur("testNomDelete","TestPrenomDelete"));
        try {
            formateurs.get(0).setId(formateurRepository.insert(formateurs.get(0)));

            formateurs.get(2).setId(formateurRepository.insert(formateurs.get(2)));
        } catch (ExceptionDao exceptionDao) {
            fail(exceptionDao.getMessage());
        }
    }

    @AfterAll
    static void  tearDown() {
        for (Formateur formateur: formateurs) {

            try {
                formateurRepository.delete(formateur.getId());
            } catch (ExceptionDao exceptionDao) {
                fail(exceptionDao.getMessage());
            }


        }
    }

    @Test
    void testGetById() {
        try {
            Formateur FormateurInsert = formateurRepository.getById(formateurs.get(0).getId());
            assertEquals(FormateurInsert, formateurs.get(0));
        } catch (ExceptionDao exceptionDao) {
            fail(exceptionDao.getMessage());
        }
    }

    @Test
    void testGetAll() {
        try {
            assertTrue(formateurRepository.getAll().size() > 0);
        } catch (ExceptionDao exceptionDao) {
            fail(exceptionDao.getMessage());
        }
    }

    @Test
    void testUpdate() {
        try {
            Formateur ancienneValeur = formateurs.get(0);

            formateurs.get(0).setNom("ChangeUpdate");
            formateurRepository.update(formateurs.get(0));

            assertEquals( formateurRepository.getById(formateurs.get(0).getId()), formateurs.get(0));
           // assertNotEquals(formateurRepository.getById(formateurs.get(0).getId()), ancienneValeur);

        } catch (ExceptionDao exceptionDao) {
            fail(exceptionDao.getMessage());
        }
    }

    @Test
    void testDelete() {
        try {
            formateurRepository.delete(formateurs.get(2).getId());

        } catch (ExceptionDao exceptionDao) {
            assertEquals(exceptionDao.getId(),1);
        }
    }

    @Test
    void testInsert() {
        try {
            formateurs.get(1).setId(formateurRepository.insert(formateurs.get(1)));
            Formateur formateurInsert = formateurRepository.getById(formateurs.get(1).getId());

            assertEquals(formateurInsert, formateurs.get(1));
        } catch (ExceptionDao exceptionDao) {
            fail(exceptionDao.getMessage());
        }
    }
}
package fr.eni.parking.dao.Interface;

import fr.eni.parking.ExceptionPerso.ExceptionDao;

import java.util.List;


public interface IRepository<T> {

    T getById (int id) throws ExceptionDao;
    List<T> getAll() throws ExceptionDao;
    void update(T t) throws ExceptionDao;
    void delete(int id) throws ExceptionDao;
    int insert(T t) throws ExceptionDao;
}

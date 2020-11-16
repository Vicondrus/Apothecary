package com.ds.project.apothecary.services;

import java.util.List;

/**
 * The interface Crud service.
 *
 * @param <T> the type parameter
 */
public interface CrudService<T> {

    /**
     * Create t.
     *
     * @param t the t
     * @return the t
     */
    T create(T t);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<T> findAll();

    /**
     * Find by id t.
     *
     * @param id the id
     * @return the t
     */
    T findById(Long id);

    /**
     * Update t.
     *
     * @param id the id
     * @param t  the t
     * @return the t
     */
    T update(Long id,
             T t);

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    Boolean delete(Long id);
}

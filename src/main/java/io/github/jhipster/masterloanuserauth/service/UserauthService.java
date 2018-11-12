package io.github.jhipster.masterloanuserauth.service;

import io.github.jhipster.masterloanuserauth.domain.Userauth;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Userauth.
 */
public interface UserauthService {

    /**
     * Save a userauth.
     *
     * @param userauth the entity to save
     * @return the persisted entity
     */
    Userauth save(Userauth userauth);

    /**
     * Get all the userauths.
     *
     * @return the list of entities
     */
    List<Userauth> findAll();


    /**
     * Get the "id" userauth.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Userauth> findOne(Long id);

    /**
     * Delete the "id" userauth.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the userauth corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Userauth> search(String query);
}

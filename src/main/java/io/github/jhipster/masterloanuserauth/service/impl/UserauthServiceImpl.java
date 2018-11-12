package io.github.jhipster.masterloanuserauth.service.impl;

import io.github.jhipster.masterloanuserauth.service.UserauthService;
import io.github.jhipster.masterloanuserauth.domain.Userauth;
import io.github.jhipster.masterloanuserauth.repository.UserauthRepository;
import io.github.jhipster.masterloanuserauth.repository.search.UserauthSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Userauth.
 */
@Service
@Transactional
public class UserauthServiceImpl implements UserauthService {

    private final Logger log = LoggerFactory.getLogger(UserauthServiceImpl.class);

    private final UserauthRepository userauthRepository;

    private final UserauthSearchRepository userauthSearchRepository;

    public UserauthServiceImpl(UserauthRepository userauthRepository, UserauthSearchRepository userauthSearchRepository) {
        this.userauthRepository = userauthRepository;
        this.userauthSearchRepository = userauthSearchRepository;
    }

    /**
     * Save a userauth.
     *
     * @param userauth the entity to save
     * @return the persisted entity
     */
    @Override
    public Userauth save(Userauth userauth) {
        log.debug("Request to save Userauth : {}", userauth);
        Userauth result = userauthRepository.save(userauth);
        userauthSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the userauths.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Userauth> findAll() {
        log.debug("Request to get all Userauths");
        return userauthRepository.findAll();
    }


    /**
     * Get one userauth by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Userauth> findOne(Long id) {
        log.debug("Request to get Userauth : {}", id);
        return userauthRepository.findById(id);
    }

    /**
     * Delete the userauth by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Userauth : {}", id);
        userauthRepository.deleteById(id);
        userauthSearchRepository.deleteById(id);
    }

    /**
     * Search for the userauth corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Userauth> search(String query) {
        log.debug("Request to search Userauths for query {}", query);
        return StreamSupport
            .stream(userauthSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

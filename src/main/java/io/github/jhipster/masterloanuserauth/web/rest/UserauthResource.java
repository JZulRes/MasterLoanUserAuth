package io.github.jhipster.masterloanuserauth.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.masterloanuserauth.domain.Userauth;
import io.github.jhipster.masterloanuserauth.service.UserauthService;
import io.github.jhipster.masterloanuserauth.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.masterloanuserauth.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Userauth.
 */
@RestController
@RequestMapping("/api")
public class UserauthResource {

    private final Logger log = LoggerFactory.getLogger(UserauthResource.class);

    private static final String ENTITY_NAME = "masterLoanUserAuthUserauth";

    private final UserauthService userauthService;

    public UserauthResource(UserauthService userauthService) {
        this.userauthService = userauthService;
    }

    /**
     * POST  /userauths : Create a new userauth.
     *
     * @param userauth the userauth to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userauth, or with status 400 (Bad Request) if the userauth has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/userauths")
    @Timed
    public ResponseEntity<Userauth> createUserauth(@Valid @RequestBody Userauth userauth) throws URISyntaxException {
        log.debug("REST request to save Userauth : {}", userauth);
        if (userauth.getId() != null) {
            throw new BadRequestAlertException("A new userauth cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Userauth result = userauthService.save(userauth);
        return ResponseEntity.created(new URI("/api/userauths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /userauths : Updates an existing userauth.
     *
     * @param userauth the userauth to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userauth,
     * or with status 400 (Bad Request) if the userauth is not valid,
     * or with status 500 (Internal Server Error) if the userauth couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/userauths")
    @Timed
    public ResponseEntity<Userauth> updateUserauth(@Valid @RequestBody Userauth userauth) throws URISyntaxException {
        log.debug("REST request to update Userauth : {}", userauth);
        if (userauth.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Userauth result = userauthService.save(userauth);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userauth.getId().toString()))
            .body(result);
    }

    /**
     * GET  /userauths : get all the userauths.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userauths in body
     */
    @GetMapping("/userauths")
    @Timed
    public List<Userauth> getAllUserauths() {
        log.debug("REST request to get all Userauths");
        return userauthService.findAll();
    }

    /**
     * GET  /userauths/:id : get the "id" userauth.
     *
     * @param id the id of the userauth to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userauth, or with status 404 (Not Found)
     */
    @GetMapping("/userauths/{id}")
    @Timed
    public ResponseEntity<Userauth> getUserauth(@PathVariable Long id) {
        log.debug("REST request to get Userauth : {}", id);
        Optional<Userauth> userauth = userauthService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userauth);
    }

    /**
     * DELETE  /userauths/:id : delete the "id" userauth.
     *
     * @param id the id of the userauth to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/userauths/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserauth(@PathVariable Long id) {
        log.debug("REST request to delete Userauth : {}", id);
        userauthService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/userauths?query=:query : search for the userauth corresponding
     * to the query.
     *
     * @param query the query of the userauth search
     * @return the result of the search
     */
    @GetMapping("/_search/userauths")
    @Timed
    public List<Userauth> searchUserauths(@RequestParam String query) {
        log.debug("REST request to search Userauths for query {}", query);
        return userauthService.search(query);
    }

}

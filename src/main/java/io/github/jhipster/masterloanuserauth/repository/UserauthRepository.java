package io.github.jhipster.masterloanuserauth.repository;

import io.github.jhipster.masterloanuserauth.domain.Userauth;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Userauth entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserauthRepository extends JpaRepository<Userauth, Long> {

}

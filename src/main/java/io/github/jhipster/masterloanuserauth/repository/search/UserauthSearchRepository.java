package io.github.jhipster.masterloanuserauth.repository.search;

import io.github.jhipster.masterloanuserauth.domain.Userauth;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Userauth entity.
 */
public interface UserauthSearchRepository extends ElasticsearchRepository<Userauth, Long> {
}

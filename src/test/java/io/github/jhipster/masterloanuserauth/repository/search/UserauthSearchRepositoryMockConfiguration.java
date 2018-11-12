package io.github.jhipster.masterloanuserauth.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of UserauthSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class UserauthSearchRepositoryMockConfiguration {

    @MockBean
    private UserauthSearchRepository mockUserauthSearchRepository;

}

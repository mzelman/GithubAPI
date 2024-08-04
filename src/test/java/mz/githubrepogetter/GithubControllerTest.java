package mz.githubrepogetter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import mz.githubrepogetter.entity.Owner;
import mz.githubrepogetter.entity.Repository;
import mz.githubrepogetter.service.GithubService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GithubControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GithubService githubService;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void testGetUserRepositories() throws Exception {
        List<Repository> repositories = Arrays.asList(
                new Repository("example1", new Owner("testUser"), false, "url1", null),
                new Repository("example2", new Owner("testUser"), false, "url2", null));

        when(githubService.getUserNonForkRepos("testUser")).thenReturn(repositories);

        mockMvc.perform(get("/repositories").param("username", "testUser"))
                                                        .andExpectAll(
                                                            status().isOk(),
                                                            content().contentType(MediaType.APPLICATION_JSON),
                                                            content().json(mapper.writeValueAsString(repositories))
                                                        );
    }
}
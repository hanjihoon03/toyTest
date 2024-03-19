package toypro.developer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import toypro.developer.domain.Article;
import toypro.developer.dto.AddArticleRequest;
import toypro.developer.dto.UpdateArticleRequest;
import toypro.developer.repository.BlogRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class BlogApiControllerTest {
    @Autowired
    BlogRepository repository;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        repository.deleteAll();
    }

    @Test
    void addArticle() throws Exception {
        //given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);
        final AddArticleRequest userRequest1 = new AddArticleRequest(title, content);

        //직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);
        final String requestBody1 = objectMapper.writeValueAsString(userRequest1);

        //when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
        );
        ResultActions result1 = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody1)
        );
        log.info("result= {}, .getClass= {}", result, result.getClass());

        //then
        result.andExpect(status().isCreated());
        List<Article> articles = repository.findAll();
        for (Article article : articles) {
            log.info("article.title={}", article.getTitle());
            log.info("article.content={}", article.getContent());
            log.info("article={}", article.getClass());
            log.info("articles={}", articles.getClass());
        }

        assertThat(articles.size()).isEqualTo(2);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);



    }

    @Test
    void findAllArticles() throws Exception {
        //given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        repository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        //when
        final ResultActions resultAction = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));


    }

    @Test
    void findArticle() throws Exception {
        //given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = repository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        //when
        ResultActions result = mockMvc.perform(get(url, savedArticle.getId()));

        String jsonResult = result.andReturn().getResponse().getContentAsString();
        log.info("json={}", jsonResult);

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.title").value(title));

    }

    @Test
    void deleteArticles() throws Exception {
        //given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = repository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        //when
        mockMvc.perform(delete(url, savedArticle.getId()))
                .andExpect(status().isOk());

        //then
        List<Article> articles = repository.findAll();

        assertThat(articles).isEmpty();


    }

    @Test
    void updateArticle() throws Exception {
        //given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = repository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        final String newTitle = "title";
        final String newContent = "content";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);


        //when
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));
        //then
        result.andExpect(status().isOk());

        Article article = repository.findById(savedArticle.getId()).get();

        assertThat(article.getContent()).isEqualTo(newContent);
        assertThat(article.getTitle()).isEqualTo(newTitle);


    }


}
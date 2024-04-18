package com.ryan.simpleBlog.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ryan.simpleBlog.domain.Article;
//import com.ryan.simpleBlog.dto.AddArticleRequest;
//import com.ryan.simpleBlog.dto.UpdateArticleRequest;
//import com.ryan.simpleBlog.repository.BlogRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest // 테스트용 애플리케이션 컨텍스트
//@AutoConfigureMockMvc // MockMVC 생성 및 자동 구성
//class BlogApiControllerTest {
//
//    @Autowired
//    protected MockMvc mockMvc;
//
//    @Autowired
//    protected ObjectMapper objectMapper; // 직렬화 및 역직렬화를 위한 클래스
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired
//    BlogRepository blogRepository;
//
//    @BeforeEach
//    public void mockMvcSetup() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
//                .build();
//        blogRepository.deleteAll();
//    }
//
//    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")
//    @Test
//    public void addArticle() throws Exception {
//        // given
//        final String url = "/api/articles";
//        final String title = "title";
//        final String content = "content";
//        final AddArticleRequest userRequest = new AddArticleRequest(title, content);
//
//        // 객체 JSON으로 직렬화
//        final String requestBody = objectMapper.writeValueAsString(userRequest);
//
//        // when
//        // 설정한 내용을 바탕으로 요청 전송
//        /**
//         * ObjectMapper의 writeValueAsString 메서드는 자바 객체를 JSON 문자열로 변환합니다.
//         * 여기서는 userRequest 객체(블로그 글을 추가하기 위한 요청 객체)를 JSON 문자열로 변환하여 requestBody 변수에 저장합니다.
//         * 이 JSON 문자열은 이후 HTTP 요청의 본문으로 사용됩니다.
//         */
//        ResultActions result = mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(requestBody));
//
//        // then
//        /**
//         * andExpect 메서드는 실행된 요청의 결과를 검증하는 데 사용됩니다.
//         * 여기서는 status().isCreated()를 통해 HTTP 응답 상태 코드가 201 Created인지 확인합니다.
//         * 201 Created 상태 코드는 요청이 성공적으로 처리되었고, 그 결과로 새로운 리소스가 생성되었음을 나타냅니다.
//         */
//        result.andExpect(status().isCreated());
//
//        List<Article> articles = blogRepository.findAll();
//
//        Assertions.assertThat(articles.size()).isEqualTo(1);
//        Assertions.assertThat(articles.get(0).getTitle()).isEqualTo(title);
//        Assertions.assertThat(articles.get(0).getContent()).isEqualTo(content);
//    }
//
//    @DisplayName("findAllArticles: 블로그 글 목록 조회에 성공한다.")
//    @Test
//    public void findAllArticles() throws Exception {
//        // given
//        final String url = "/api/articles";
//        final String title = "title";
//        final String content = "content";
//
//        blogRepository.save(Article.builder()
//                .title(title)
//                .content(content)
//                .build());
//
//        // when
//        final ResultActions resultActions = mockMvc.perform(get(url)
//                .accept(MediaType.APPLICATION_JSON));
//
//        // then
//        // JSON 응답본문의 첫번째 요소(인덱스 0)의 content 필드가 테스트에서 지정한 content 값과 일치하는지 검증
//        // JSON 응답본문의 두번째 요소(인덱스 1)의 title 필드가 테스트에서 지정한 title 값과 일치하는지 검증
//        resultActions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].content").value(content))
//                .andExpect(jsonPath("$[0].title").value(title));
//    }
//
//    @DisplayName("findArticle: 블로그 글 조회에 성공한다.")
//    @Test
//    public void findArticle() throws Exception {
//        // given
//        final String url = "/api/articles/{id}";
//        final String title = "title";
//        final String content = "content";
//
//        Article savedArticle = blogRepository.save(Article.builder()
//                .title(title)
//                .content(content)
//                .build());
//
//        // when
//        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));
//
//        // then
//        resultActions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").value(content))
//                .andExpect(jsonPath("$.title").value(title));
//    }
//
//    @DisplayName("deleteArticlee: 블로그 글 삭제에 성공한다.")
//    @Test
//    public void deleteArticle() throws Exception {
//        // given
//        final String url = "/api/articles/{id}";
//        final String title = "title";
//        final String content = "content";
//
//        Article savedArticle = blogRepository.save(Article.builder()
//                .title(title)
//                .content(content)
//                .build());
//
//        // when
//        mockMvc.perform(delete(url, savedArticle.getId()))
//                .andExpect(status().isOk());
//        // then
//        List<Article> articles = blogRepository.findAll();
//        Assertions.assertThat(articles).isEmpty();
//    }
//
//    @DisplayName("updateArticle: 블로그 글 수정에 성공한다.")
//    @Test
//    public void updateArticle() throws Exception {
//        // given
//        final String url = "/api/articles/{id}";
//        final String title = "title";
//        final String content = "content";
//        Article savedArticle = blogRepository.save(Article.builder()
//                .title(title)
//                .content(content)
//                .build());
//
//        final String newTitle = "new title";
//        final String newContent = "new content";
//        UpdateArticleRequest request = new UpdateArticleRequest(newTitle,
//                newContent);
//
//        // when
//        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(request)));
//
//        // then
//        result.andExpect(status().isOk());
//        Article article = blogRepository.findById(savedArticle.getId()).get();
//        Assertions.assertThat(article.getTitle()).isEqualTo(newTitle);
//        Assertions.assertThat(article.getContent()).isEqualTo(newContent);
//    }
//}

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryan.simpleBlog.domain.Article;
import com.ryan.simpleBlog.domain.User;
import com.ryan.simpleBlog.dto.AddArticleRequest;
import com.ryan.simpleBlog.dto.UpdateArticleRequest;
import com.ryan.simpleBlog.repository.BlogRepository;
import com.ryan.simpleBlog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        blogRepository.deleteAll();
    }


    @BeforeEach
    void setSecurityContext() {
        userRepository.deleteAll();
        user = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }


    @DisplayName("addArticle: 아티클 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);

        final String requestBody = objectMapper.writeValueAsString(userRequest);

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .principal(principal)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAllArticles: 아티클 목록 조회에 성공한다.")
    @Test
    public void findAllArticles() throws Exception {
        // given
        final String url = "/api/articles";
        Article savedArticle = createDefaultArticle();

        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(savedArticle.getContent()))
                .andExpect(jsonPath("$[0].title").value(savedArticle.getTitle()));
    }

    @DisplayName("findArticle: 아티클 단건 조회에 성공한다.")
    @Test
    public void findArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(savedArticle.getContent()))
                .andExpect(jsonPath("$.title").value(savedArticle.getTitle()));
    }


    @DisplayName("deleteArticle: 아티클 삭제에 성공한다.")
    @Test
    public void deleteArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();

        // when
        mockMvc.perform(delete(url, savedArticle.getId()))
                .andExpect(status().isOk());

        // then
        List<Article> articles = blogRepository.findAll();

        assertThat(articles).isEmpty();
    }


    @DisplayName("updateArticle: 아티클 수정에 성공한다.")
    @Test
    public void updateArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();

        final String newTitle = "new title";
        final String newContent = "new content";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

        // when
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        Article article = blogRepository.findById(savedArticle.getId()).get();

        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);
    }

    private Article createDefaultArticle() {
        return blogRepository.save(Article.builder()
                .title("title")
                .author(user.getUsername())
                .content("content")
                .build());
    }
}
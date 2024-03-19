package toypro.developer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import toypro.developer.domain.Article;
import toypro.developer.dto.AddArticleRequest;
import toypro.developer.dto.ArticleResponse;
import toypro.developer.dto.UpdateArticleRequest;
import toypro.developer.service.BlogService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class BlogApiController {

    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        //RequestBody 요청 값 매핑
        Article savedArticle = blogService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticle() {
        List<ArticleResponse> findAllArticle = blogService.findAll()
                .stream()
                .map(article -> new ArticleResponse(article.getTitle(), article.getContent()))
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(findAllArticle);

    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable Long id) {
        Article findArticle = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(findArticle.getTitle(), findArticle.getContent()));

    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id,
                                                 @RequestBody UpdateArticleRequest request) {
        Article updateArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updateArticle);
    }
}

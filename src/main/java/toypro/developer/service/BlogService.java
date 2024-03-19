package toypro.developer.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toypro.developer.domain.Article;
import toypro.developer.dto.AddArticleRequest;
import toypro.developer.dto.UpdateArticleRequest;
import toypro.developer.repository.BlogRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository repository;

    public Article save(AddArticleRequest request) {
        return repository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return repository.findAll();
    }

    public Article findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found:"+ id));
    }
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public Article update(Long id, UpdateArticleRequest request) {
        Article article = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found" + id));

        article.update(request.getTitle(), request.getContent());
        return article;

    }
}

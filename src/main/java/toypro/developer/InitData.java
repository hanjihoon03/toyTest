package toypro.developer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import toypro.developer.domain.Article;
import toypro.developer.repository.BlogRepository;

import static java.time.LocalDateTime.now;

@Component
@RequiredArgsConstructor
public class InitData {

    private final BlogRepository repository;

    @PostConstruct
    @Transactional
    public void initData() {
        Article article = new Article("제목1", "내용1","user1", now(), now());
        Article article1 = new Article("제목2", "내용2","user2", now(), now());
        Article article2 = new Article("제목3", "내용3","user3", now(), now());

        repository.save(article);
        repository.save(article1);
        repository.save(article2);

    }

}
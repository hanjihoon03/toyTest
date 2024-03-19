package toypro.developer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toypro.developer.domain.Article;

@Repository
public interface BlogRepository extends JpaRepository<Article, Long> {
}

package toypro.developer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toypro.developer.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}

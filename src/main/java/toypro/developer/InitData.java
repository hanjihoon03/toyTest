package toypro.developer;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import toypro.developer.domain.Member;
import toypro.developer.repository.MemberRepository;

@Component
@RequiredArgsConstructor
public class InitData {

    private final MemberRepository repository;

    @PostConstruct
    @Transactional
    public void init() {
        Member member1 = new Member(1L, "name1");
        Member member2 = new Member(2L, "name2");
        Member member3 = new Member(3L, "name3");

        repository.save(member1);
        repository.save(member2);
        repository.save(member3);
    }
}

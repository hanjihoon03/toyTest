package toypro.developer;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import toypro.developer.domain.Member;
import toypro.developer.repository.MemberRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class InitDataTest {

    @Autowired
    MemberRepository repository;

    @BeforeEach
    void before() {
        repository.deleteAll();
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void saveTest() {
        //given
        Member member1 = new Member(1L, "name1");
        Member member2 = new Member(2L, "name2");
        Member member3 = new Member(3L, "name3");

        //when
        repository.save(member1);
        repository.save(member2);
        repository.save(member3);
        List<Member> result = repository.findAll();
        for (Member member : result) {
            log.info("memberId={}", member.getId());
            log.info("memberName={}", member.getName());
        }

        //then
        assertThat(result.size()).isEqualTo(3);


    }

}
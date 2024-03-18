package toypro.developer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toypro.developer.domain.Member;
import toypro.developer.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;

    public List<Member> getAllMembers() {
        return repository.findAll();
    }
}

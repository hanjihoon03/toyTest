package toypro.developer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toypro.developer.domain.RefreshToken;
import toypro.developer.repository.RefreshTokenRepository;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }
}
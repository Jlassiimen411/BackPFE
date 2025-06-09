package backAgil.example.back.services;

import backAgil.example.back.models.PasswordResetToken;
import backAgil.example.back.repositories.PasswordResetTokenRepository;
import backAgil.example.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private UserRepository userRepository;
    public void createOrUpdateToken(String userName, String token, Date expiryDate) {
        Optional<PasswordResetToken> tokenOpt = passwordResetTokenRepository.findByUser_UserName(userName);


        if (tokenOpt.isPresent()) {
            PasswordResetToken existingToken = tokenOpt.get();
            existingToken.setToken(token);
            existingToken.setExpiryDate(expiryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            passwordResetTokenRepository.save(existingToken);
        } else {
            PasswordResetToken newToken = new PasswordResetToken();
            newToken.setUser(userRepository.findByUserName(userName).orElseThrow(() -> new RuntimeException("User not found")));
            newToken.setToken(token);
            newToken.setExpiryDate(expiryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            passwordResetTokenRepository.save(newToken);
        }
    }

}


package com.eayesiltas.bookApp.security.auth;

import com.eayesiltas.bookApp.entity.DetailsOfUser;
import com.eayesiltas.bookApp.entity.User;
import com.eayesiltas.bookApp.repository.DetailsOfUserRepository;
import com.eayesiltas.bookApp.repository.UserRepository;
import com.eayesiltas.bookApp.request.UpdatePasswordRequest;
import com.eayesiltas.bookApp.security.config.JwtService;
import com.eayesiltas.bookApp.security.token.Token;
import com.eayesiltas.bookApp.security.token.TokenRepository;
import com.eayesiltas.bookApp.security.token.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final DetailsOfUserRepository detailsOfUserRepository;

    public String register(RegisterRequest request) {

        List<User> users = userRepository.findAll();

        for (int i = 0; i < users.size(); i++) {

            String email = users.get(i).getEmail();
            String email2 = request.getEmail();

            if(email2.equals(email)){
                return "Your email has already used. Please use different email to register!";
            }
        }

        if(! (isEnoughLength(request.getPassword())) || !(isIncludeAnyNumber(request.getPassword())) ){
            return isValidPassword(request.getPassword());
        }

        var detailsOfUser = DetailsOfUser.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .gsm(request.getGsm())
                .address(request.getAddress())
                .birthDate(request.getBirthDate())
                .gender(request.getGender())
                .currentBookId(null)
                .build();

        detailsOfUserRepository.save(detailsOfUser);

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .detailsOfUser(detailsOfUser)
                .role(request.getRole())
                .build();

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);

        String result = "access token: "+ jwtToken+"\n";
        result+="refresh token: "+refreshToken;

        return result;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public String changePassword(UpdatePasswordRequest request){
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        String x = request.getCurrentPassword();
        String y = request.getVerifyPassword();
        String z = request.getNewPassword();

        if(user == null){
            return "There is no user has this email.";
        }else if(!(passwordEncoder.matches(request.getCurrentPassword(), user.getPassword()))){
            return "The password you entered is wrong.";

        }else if(!x.equals(y)){
            return "Your verified password does not match with your current password.";

        }else if(x.equals(z)){
            return "The new password cannot be same with beforehand.";
        }else if(! (isEnoughLength(request.getNewPassword())) || !(isIncludeAnyNumber(request.getNewPassword())) ){
            return isValidPassword(request.getNewPassword());
        }else{
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
            return "Your password was changed successfully!";
        }
    }

    public String isValidPassword(String password){

        String message = "";

        if(!isEnoughLength(password)){
            message+="The password must be at least 8 characters.\n";
        }
        if(!isIncludeAnyNumber(password)){
            message+="The password must be includes at least one number.";
        }

        return message;
    }

    public boolean isEnoughLength(String password){
        if(password.length() < 8){
            return false;
        }
        return true;
    }

    public boolean isIncludeAnyNumber(String password){

        char c;
        char[] numbers = {'0','1','2','3','4','5','6','7','8','9'};

        for (int i = 0; i < password.length(); i++) {
            c = password.charAt(i);

            for (int j = 0; j < numbers.length; j++) {
                if(c == numbers[j]){
                    return true;
                }
            }
        }
        return false;
    }
}

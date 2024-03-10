package dat3.security.api;

import dat3.security.service.UserDetailsServiceImp;
import dat3.security.dto.LoginRequest;
import dat3.security.dto.LoginResponse;
import dat3.security.entity.UserWithRoles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@RestController
@RequestMapping("/api/auth/")
@CrossOrigin
public class AuthenticationController {

  @Value("${app.token-issuer}")
  private String tokenIssuer;

  @Value("${app.token-expiration}")
  private long tokenExpiration;

  private final AuthenticationManager authenticationManager;
  UserDetailsServiceImp userDetailsServiceImp;


  JwtEncoder encoder;
  public AuthenticationController(AuthenticationManager authenticationManager, JwtEncoder encoder, UserDetailsServiceImp userDetailsServiceImp) {
    this.authenticationManager = authenticationManager;
    this.encoder = encoder;
    this.userDetailsServiceImp = userDetailsServiceImp;
  }

  @PostMapping("login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    try {
      UserWithRoles userCaseSensitive = userDetailsServiceImp.userNotIgnoreCase(request.getUsername());
      if(!userCaseSensitive.getUsername().equals(request.getUsername())) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UserDetailsServiceImp.WRONG_USERNAME_OR_PASSWORD);
      }

      UsernamePasswordAuthenticationToken uat = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
      Authentication authentication = authenticationManager.authenticate(uat);
      UserWithRoles user = (UserWithRoles) authentication.getPrincipal();

      Instant now = Instant.now();
      long expiry = tokenExpiration;
      String scope = authentication.getAuthorities().stream()
              .map(GrantedAuthority::getAuthority)
              .collect(joining(" "));

      JwtClaimsSet claims = JwtClaimsSet.builder()
              .issuer(tokenIssuer)  //Only this for simplicity
              .issuedAt(now)
              .expiresAt(now.plusSeconds(tokenExpiration))
              .subject(user.getUsername())
              .claim("roles", scope)
              .build();
      JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();
      String token = encoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

      List<String> roles = user.getRoles().stream().map(Enum::toString).collect(Collectors.toList());
      return ResponseEntity.ok()
              .body(new LoginResponse(user.getUsername(), token, roles));

    } catch (BadCredentialsException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UserDetailsServiceImp.WRONG_USERNAME_OR_PASSWORD);
    }
  }

}

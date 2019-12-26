package com.castlery.modules.web.service.impl;

import com.castlery.modules.web.dto.Constant;
import com.castlery.modules.web.dto.LoginUser;
import com.castlery.modules.web.service.HydraService;
import com.castlery.system.domain.User;
import com.castlery.system.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ory.hydra.model.AcceptConsentRequest;
import com.github.ory.hydra.model.AcceptLoginRequest;
import com.github.ory.hydra.model.CompletedRequest;
import com.github.ory.hydra.model.ConsentRequest;
import com.github.ory.hydra.model.LoginRequest;
import com.github.ory.hydra.model.RejectRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Service
public class HydraServiceImpl implements HydraService {

  private static final String HYDRA_AUTH_URI = "/oauth2/auth/requests/";
  private static final String OAUTH2_URI = "/oauth2/authorization/azure?challenge=";

  @Value("${hydra.admin.url}")
  private String hydraAdminUrl;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RestTemplateBuilder restTemplateBuilder;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public ModelAndView login(String challenge, HttpServletResponse response)
      throws IOException {
    // 调用登陆
    String login = getRequest("login", challenge);
    LoginRequest loginRequest = objectMapper.readValue(login, LoginRequest.class);
    if (loginRequest.getSkip()) {
      // accept login
      String redirectUrl = acceptLogin(challenge, loginRequest.getSubject(), response);
      return new ModelAndView("redirect:" + redirectUrl);
    } else {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName(Constant.LOGIN_UI);
      modelAndView.addObject("challenge", challenge);
      modelAndView.addObject("hydraUrl", OAUTH2_URI + challenge);
      return modelAndView;
    }
  }

  @Override
  public void login(LoginUser user, HttpServletResponse response) throws IOException {
    User databaseUser = userRepository.findByEmail(user.getEmail());
    if (null == databaseUser) {
      rejectLogin(user.getChallenge(), response);
    } else {
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
      if (!encoder.matches(user.getPassword(), databaseUser.getPassword())) {
        rejectLogin(user.getChallenge(), response);
      }
      String redirectUrl = acceptLogin(user.getChallenge(), databaseUser.getEmail(), response);
      response.sendRedirect(redirectUrl);
    }
  }

  @Override
  public String acceptLogin(String challenge, String subject, HttpServletResponse response)
      throws IOException {
    AcceptLoginRequest acceptLoginRequest = initLoginConsentRequest(subject);
    CompletedRequest completedRequest = objectMapper
        .readValue(putRequest("login", "accept", challenge, acceptLoginRequest),
            CompletedRequest.class);
    log.info(completedRequest.getRedirectTo());
    return completedRequest.getRedirectTo();
  }

  @Override
  public void consent(String challenge, HttpServletResponse response) {
    log.info("accept consent");
    // get consent
    String consent = getRequest("consent", challenge);
    try {
      ConsentRequest consentRequest = objectMapper.readValue(consent, ConsentRequest.class);
      AcceptConsentRequest acceptConsentRequest = initScopeConsentRequest(consentRequest);
      String result = putRequest("consent", "accept", challenge, acceptConsentRequest);
      CompletedRequest completedRequest = objectMapper.readValue(result, CompletedRequest.class);
      response.sendRedirect(completedRequest.getRedirectTo());
    } catch (IOException e) {
      log.error("Read consent response error. {} ", e);
      throw new InternalError();
    }

  }

  private void rejectLogin(String challenge, HttpServletResponse response)
      throws IOException {
    RejectRequest rejectRequest = new RejectRequest();
    rejectRequest.setError("Unauthorized");
    rejectRequest.setErrorDescription("User's name and password didn't match.");
    rejectRequest.setStatusCode(400L);
    String result = putRequest("login", "reject", challenge, rejectRequest);
    CompletedRequest completedRequest = objectMapper.readValue(result, CompletedRequest.class);
    response.sendRedirect(completedRequest.getRedirectTo());
  }

  private AcceptLoginRequest initLoginConsentRequest(String subject) {
    AcceptLoginRequest acceptLoginRequest = new AcceptLoginRequest();
    acceptLoginRequest.setRemember(true);
    acceptLoginRequest.setRememberFor(7200L);
    acceptLoginRequest.setSubject(subject);
    return acceptLoginRequest;
  }

  private AcceptConsentRequest initScopeConsentRequest(ConsentRequest consentRequest) {
    AcceptConsentRequest acceptConsentRequest = new AcceptConsentRequest();
    acceptConsentRequest.setRemember(true);
    acceptConsentRequest.setRememberFor(7200L);
    // get user's permission code to list and put here
    List<String> scopes = new ArrayList<>(3);
    scopes.add("openid");
    scopes.add("offline");
    scopes.add("photos.read");
    acceptConsentRequest
        .setGrantAccessTokenAudience(consentRequest.getRequestedAccessTokenAudience());
    acceptConsentRequest.setGrantScope(scopes);
    return acceptConsentRequest;
  }

  private <T> String putRequest(String flow, String action, String challenge, T body)
      throws JsonProcessingException {
    String preUrl = hydraAdminUrl + HYDRA_AUTH_URI + "%s/%s?%s_challenge=%s";
    String url = String.format(preUrl, flow, action, flow, challenge);
    log.info("Put request hydra url is {}", url);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(body), headers);
    ResponseEntity<String> responseEntity =
        restTemplateBuilder.build().exchange(url, HttpMethod.PUT, entity, String.class);
    HttpStatus statusCode = responseEntity.getStatusCode();
    if (statusCode.is2xxSuccessful()) {
      return responseEntity.getBody();
    } else {
      throw new RuntimeException();
    }
  }

  private String getRequest(String flow, String challenge) {
    String preUrl = hydraAdminUrl + HYDRA_AUTH_URI + "%s?%s_challenge=%s";
    String url = String.format(preUrl, flow, flow, challenge);
    log.info("Get request hydra url is {}", url);
    RestTemplate restTemplate = restTemplateBuilder.build();
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
    HttpStatus statusCode = responseEntity.getStatusCode();
    if (statusCode.is2xxSuccessful()) {
      return responseEntity.getBody();
    } else {
      throw new RuntimeException();
    }
  }
}

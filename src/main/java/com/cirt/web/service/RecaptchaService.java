package com.cirt.web.service;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

import com.cirt.web.config.RecaptchaProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Nullable;

@Service
public class RecaptchaService {
  private final RestClient http = RestClient.create();
  private final RecaptchaProperties props;
  public RecaptchaService(RecaptchaProperties props) { this.props = props; }

  public static record VerifyRes(
      boolean success,
      Float score,
      String action,
      @JsonProperty("error-codes") List<String> errorCodes
  ) {}

  public VerifyRes verify(String token, @Nullable String ip) {
    var form = new LinkedMultiValueMap<String,String>();
    form.add("secret", props.getSecretKey());
    form.add("response", token);
    if (ip != null) form.add("remoteip", ip);

    return http.post().uri(props.getVerifyUrl())
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .body(form).retrieve()
      .body(VerifyRes.class);
  }
}

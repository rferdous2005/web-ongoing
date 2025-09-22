package com.cirt.web.entity;

import java.time.Instant;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Otp {
    @Id
    private int id; // random UUID key
    private String contact; // email or phone
    private String purpose; // "REPORT_INCIDENT"
    private String otpHash; // Argon2/Bcrypt hash
    private Instant expiresAt; // now + 5 minutes
    private int attempts; // increment on verify fail
    private String ipHash; // hash of IP (optional)
    private String userAgentHash; // hash of UA (optional)
    // getters/setters
}

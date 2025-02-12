package com.cirt.web.dto;

import java.time.LocalDateTime;

public interface PostSummaryDto {
    Long getId();
    String getTitle();
    String getVisibility();
    String getCategory();
    LocalDateTime getPublishedAt();  
    String getSubTitle();
    String getUri();
    String getThumbnail();  
}

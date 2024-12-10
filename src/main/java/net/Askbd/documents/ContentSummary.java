package net.Askbd.documents;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "contents")
@Getter
@Setter
public class ContentSummary {
    @Id private String id;
    
    private String prompt, viewType, category, thumbnail , uri, visitedView, categoryView, createdAtView;
    private LocalDateTime createdAt;
    private int visited;
}

package net.Askbd.documents;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "contents")
@Getter
@Setter
public class Content {
    @Id private String id;
    
    private String prompt, viewType, category, uri, title, keywords, description, visitedView, categoryView, createdAtView;
    private LocalDateTime createdAt;
    private int visited;
    List<Answer> answers;
    
    class Answer {
        public String body, viewType;
    }
}

package net.Askbd.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import net.Askbd.documents.ContentSummary;

@Repository
public interface ContentSummaryRepository extends MongoRepository<ContentSummary, String>{
    //Content findByCategoryAndUri(String category, String uri);
}

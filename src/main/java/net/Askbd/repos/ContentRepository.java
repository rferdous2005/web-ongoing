package net.Askbd.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import net.Askbd.documents.Content;

@Repository
public interface ContentRepository extends MongoRepository<Content, String>{
    Content findByCategoryAndUri(String category, String uri);
}

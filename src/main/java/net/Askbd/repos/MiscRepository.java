package net.Askbd.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import net.Askbd.documents.Misc;

@Repository
public interface MiscRepository extends MongoRepository<Misc, String> {
    Misc findByItemName(String itemName);
}

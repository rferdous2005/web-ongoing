package net.Askbd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.Askbd.documents.Content;
import net.Askbd.repos.ContentRepository;

@Service
public class ContentService {
    @Autowired
    private ContentRepository contentRepository;

    public Content getSingleContentByCategoryAndPostURI(String category, String postURI) {
        return contentRepository.findByCategoryAndUri(category, postURI);
    }
}

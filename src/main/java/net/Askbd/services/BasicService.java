package net.Askbd.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.Askbd.config.Utilities;
import net.Askbd.documents.Content;
import net.Askbd.documents.ContentSummary;
import net.Askbd.repos.ContentRepository;
import net.Askbd.repos.ContentSummaryRepository;

@Service
public class BasicService {

    @Value("${content.popular.count:20}")
    private int popularCount;
    @Value("${content.recent.count:10}")
    private int recentCount;

    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private ContentSummaryRepository contentSummaryRepository;

    public List<ContentSummary> getTopRecentCreatedContents() {
        Pageable pageable = PageRequest.of(0, recentCount, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<ContentSummary> contentPage = contentSummaryRepository.findAll(pageable);
        Utilities.convertIntoBangla(contentPage.getContent());
        return contentPage.getContent(); // Get the content as a list
    }

    public List<ContentSummary> getTopPopularContents() {
        Pageable pageable = PageRequest.of(0, popularCount, Sort.by(Sort.Direction.DESC, "visited"));
        Page<ContentSummary> contentPage = contentSummaryRepository.findAll(pageable);
        Utilities.convertIntoBangla(contentPage.getContent());
        return contentPage.getContent(); // Get the content as a list
    }
}

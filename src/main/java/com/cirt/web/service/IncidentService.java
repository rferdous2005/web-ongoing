package com.cirt.web.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cirt.web.dto.IncidentDto;
import com.cirt.web.dto.PostSummaryDto;
import com.cirt.web.entity.Homepage;
import com.cirt.web.entity.Incident;
import com.cirt.web.entity.Post;
import com.cirt.web.repository.HomepageRepository;
import com.cirt.web.repository.IncidentRepository;
import com.cirt.web.repository.PostRepository;

@Service
public class IncidentService {
    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private MailService mailService;

    @Value("${app.file.location}")
    private String UPLOAD_DIR;
    
    public Incident saveIncident(IncidentDto incidentDto) {
        Incident incidentEntity = new Incident();
        incidentEntity.setAffectedAsset(incidentDto.getAffectedAsset());
        incidentEntity.setAttackVector(incidentDto.getAttackVector());
        incidentEntity.setContactName(incidentDto.getAttackVector());
        incidentEntity.setDescription(incidentDto.getDescription());
        incidentEntity.setDiscovery(incidentDto.getDiscovery());
        incidentEntity.setDomainIP(incidentDto.getDomainIP());
        incidentEntity.setEmail(incidentDto.getEmail());
        incidentEntity.setImpact(incidentDto.getImpact());
        incidentEntity.setIncidentTime(incidentDto.getIncidentTime());
        incidentEntity.setIncidentType(incidentDto.getIncidentType());
        incidentEntity.setLocation(incidentDto.getLocation());
        incidentEntity.setName(incidentDto.getName());
        incidentEntity.setOngoing(incidentDto.getOngoing());
        incidentEntity.setOrgName(incidentDto.getOrgName());
        incidentEntity.setPhone(incidentDto.getPhone());
        incidentEntity.setRegion(incidentDto.getRegion());
        incidentEntity.setReportType(incidentDto.getReportType());
        incidentEntity.setStepsTaken(incidentDto.getStepsTaken());
        incidentEntity.setResponseStatus("New");
        // process zip log file
        String fullFilePath = UPLOAD_DIR + "incident" + "/" + incidentDto.getFiles().getOriginalFilename();
        String filePath = "incident" + "/" + incidentDto.getFiles().getOriginalFilename();
        if (!incidentDto.getFiles().isEmpty()) {
            try {
                byte[] bytes = incidentDto.getFiles().getBytes();
                filePath = Files.exists(Paths.get(fullFilePath)) 
                        ? "incident" + "/" + System.currentTimeMillis() / 1000000+ "-" +incidentDto.getFiles().getOriginalFilename()
                        : filePath;
                Path path = Paths.get(UPLOAD_DIR + filePath);
                Path p = Files.write(path, bytes);
                System.out.println(p.getFileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String genId = "CIRT-INC-"+LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-")) + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        incidentEntity.setGeneratedId(genId);
        incidentEntity.setFileName(filePath);
        incidentEntity = this.incidentRepository.save(incidentEntity);
        
        this.mailService.sendIncidentReceivedEmail(incidentEntity);
        return incidentEntity;
    }

    public Page<Incident> getPaginatedIncidents(Pageable pageable) {
        return this.incidentRepository.findAll(pageable);
    }

    public Incident getSingleIncident(int id) {
        return this.incidentRepository.findById(id).get();
    }

    public void makeResponseDone(int id) {
        Incident incident = this.getSingleIncident(id);
        incident.setResponseStatus("Response Done");
        this.incidentRepository.save(incident);
        this.mailService.sendIncidentResponseDoneEmail(incident);
    }
}

package com.cirt.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
        // process zip log file
        return this.incidentRepository.save(incidentEntity);
    }
}

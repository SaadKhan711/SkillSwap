package com.skills.swap.service;

import com.skills.swap.model.SkillOffering;
import com.skills.swap.model.SkillRequest;
import com.skills.swap.repository.SkillOfferingRepository;
import com.skills.swap.repository.SkillRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private SkillOfferingRepository offeringRepository;

    @Autowired
    private SkillRequestRepository requestRepository;

    @Transactional
    public Optional<SkillOffering> createAndFetchOffering(SkillOffering offering) {
        SkillOffering saved = offeringRepository.save(offering);
        return offeringRepository.findById(saved.getId());
    }

    @Transactional
    public Optional<SkillRequest> createAndFetchRequest(SkillRequest request) {
        SkillRequest saved = requestRepository.save(request);
        return requestRepository.findById(saved.getId());
    }
}

package com.ezshipp.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezshipp.api.persistence.entity.CustomSequenceEntity;
import com.ezshipp.api.repository.CustomSequenceEntityRepository;




@Service
public class SequenceEntityService {
   @Autowired
   CustomSequenceEntityRepository sequenceEntityRepository;
    
    public Long getLastOrderSequenceId()    {
        CustomSequenceEntity customSequenceEntity = sequenceEntityRepository.getOne(1);
        long seqId = customSequenceEntity.getOrderSeqId() + 1;
        customSequenceEntity.setOrderSeqId(seqId);
        sequenceEntityRepository.save(customSequenceEntity);
        return seqId;
    }

}

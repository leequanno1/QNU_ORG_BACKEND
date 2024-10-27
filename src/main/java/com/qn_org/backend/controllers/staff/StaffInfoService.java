package com.qn_org.backend.controllers.staff;

import com.qn_org.backend.models.StaffInfo;
import com.qn_org.backend.repositories.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StaffInfoService {
    private final StaffRepository repository;

    public void save(StaffInfo info) {
        repository.save(info);
    }
}

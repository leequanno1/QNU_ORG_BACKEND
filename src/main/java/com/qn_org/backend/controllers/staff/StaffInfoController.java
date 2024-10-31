package com.qn_org.backend.controllers.staff;

import com.qn_org.backend.models.StaffInfo;
import com.qn_org.backend.responses.QnuResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff_info")
@RequiredArgsConstructor
@CrossOrigin
public class StaffInfoController {
    private final StaffInfoService service;

    @PutMapping("/update")
    public QnuResponseEntity<StaffInfo> update(@RequestBody UpdateStaffInfoRequest request) {
        return new QnuResponseEntity<>(service.update(request), HttpStatus.OK);
    }

    @GetMapping("/get-by-id")
    public QnuResponseEntity<StaffInfo> getById(@RequestParam String staffKey) {
        return new QnuResponseEntity<>(service.getById(staffKey), HttpStatus.OK);
    }
}

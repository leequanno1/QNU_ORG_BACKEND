package com.qn_org.backend.controllers.student;

import com.qn_org.backend.models.StudentInfo;
import com.qn_org.backend.responses.QnuResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student-info")
@RequiredArgsConstructor
@CrossOrigin
public class StudentInfoController {

    private final StudentInfoService service;

    @PutMapping("/update")
    public QnuResponseEntity<StudentInfo> update(@RequestBody UpdateStudentInfoRequest request) {
        return new QnuResponseEntity<>(service.update(request), HttpStatus.OK);
    }

    @GetMapping("get-by-id")
    public QnuResponseEntity<StudentInfo> getById(@RequestParam String studentKey) {
        return new QnuResponseEntity<>(service.getById(studentKey), HttpStatus.OK);
    }
}

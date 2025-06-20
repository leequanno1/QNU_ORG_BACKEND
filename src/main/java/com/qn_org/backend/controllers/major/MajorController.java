package com.qn_org.backend.controllers.major;

import com.qn_org.backend.common_requests.FromToIndexRequest;
import com.qn_org.backend.responses.QnuResponseEntity;
import com.qn_org.backend.services.exceptions.NoAuthorityToDoActionException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/major")
@RequiredArgsConstructor
@CrossOrigin
public class MajorController {
    private final MajorService service;

    @PutMapping("/create")
    public QnuResponseEntity<MajorDTO> create(@RequestBody CreateMajorRequest request) {
        return new QnuResponseEntity<>(service.create(request), HttpStatus.OK);
    }

    @PostMapping("/create_many")
    public QnuResponseEntity<List<MajorExtend>> createMany(@RequestBody CreateManyMajorRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(service.createMany(request, servletRequest), HttpStatus.OK);
    }

    @PutMapping("/update")
    public QnuResponseEntity<MajorDTO> update(@RequestBody UpdateMajorRequest request) {
        return new QnuResponseEntity<>(service.update(request), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public QnuResponseEntity<MajorDTO> delete(@RequestBody MajorIdRequest request) {
        return new QnuResponseEntity<>(service.delete(request), HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public QnuResponseEntity<List<MajorDTO>> getAll (@RequestBody FromToIndexRequest request) {
        return new QnuResponseEntity<>(service.getAll(request), HttpStatus.OK);
    }

    @GetMapping("/get_all_major_extend")
    public QnuResponseEntity<List<MajorExtend>> getAllForBoard (HttpServletRequest servletRequest) {
        return new QnuResponseEntity<>(service.getAllForBoard(servletRequest), HttpStatus.OK);
    }

    @GetMapping("/get-deleted")
    public QnuResponseEntity<List<MajorDTO>> getDeleted (@RequestBody FromToIndexRequest request) {
        return new QnuResponseEntity<>(service.getDeleted(request), HttpStatus.OK);
    }

    @GetMapping("/get-by-id")
    public QnuResponseEntity<MajorDTO> getById(@RequestParam String majorId) {
        return new QnuResponseEntity<>(service.getById(majorId), HttpStatus.OK);
    }

    @GetMapping("/get-by-dep-id")
    public QnuResponseEntity<List<MajorDTO>> getByDepId(@RequestParam String depId) {
        return new QnuResponseEntity<>(service.getByDepId(depId), HttpStatus.OK);
    }

    @GetMapping("/get-all-total")
    public QnuResponseEntity<Integer> getAllTotal() {
        return new QnuResponseEntity<>(service.getAllTotal(), HttpStatus.OK);
    }

    @GetMapping("/get-deleted-total")
    public QnuResponseEntity<Integer> getDeletedTotal() {
        return new QnuResponseEntity<>(service.getDeletedTotal(), HttpStatus.OK);
    }
}

package com.qn_org.backend.controllers.post;

import com.qn_org.backend.common_requests.FromToIndexRequest;
import com.qn_org.backend.models.Post;
import com.qn_org.backend.responses.QnuResponseEntity;
import com.qn_org.backend.services.exceptions.ApprovalNoAuthorityException;
import com.qn_org.backend.services.exceptions.EditorNoAuthorityException;
import com.qn_org.backend.services.exceptions.NoAuthorityToDoActionException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@CrossOrigin
public class PostController {
    private final PostService service;

    @PutMapping("/create")
    public QnuResponseEntity<PostDTO> create(CreatePostRequest request, HttpServletRequest servletRequest) throws IOException, NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(service.create(request, servletRequest), HttpStatus.OK);
    }

    @PostMapping("/approve")
    public QnuResponseEntity<PostDTO> approve(@RequestBody ApprovePostRequest request, HttpServletRequest servletRequest) throws ApprovalNoAuthorityException {
        return new QnuResponseEntity<>(service.approve(request, servletRequest), HttpStatus.OK);
    }

    @PostMapping("/edit")
    public QnuResponseEntity<PostDTO> edit(EditPostRequest request, HttpServletRequest servletRequest) throws EditorNoAuthorityException, IOException {
        return new QnuResponseEntity<>(service.edit(request, servletRequest),HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public QnuResponseEntity<PostDTO> delete(@RequestBody DeletePostRequest request) throws NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(service.delete(request),HttpStatus.OK);
    }

    @PostMapping("/get_all")
    public QnuResponseEntity<List<PostDTO>> getAll(@RequestBody FromToIndexRequest request, HttpServletRequest servletRequest) {
        return new QnuResponseEntity<>(service.getAll(request, servletRequest), HttpStatus.OK);
    }

    @PostMapping("/get_in_org")
    public QnuResponseEntity<List<PostDTO>> getInOrg(@RequestBody GetInOrgRequest request) {
        return new QnuResponseEntity<>(service.getInOrg(request), HttpStatus.OK);
    }

    @PostMapping("/get_not_approved_in_org")
    public QnuResponseEntity<List<PostDTO>> getNotApprovedInOrg(@RequestBody GetInOrgRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(service.getNotApprovedInOrg(request, servletRequest), HttpStatus.OK);
    }

    @GetMapping("/get_by_id")
    public QnuResponseEntity<PostDTO> getById(@RequestParam String postId, HttpServletRequest request) {
        return new QnuResponseEntity<>(service.getById(postId, request), HttpStatus.OK);
    }

    @ExceptionHandler(ApprovalNoAuthorityException.class)
    public QnuResponseEntity<String> ApprovalNoAuthorityExceptionHandler() {
        return new QnuResponseEntity<>("User have no authority for approve",HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EditorNoAuthorityException.class)
    public QnuResponseEntity<String> EditorNoAuthorityExceptionHandler() {
        return new QnuResponseEntity<>("User have no authority for edit",HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoAuthorityToDoActionException.class)
    public QnuResponseEntity<String> NoAuthorityToDeleteExceptionHandler() {
        return new QnuResponseEntity<>("User have no authority to do this action",HttpStatus.FORBIDDEN);
    }
}

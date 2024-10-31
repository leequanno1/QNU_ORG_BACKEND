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

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@CrossOrigin
public class PostController {
    private final PostService service;

    @PutMapping("/create")
    public QnuResponseEntity<Post> create(@RequestBody CreatePostRequest request) {
        return new QnuResponseEntity<>(service.create(request), HttpStatus.OK);
    }

    @PostMapping("/approve")
    public QnuResponseEntity<Post> approve(@RequestBody ApprovePostRequest request) throws ApprovalNoAuthorityException {
        return new QnuResponseEntity<>(service.approve(request), HttpStatus.OK);
    }

    @PostMapping("/edit")
    public QnuResponseEntity<Post> edit(@RequestBody EditPostRequest request) throws EditorNoAuthorityException {
        return new QnuResponseEntity<>(service.edit(request),HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public QnuResponseEntity<Post> delete(@RequestBody DeletePostRequest request) throws NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(service.delete(request),HttpStatus.OK);
    }

    @GetMapping("/get_all")
    public QnuResponseEntity<List<Post>> getAll(@RequestBody FromToIndexRequest request) {
        return new QnuResponseEntity<>(service.getAll(request), HttpStatus.OK);
    }

    @GetMapping("/get_in_org")
    public QnuResponseEntity<List<Post>> getInOrg(@RequestBody GetInOrgRequest request) {
        return new QnuResponseEntity<>(service.getInOrg(request), HttpStatus.OK);
    }

    @GetMapping("/get-not-approved-in-org")
    public QnuResponseEntity<List<Post>> getNotApprovedInOrg(@RequestBody GetInOrgRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(service.getNotApprovedInOrg(request, servletRequest), HttpStatus.OK);
    }

    @GetMapping("/get_by_id")
    public QnuResponseEntity<Post> getById(@RequestParam String postId, HttpServletRequest request) {
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

package com.qn_org.backend.controllers.auth;

import com.qn_org.backend.config.JwtService;
import com.qn_org.backend.controllers.staff.StaffInfoService;
import com.qn_org.backend.controllers.student.StudentInfoService;
import com.qn_org.backend.models.*;
import com.qn_org.backend.models.enums.UserType;
import com.qn_org.backend.repositories.*;
import com.qn_org.backend.services.QnuService;
import com.qn_org.backend.services.exceptions.EditorNoAuthorityException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements QnuService<User> {

    private final UserRepository repository;
    private final MajorRepository majorRepository;
    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;
    private final StaffRepository staffRepository;

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final StudentInfoService studentInfoService;
    private final StaffInfoService staffInfoService;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .userId(request.getUserId())
                .password(passwordEncoder.encode(request.getPassword()))
                .emailAddress(request.email)
                .userInfoKey(request.handleGetUserInfoKey())
                .displayName(request.displayName)
                .userType(request.userType)
                .insDate(new Date())
                .passwordValidatedFlg(false)
                .build();
        repository.save(user);
        handleSaveRepository(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserId(),
                        request.getPassword()
                )
        );
        var user = repository.findByUserId(request.getUserId())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public boolean handleSaveRepository(User entity) {
        var res = entity.getUserInfoKey() == null;
        if(!res) {
            UserType typeEnum = UserType.fromValue(entity.getUserType());
            switch (typeEnum){
                case STAFF -> staffInfoService.save(StaffInfo.builder()
                        .staffKey(entity.getUserInfoKey())
                        .isTeacher(false)
                        .build());
                case TEACHER -> staffInfoService.save(StaffInfo.builder()
                        .staffKey(entity.getUserInfoKey())
                        .isTeacher(true)
                        .build());
                case STUDENT -> studentInfoService.save(StudentInfo.builder()
                        .studentKey(entity.getUserInfoKey())
                        .build());
            }
        }
        return res;
    }

    public ValidateResponse validation(String bearerToken) {
        if(!bearerToken.startsWith("Bearer")){
            throw new ExpiredJwtException(null, null,null);
        }
        var jwt = bearerToken.substring(7);
        var userId = jwtService.extractUsername(jwt);
        System.out.println(userId);
        return ValidateResponse
                .builder()
                .isValidated(jwtService.isTokenValid(jwt,userDetailsService.loadUserByUsername(userId)))
                .problemCause("")
                .build();
    }

    public String multipleRegister(MultipleRegisterRequest request) throws EditorNoAuthorityException {
        User register = repository.getReferenceById(request.getRegisterId());
        if(!register.isSuperAdmin())
            throw new EditorNoAuthorityException();
        HashMap<String,Major> majorHashMap = new HashMap<>();
        HashMap<String,Department> departmentHashMap = new HashMap<>();
        var users = userDataToUsers(request.getUserData());
        var studentInfos = studentDataToStudents(request.getStudentInfos(),majorHashMap);
        var staffInfos = staffDataToStaffs(request.getStaffInfos(), departmentHashMap);
        repository.saveAll(users);
        studentRepository.saveAll(studentInfos);
        staffRepository.saveAll(staffInfos);
        return "Register OK";
    }

    private List<User> userDataToUsers(List<RequestUserData> userData) {
        List<User> users = new ArrayList<>();
        for(RequestUserData data : userData) {
            users.add(userDataToUser(data));
        }
        return users;
    }

    private User userDataToUser(RequestUserData data) {
        return User.builder()
                .userId(data.getUserId())
                .password("123456")
                .emailAddress(data.getEmailAddress())
                .displayName(data.getDisplayName())
                .userType(data.getUserType())
                .userInfoKey(RegisterRequest.handleGetUserInfoKey(data.getUserType()))
                .insDate(new Date())
                .build();
    }

    private List<StudentInfo> studentDataToStudents(List<RequestStudentInfo> requestStudentInfos, HashMap<String, Major> majorHashMap) {
        List<StudentInfo> studentInfos = new ArrayList<>();
        for(RequestStudentInfo data : requestStudentInfos) {
            studentInfos.add(studentDataToStudent(data, majorHashMap));
        }
        return studentInfos;
    }

    private StudentInfo studentDataToStudent(RequestStudentInfo data, HashMap<String, Major> majorHashMap) {
        return StudentInfo.builder()
                .studentKey(data.getStudentKey())
                .major(flyweightGetMajor(data.getMajorId(),majorHashMap))
                .firstName(data.getFirstName())
                .lastName(data.getLastName())
                .fullName(data.getFullName())
                .phoneNumber(data.getPhoneNumber())
                .insDate(new Date())
                .build();
    }

    private Major flyweightGetMajor(String majorId , HashMap<String,Major> majorHashMap) {
        Major major = majorHashMap.get(majorId);
        if(major == null) {
            major = majorRepository.getReferenceById(majorId);
            majorHashMap.put(majorId, major);
        }
        return major;
    }

    private Department flyweightGetDepartment(String depId, HashMap<String,Department> departmentHashMap) {
        Department department = departmentHashMap.get(depId);
        if(department == null) {
            department = departmentRepository.getReferenceById(depId);
            departmentHashMap.put(depId,department);
        }
        return department;
    }

    private List<StaffInfo> staffDataToStaffs(List<RequestStaffInfo> requestStaffInfos, HashMap<String, Department> departmentHashMap) {
        List<StaffInfo> staffInfos = new ArrayList<>();
        for(RequestStaffInfo data : requestStaffInfos) {
            staffInfos.add(staffDataToStaff(data, departmentHashMap));
        }
        return staffInfos;
    }

    private StaffInfo staffDataToStaff(RequestStaffInfo data, HashMap<String, Department> departmentHashMap) {
        return StaffInfo.builder()
                .staffKey(data.getStaffKey())
                .department(flyweightGetDepartment(data.getDepId(),departmentHashMap))
                .firstName(data.getFirstName())
                .lastName(data.getLastName())
                .fullName(data.getFullName())
                .phoneNumber(data.getPhoneNumber())
                .isTeacher(data.isTeacher())
                .insDate(new Date())
                .build();
    }
}

package com.qn_org.backend.controllers.major;

import com.qn_org.backend.common_requests.FromToIndexRequest;
import com.qn_org.backend.config.JwtService;
import com.qn_org.backend.models.Major;
import com.qn_org.backend.repositories.DepartmentRepository;
import com.qn_org.backend.repositories.MajorRepository;
import com.qn_org.backend.repositories.UserRepository;
import com.qn_org.backend.services.exceptions.NoAuthorityToDoActionException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MajorService {
    private final MajorRepository majorRepository;
    private final DepartmentRepository depRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    public MajorDTO create(CreateMajorRequest request) {
        Major major = Major.builder()
                .department(depRepository.getReferenceById(request.getDepId()))
                .majorName(request.getMajorName())
                .insDate(new Date())
                .build();
        majorRepository.save(major);
        return new MajorDTO(major);
    }

    public MajorDTO update(UpdateMajorRequest request) {
        Major major = majorRepository.getReferenceById(request.getMajorId());
        major.setDepartment(depRepository.getReferenceById(request.getDepId()));
        major.setMajorName(request.getMajorName());
        majorRepository.save(major);
        return new MajorDTO(major);
    }

    public MajorDTO delete(MajorIdRequest request) {
        Major major = majorRepository.getReferenceById(request.getMajorId());
        major.setDelFlg(true);
        majorRepository.save(major);
        return new MajorDTO(major);
    }

    public List<MajorDTO> getAll(FromToIndexRequest request) {
        if(!request.isValid()) {
            return new ArrayList<>();
        }
        return MajorDTO.fromList(majorRepository.getAll(request.getLimit(), request.getOffset()));
    }


    public List<MajorDTO> getDeleted(FromToIndexRequest request) {
        if(!request.isValid()) {
            return new ArrayList<>();
        }
        return MajorDTO.fromList(majorRepository.getDeleted(request.getLimit(), request.getOffset()));
    }

    public MajorDTO getById(String majorId) {
        return new MajorDTO(majorRepository.getReferenceById(majorId));
    }

    public Integer getAllTotal() {
        return majorRepository.getAllTotal();
    }

    public Integer getDeletedTotal() {
        return majorRepository.getDeletedTotal();
    }

    public List<MajorDTO> getByDepId(String depId) {
        return MajorDTO.fromList(majorRepository.getByDepId(depId));
    }

    public List<MajorExtend> getAllForBoard(HttpServletRequest servletRequest) {
        return majorRepository.getAllForBoard();
    }

    public List<MajorExtend> createMany(CreateManyMajorRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        if(jwtService.isSuperAdmin(servletRequest)){
            List<Major> majors = generateMajor(request.getMajorInfo());
            majorRepository.saveAll(majors);
            var majorIds = majors.stream().map(Major::getMajorId).toList();
            return majorRepository.getMajorExtendByIds(majorIds);
        }
        throw new NoAuthorityToDoActionException();
    }

    private List<Major> generateMajor(List<CreateMajorRequest> majorInfos) {
        List<Major> majors = new ArrayList<>();
        for(var info : majorInfos) {
            var major = Major.builder()
                    .majorId("MAJ_"+ UUID.randomUUID())
                    .majorName(info.getMajorName())
                    .department(depRepository.getReferenceById(info.getDepId()))
                    .insDate(new Date())
                    .delFlg(false)
                    .build();
            majors.add(major);
        }
        return majors;
    }
}

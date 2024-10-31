package com.qn_org.backend.controllers.staff;

import com.qn_org.backend.models.Department;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateStaffInfoRequest {
    @NotNull
    private String staffKey;

    @Nullable
    private String depId;

    @Nullable
    private String firstName;

    @Nullable
    private String lastName;

    @Nullable
    private String fullName;

    @Nullable
    private String phoneNumber;

    @Nullable
    private Boolean isTeacher = true;
}

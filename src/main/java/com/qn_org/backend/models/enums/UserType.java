package com.qn_org.backend.models.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public enum UserType {
    UNDEFINED(0),
    TEACHER(1),
    STAFF(2),
    STUDENT(3);

    private final int value;

    // Constructor to assign the value
    UserType(int value) {
        this.value = value;
    }

    // Getter to retrieve the value
    public int getValue() {
        return value;
    }

    // Method to get UserType by value (optional)
    public static UserType fromValue(int value) {
        for (UserType type : UserType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return UNDEFINED; // Return UNDEFINED if no match is found
    }
}

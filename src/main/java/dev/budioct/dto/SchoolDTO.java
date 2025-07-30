package dev.budioct.dto;

import dev.budioct.model.School;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class SchoolDTO {

    @Getter
    @Setter
    @Builder
    public static class SchoolResponse {
        private Long id;
        private String name;
        private String address;
        private String phone;

        @Override
        public String toString() {
            return "SchoolResponse{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }

    public static SchoolResponse toSchoolResponse(School school) {
        return SchoolResponse.builder()
                .id(school.getId())
                .name(school.getName())
                .address(school.getAddress())
                .phone(school.getPhone())
                .build();
    }

}

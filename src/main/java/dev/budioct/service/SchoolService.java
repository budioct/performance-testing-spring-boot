package dev.budioct.service;

import dev.budioct.dto.SchoolDTO;
import dev.budioct.model.School;
import dev.budioct.repository.projection.SchoolView;

import java.util.List;

public interface SchoolService {
    List<School> getAllSchoolsAsEntity();
    List<SchoolDTO.SchoolResponse> getAllSchoolsAsDTO();
    List<SchoolView> getAllSchoolsAsView();
}

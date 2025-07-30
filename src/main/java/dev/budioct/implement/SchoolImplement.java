package dev.budioct.implement;

import dev.budioct.dto.SchoolDTO;
import dev.budioct.model.School;
import dev.budioct.repository.SchoolRepository;
import dev.budioct.repository.projection.SchoolView;
import dev.budioct.service.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class SchoolImplement implements SchoolService {

    private final SchoolRepository schoolRepository;

    public SchoolImplement(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Transactional(readOnly = true)
    public List<School> getAllSchoolsAsEntity() {
        List<School> list = schoolRepository.findAll();

        log.info("Loaded {} method getAllSchoolsAsEntity. Sample: {}", list.size(), list.get(0).toString());

        return list;
    }

    @Transactional(readOnly = true)
    public List<SchoolDTO.SchoolResponse> getAllSchoolsAsDTO() {
        List<SchoolDTO.SchoolResponse> list = schoolRepository
                .findAll()
                .stream()
                .map(SchoolDTO::toSchoolResponse)
                .toList();

        log.info("Loaded {} method getAllSchoolsAsDTO. Sample: {}", list.size(), list.get(0).toString());

        return list;
    }

    @Override
    public List<SchoolView> getAllSchoolsAsView() {
        List<SchoolView> list = schoolRepository.findAllAsView();

        log.info("Loaded {} method getAllSchoolsAsView. Sample: {}", list.size(), list.get(0).toString());

        return list;
    }

}

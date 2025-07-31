package dev.budioct.controller;

import dev.budioct.service.SchoolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schools")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping(
            path = "/list",
            produces = "application/json"
    )
    public ResponseEntity<?> getAllSchoolsAsEntity() {
        return ResponseEntity.ok(schoolService.getAllSchoolsAsEntity());
    }

    @GetMapping(
            path = "/list-dto",
            produces = "application/json"
    )
    public ResponseEntity<?> getAllSchoolsDTO() {
        return ResponseEntity.ok(schoolService.getAllSchoolsAsDTO());
    }

    @GetMapping(
            path = "/list-view",
            produces = "application/json"
    )
    public ResponseEntity<?> getAllSchoolsAsView() {
        return ResponseEntity.ok(schoolService.getAllSchoolsAsView());
    }

    @GetMapping(
            path = "/load-test",
            produces = "application/json"
    )
    public ResponseEntity<?> getAllSchoolsForLoadTesting() {
        // non cache
        return ResponseEntity.ok(schoolService.getAllSchoolsForLoadTesting());
    }

}

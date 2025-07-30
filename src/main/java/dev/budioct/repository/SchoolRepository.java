package dev.budioct.repository;

import dev.budioct.model.School;
import dev.budioct.repository.projection.SchoolView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Long> {

    @Query("SELECT s.id as id, s.name as name, s.address as address, s.phone as phone FROM School s")
    List<SchoolView> findAllAsView();

}
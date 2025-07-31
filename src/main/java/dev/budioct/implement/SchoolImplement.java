package dev.budioct.implement;

import dev.budioct.dto.SchoolDTO;
import dev.budioct.model.School;
import dev.budioct.repository.SchoolRepository;
import dev.budioct.repository.projection.SchoolView;
import dev.budioct.service.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SchoolImplement implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String CACHE_KEY_ENTITY = "schools:entity";
    private static final String CACHE_KEY_DTO = "schools:dto";
    private static final String CACHE_KEY_VIEW = "schools:view";
    private static final long CACHE_TTL = 5; // 5 minutes

    public SchoolImplement(SchoolRepository schoolRepository, RedisTemplate<String, Object> redisTemplate) {
        this.schoolRepository = schoolRepository;
        this.redisTemplate = redisTemplate;
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<School> getAllSchoolsAsEntity() {
        log.debug("Checking Redis cache for key: {}", CACHE_KEY_ENTITY);

        // 1. Check if data exists in Redis
        Object cachedData = redisTemplate.opsForValue().get(CACHE_KEY_ENTITY);
        if (cachedData != null) {
            List<School> cachedList = (List<School>) cachedData;
            log.info("Cache hit! Returning {} schools from Redis cache", cachedList.size());
            return cachedList;
        }

        // 2. If not in cache, query the database
        log.info("Cache miss! Querying database for schools");
        List<School> list = schoolRepository.findAll();

        // 3. Save to Redis with expiration
        log.debug("Saving {} schools to Redis with key: {}", list.size(), CACHE_KEY_ENTITY);
        redisTemplate.opsForValue().set(CACHE_KEY_ENTITY, list, CACHE_TTL, TimeUnit.MINUTES);

        log.info("Loaded {} schools from database. Sample: {}", list.size(),
                list.isEmpty() ? "No schools found" : list.get(0).toString());

        return list;
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<SchoolDTO.SchoolResponse> getAllSchoolsAsDTO() {
        log.debug("Checking Redis cache for key: {}", CACHE_KEY_DTO);

        Object cachedData = redisTemplate.opsForValue().get(CACHE_KEY_DTO);
        if (cachedData != null) {
            List<SchoolDTO.SchoolResponse> cachedList = (List<SchoolDTO.SchoolResponse>) cachedData;
            log.info("Cache hit! Returning {} school DTOs from Redis cache", cachedList.size());
            return cachedList;
        }

        log.info("Cache miss! Querying database for school DTOs");
        List<SchoolDTO.SchoolResponse> list = schoolRepository
                .findAll()
                .stream()
                .map(SchoolDTO::toSchoolResponse)
                .toList();

        log.debug("Saving {} school DTOs to Redis with key: {}", list.size(), CACHE_KEY_DTO);
        redisTemplate.opsForValue().set(CACHE_KEY_DTO, list, CACHE_TTL, TimeUnit.MINUTES);

        log.info("Loaded {} school DTOs from database. Sample: {}", list.size(),
                list.isEmpty() ? "No schools found" : list.getFirst().toString());

        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SchoolView> getAllSchoolsAsView() {
        log.debug("Checking Redis cache for key: {}", CACHE_KEY_VIEW);

        Object cachedData = redisTemplate.opsForValue().get(CACHE_KEY_VIEW);
        if (cachedData != null) {
            List<SchoolView> cachedList = (List<SchoolView>) cachedData;
            log.info("Cache hit! Returning {} school views from Redis cache", cachedList.size());
            return cachedList;
        }

        log.info("Cache miss! Querying database for school views");
        List<SchoolView> list = schoolRepository.findAllAsView();

        log.debug("Saving {} school views to Redis with key: {}", list.size(), CACHE_KEY_VIEW);
        redisTemplate.opsForValue().set(CACHE_KEY_VIEW, list, CACHE_TTL, TimeUnit.MINUTES);

        log.info("Loaded {} school views from database. Sample: {}", list.size(),
                list.isEmpty() ? "No schools found" : list.getFirst().toString());

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SchoolDTO.SchoolResponse> getAllSchoolsForLoadTesting() {
        log.info("Load testing: Directly querying database for schools (bypassing cache)");

        List<SchoolDTO.SchoolResponse> list = schoolRepository
                .findAll()
                .stream()
                .map(SchoolDTO::toSchoolResponse)
                .toList();

        log.info("Load testing: Loaded {} schools from database. Sample: {}", list.size(),
                list.isEmpty() ? "No schools found" : list.getFirst().toString());

        return list;
    }

}

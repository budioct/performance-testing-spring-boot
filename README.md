# 🚀 Spring Boot API Performance Testing with Redis Cache

Discover how **Spring Boot**, **Redis**, and **PostgreSQL** come together in a high-performance API application with caching capabilities! 💡

## Project Overview

This project demonstrates the performance benefits of implementing Redis caching in a Spring Boot application. It provides a practical example of how caching can significantly improve API response times under load.

<div align="center">
<img src="https://raw.githubusercontent.com/redis/redis-io/master/public/images/redis-white.png" alt="Redis" width="200"/>
</div>

## Features

✅ **Redis Caching Implementation** - See the performance difference with and without caching  
✅ **Multiple Response Types** - Compare Entity, DTO, and Projection performance  
✅ **Performance Testing with k6** - Ready-to-run test scripts for load testing  
✅ **Docker Containerization** - Easy setup with Docker Compose  
✅ **Flyway Database Migration** - Automatic database schema and data initialization

## Performance Testing Results

The project includes four API endpoints for performance comparison:

1. `/api/v1/schools/list` - Returns entities with Redis caching
2. `/api/v1/schools/list-dto` - Returns DTOs with Redis caching
3. `/api/v1/schools/list-view` - Returns view projections with Redis caching
4. `/api/v1/schools/load-test` - Returns DTOs without caching (for comparison)

## Prerequisites

Ensure you have the following installed before running the project:

- **Java**: 21
- **Docker**: 20.10+ (for running containers)
- **k6**: For running performance tests (optional)

### Dependencies

- **Spring Boot**: 3.5.4
- **Spring Data JPA**
- **Spring Data Redis**
- **PostgreSQL**
- **Flyway**
- **Lombok**
- **Maven**

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/budioct/performance-testing-spring-boot.git
   cd performance-testing-spring-boot
   ```

2. Build the application:
   ```bash
   mvn clean package
   ```

3. Start the Docker containers:
   ```bash
   docker-compose up -d
   ```

4. The application will be available at:
   ```
   http://localhost:8080
   ```

## API Testing

You can test the API endpoints using the provided `api_test.http` file:

```http
### GET request list schools return entity
GET http://localhost:8080/api/v1/schools/list

### GET request list schools return dto
GET http://localhost:8080/api/v1/schools/list-dto

### GET request list schools return view-projection
GET http://localhost:8080/api/v1/schools/list-view

### GET request list schools non cache
GET http://localhost:8080/api/v1/schools/load-test
```

## Performance Testing with k6

The project includes three k6 test scripts for performance testing:

1. `performance-test-first.js` - Tests the entity endpoint
2. `performance-test-second.js` - Tests the DTO endpoint
3. `performance-test-third.js` - Tests the view projection endpoint

To run a performance test:
```bash
   k6 run performance-test-first.js
```

Compare the results between cached and non-cached endpoints to see the performance difference.

## Redis Cache Implementation

The application implements Redis caching with a 5-minute TTL (Time To Live) for all cached endpoints. The caching logic follows this pattern:

1. Check if data exists in Redis cache
2. If it exists, return the cached data
3. If not, query the database
4. Store the result in Redis cache
5. Return the data

## Database Schema

The application uses a simple school database with the following schema:

```sql
CREATE TABLE tbl_school
(
    id      BIGINT       NOT NULL,
    name    VARCHAR(100) NOT NULL,
    address VARCHAR(200) NOT NULL,
    phone   VARCHAR(12)  NOT NULL,
    CONSTRAINT pk_tbl_school PRIMARY KEY (id)
);
```

## Logging Configuration

The application includes comprehensive logging with different levels:

- Development: Detailed logging with TRACE level for cache operations
- Production: Minimal logging with only WARN level messages

---

#### Wishing you success in your development efforts. May this project support and inspire you in building even better solutions.

Best regards,  
Budhi Octaviansyah
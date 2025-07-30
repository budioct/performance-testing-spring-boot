import http from 'k6/http';
import {check} from 'k6';
import {Rate} from 'k6/metrics';

// Custom metrics
const errorRateThree = new Rate('errors');

// Test configuration
export const options = {
    stages: [
        {duration: '10s', target: 50},   // Ramp up to 50 VUs over 10 seconds
        {duration: '50s', target: 100},  // Stay at 100 VUs for 50 seconds
        {duration: '10s', target: 0},    // Ramp down to 0 VUs over 10 seconds
    ],
    thresholds: {
        'http_req_duration': ['p(95)<200'], // 95th percentile should be below 200ms
        'http_req_failed': ['rate<0.05'],    // Error rate should be below 10%
        'errors': ['rate<0.05'],             // Custom error rate should be below 10%
    },
};

export default function () {
    // Make GET request to schools list endpoint
    const response = http.get('http://localhost:8080/api/v1/schools/list-view');

    // Record errors for custom metric
    errorRateThree.add(response.status !== 200);

    // Check response status and content
    check(response, {
        'status is 200': (r) => r.status === 200,
        'response time < 200ms': (r) => r.timings.duration < 200,
        'response has body': (r) => r.body && r.body.length > 0,
        'content type is JSON': (r) => r.headers['Content-Type'] && r.headers['Content-Type'].includes('application/json'),
    });
}

export function setup() {
    console.log('Starting performance teest for schools list endpoint');
    console.log('Target: http://localhost:8080/api/v1/schools/list-view');
    console.log('Scenario: 10s ramp up to 50 VUs, 50s at 100 VUs, 10s ramp down to 0 VUs');
}

export function teardown() {
    console.log('Performance test completed');
}
DROP TABLE work_experience IF EXISTS;
DROP TABLE candidate IF EXISTS;

CREATE TABLE candidate (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(50),
    location VARCHAR(255),
    experience_in_years INT,
    skills VARCHAR ARRAY
);

CREATE TABLE work_experience (
    id SERIAL PRIMARY KEY,
    candidate_id INT NOT NULL,
    company_name VARCHAR(255) NOT NULL,
    job_title VARCHAR(255) NOT NULL,
    start_date DATE,
    end_date DATE,
    description VARCHAR,
    technologies VARCHAR ARRAY,
    CONSTRAINT fk_candidate FOREIGN KEY (candidate_id) REFERENCES candidate(id) ON DELETE CASCADE
);

INSERT INTO candidate(name, email, phone, location, experience_in_years, skills)
    VALUES
    ('Alice Johnson', 'alice.johnson@example.com', '+1-555-987-6543', 'San Francisco, CA', 6, ARRAY['Java', 'Spring Boot', 'Hibernate', 'AWS']),
    ('Brian Carter',  'brian.carter@example.com',   '+1-555-234-5678', 'Austin, TX', 5, ARRAY['Go', 'gRPC', 'Kubernetes', 'PostgreSQL']);

INSERT INTO work_experience (candidate_id, company_name, job_title, start_date, end_date, description, technologies)
    VALUES
    (1, 'TechSphere Inc.', 'Senior Java Developer', DATE '2019-06-01', DATE '2023-07-31', 'Led the design and development of backend microservices for a high-traffic e-commerce platform using Java and Spring Boot. Improved API performance by 30%.', ARRAY['Java', 'Spring Boot', 'Hibernate', 'AWS', 'Docker']),
    (1, 'CloudEdge Solutions', 'Backend Developer', DATE '2016-05-01', DATE '2019-05-31', 'Developed scalable REST APIs and implemented CI/CD pipelines.', ARRAY['Java', 'Microservices', 'MySQL', 'Jenkins']),
    (2, 'StreamLogic Inc.', 'Golang Backend Engineer', DATE '2019-08-01', DATE '2023-02-28', 'Developed high-performance microservices using Golang and gRPC.', ARRAY['Go', 'gRPC', 'Kubernetes', 'Prometheus', 'Docker']),
    (2, 'NextGen Cloud Services', 'Software Engineer',  DATE '2017-06-01', DATE '2019-07-31', 'Built cloud-native backend solutions with Go.', ARRAY['Go', 'REST API', 'PostgreSQL', 'Terraform']);


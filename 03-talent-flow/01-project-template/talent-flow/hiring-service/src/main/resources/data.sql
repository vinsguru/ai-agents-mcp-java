DROP TABLE job_application IF EXISTS;

CREATE TABLE job_application (
    id SERIAL PRIMARY KEY,
    job_id INT NOT NULL,
    candidate_id INT NOT NULL,
    applied_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    resume TEXT NOT NULL,
    match_score INT,
    match_reasoning TEXT,
    CONSTRAINT uq_job_candidate UNIQUE (job_id, candidate_id)
);
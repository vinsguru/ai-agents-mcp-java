DROP TABLE job_skill IF EXISTS;
DROP TABLE job IF EXISTS;

CREATE TABLE job (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    location VARCHAR(255),
    is_remote BOOLEAN,
    salary_min INT,
    salary_max INT,
    posted_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    employer VARCHAR(255)
);

CREATE TABLE job_skill (
    id SERIAL PRIMARY KEY,
    job_id INT NOT NULL,
    skill VARCHAR(100),
    CONSTRAINT fk_job FOREIGN KEY (job_id) REFERENCES job(id) ON DELETE CASCADE
);

INSERT INTO job (id, title, description, location, is_remote, salary_min, salary_max, employer) VALUES
(1, 'Senior Software Engineer - Java', 'Lead the design and development of backend services using Java and Spring Boot.', 'New York, NY', TRUE, 105000, 140000, 'FinEdge Technologies'),
(2, 'Backend Developer - Golang', 'Develop high-performance microservices in Go for large-scale distributed systems.', 'Remote', FALSE, 95000, 125000, 'CloudWorks Labs'),
(3, 'Platform Engineer - Java', 'Own backend platform services powering millions of API requests daily.', 'Austin, TX', FALSE, 98000, 130000, 'DataMesh Corp.'),
(4, 'Software Engineer II (Golang)', 'Build secure and scalable APIs for real-time applications using Go.', 'Remote', TRUE, 90000, 115000, 'GoScale Systems'),
(5, 'Senior Java Backend Engineer', 'Architect and deliver cloud-native backend solutions using Java and Spring Cloud.', 'San Francisco, CA', FALSE, 120000, 150000, 'EdgeWave Solutions'),
(6, 'Principal Software Engineer (Go)', 'Drive backend architecture decisions and mentor team in Golang best practices.', 'Seattle, WA', FALSE, 130000, 160000, 'StreamLogic Inc.'),
(7, 'Backend Services Developer - Java', 'Implement business-critical backend services using Java and Hibernate.', 'Boston, MA', FALSE, 85000, 105000, 'BrightTech Consulting'),
(8, 'Golang API Specialist', 'Create performant API layers in Go for cloud-native architectures.', 'Remote', TRUE, 95000, 120000, 'BlueSky Digital'),
(9, 'Java Microservices Developer', 'Focus on building modular microservices leveraging Spring Boot and Kafka.', 'Chicago, IL', FALSE, 98000, 125000, 'InnoCore Systems'),
(10, 'Senior Software Engineer - Go', 'Develop and scale backend components for data-intensive applications using Golang.', 'Remote', TRUE, 110000, 140000, 'DataFlux Inc.'),
(11, 'Senior Frontend Engineer', 'Lead the development of responsive web applications using React and TypeScript.', 'San Francisco, CA', FALSE, 105000, 140000, 'PixelWave Studios'),
(12, 'UI Developer - Angular', 'Build intuitive and dynamic user interfaces using Angular and RxJS.', 'Remote', TRUE, 90000, 115000, 'BrightEdge Digital'),
(13, 'Frontend Software Engineer (Vue.js)', 'Work on complex UI components and state management for enterprise apps.', 'Austin, TX', FALSE, 80000, 100000, 'CloudSpark Labs'),
(14, 'React Developer', 'Develop high-performance client-side applications integrated with REST APIs.', 'New York, NY', FALSE, 85000, 120000, 'WebVision Technologies'),
(15, 'Frontend Architect', 'Define architecture and best practices for large-scale SPAs using modern JS frameworks.', 'Remote', TRUE, 120000, 155000, 'UIForge Inc.'),
(16, 'DevOps Engineer', 'Manage CI/CD pipelines and infrastructure automation using Terraform and Jenkins.', 'Seattle, WA', FALSE, 95000, 125000, 'CloudOps Solutions'),
(17, 'Site Reliability Engineer (SRE)', 'Ensure platform reliability and performance using Kubernetes and monitoring tools.', 'Remote', TRUE, 110000, 140000, 'ScaleSphere Technologies'),
(18, 'Infrastructure Automation Specialist', 'Automate infrastructure deployments and manage IaC using Terraform and Ansible.', 'Austin, TX', FALSE, 90000, 110000, 'InfraEdge Consulting'),
(19, 'Cloud DevOps Architect', 'Architect cloud-native DevOps solutions leveraging AWS and Kubernetes.', 'San Francisco, CA', FALSE, 125000, 160000, 'NextGen Cloud Services'),
(20, 'Build and Release Engineer', 'Manage software releases, optimize build pipelines, and implement CI/CD best practices.', 'Remote', TRUE, 95000, 120000, 'PipelinePro Inc.'),
(21, 'Engineering Manager - Backend Systems', 'Lead a team of backend engineers to build scalable microservices using Java and Go.', 'New York, NY', FALSE, 140000, 180000, 'TechSphere Inc.'),
(22, 'Frontend Engineering Manager', 'Oversee the development of user-facing applications, mentor UI engineers, and ensure UI/UX excellence.', 'Remote', TRUE, 135000, 170000, 'UIForge Labs');

INSERT INTO job_skill (job_id, skill) VALUES
(1,'Java'),(1,'Spring Boot'),(1,'Docker'),(1,'AWS'),
(2,'Go'),(2,'gRPC'),(2,'PostgreSQL'),(2,'Kubernetes'),
(3,'Java'),(3,'Spring Cloud'),(3,'Kafka'),(3,'Kubernetes'),
(4,'Go'),(4,'REST API'),(4,'Docker'),(4,'Redis'),
(5,'Java'),(5,'Spring Cloud'),(5,'Microservices'),(5,'AWS'),
(6,'Go'),(6,'gRPC'),(6,'Kubernetes'),(6,'CI/CD'),
(7,'Java'),(7,'Hibernate'),(7,'MySQL'),(7,'Docker'),
(8,'Go'),(8,'REST API'),(8,'PostgreSQL'),(8,'AWS'),
(9,'Java'),(9,'Spring Boot'),(9,'Kafka'),(9,'Docker'),
(10,'Go'),(10,'Microservices'),(10,'Redis'),(10,'Kubernetes'),
(11,'React'),(11,'TypeScript'),(11,'Redux'),(11,'Webpack'),
(12,'Angular'),(12,'RxJS'),(12,'TypeScript'),(12,'SCSS'),
(13,'Vue.js'),(13,'Vuex'),(13,'JavaScript'),(13,'Tailwind CSS'),
(14,'React'),(14,'Next.js'),(14,'TypeScript'),(14,'GraphQL'),
(15,'React'),(15,'Angular'),(15,'Webpack'),(15,'Performance Optimization'),
(16,'Terraform'),(16,'Jenkins'),(16,'AWS'),(16,'Docker'),
(17,'Kubernetes'),(17,'Prometheus'),(17,'Grafana'),(17,'AWS'),
(18,'Terraform'),(18,'Ansible'),(18,'AWS'),(18,'Bash Scripting'),
(19,'AWS'),(19,'Kubernetes'),(19,'Terraform'),(19,'CI/CD'),
(20,'Jenkins'),(20,'GitLab CI'),(20,'Docker'),(20,'Helm'),
(21,'Leadership'),(21,'Java'),(21,'Golang'),(21,'Microservices'),(21,'AWS'),
(22,'React'),(22,'Angular'),(22,'Leadership'),(22,'Agile Practices');
CREATE TABLE users (
                       id INTEGER PRIMARY KEY AUTO_INCREMENT,
                       manager TINYINT(1), -- Is the user a manager (0 or 1)
                       username VARCHAR(255) NOT NULL UNIQUE, -- User credentials
                       password VARCHAR(255) NOT NULL, -- Implement hashing later
                       manager_id INTEGER, -- Id of manager
                       FOREIGN KEY (manager_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE projects (
                          id INTEGER PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255), -- Project name
                          description TEXT, -- Project description
                          manager_id INTEGER, -- Project manager
                          start_date DATE, -- Contract start date
                          end_date DATE, -- Contract end date
                          FOREIGN KEY (manager_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Time tables
CREATE TABLE time_table (
                            id INTEGER PRIMARY KEY AUTO_INCREMENT,
                            user_id INTEGER, -- Used as foreign key for users
                            project_id INTEGER, -- Used as foreign key for project
                            start_time DATETIME, -- Start of logging
                            end_time DATETIME, -- End of logging
                            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

CREATE TABLE invoices (
                          id INTEGER PRIMARY KEY AUTO_INCREMENT,
                          number VARCHAR(255) UNIQUE, -- Invoice number
                          issue_date DATE, -- Invoice date
                          amount DOUBLE, -- Invoice amount
                          description TEXT, -- Invoice description
                          project_id INTEGER, -- Foreign key for project
                          FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE SET NULL
);

CREATE TABLE assignments (
                             id INTEGER PRIMARY KEY AUTO_INCREMENT, -- Assignment id
                             user_id INTEGER NOT NULL, -- User id
                             project_id INTEGER NOT NULL, -- Project id
                             assigned_by INTEGER, -- Manager id
                             assigned_date DATE, -- Assignment date
                             FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                             FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
                             FOREIGN KEY (assigned_by) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE SPRING_SESSION (
                                PRIMARY_ID CHAR(36) NOT NULL,
                                SESSION_ID CHAR(36) NOT NULL,
                                CREATION_TIME BIGINT NOT NULL,
                                LAST_ACCESS_TIME BIGINT NOT NULL,
                                MAX_INACTIVE_INTERVAL INT NOT NULL,
                                EXPIRY_TIME BIGINT NOT NULL,
                                PRINCIPAL_NAME VARCHAR(100),
                                CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (PRINCIPAL_NAME);

-- Insert initial data
INSERT INTO users (username, password, manager) VALUES ('admin1', '$2y$10$f48C9/lnCr63yFnGGmAT1eXpkTwzvdItqffxTWDaT/Xj7Qfq2J8nO', 1);
INSERT INTO projects (name, description, manager_id, start_date, end_date) VALUES ('Time Quill', 'Time tracking app', 1, '2023-05-01', '2023-05-31');
INSERT INTO assignments (user_id, project_id, assigned_by, assigned_date) VALUES (1, 1, 1, '2023-05-01');

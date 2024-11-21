drop table if exists assignments;
drop table if exists invoices;
drop table if exists time_table;
drop table if exists projects;
drop table if exists users;

CREATE TABLE IF NOT EXISTS users (
                                     id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                     manager TINYINT(1), -- Is the user a manager (0 or 1)
                                     username VARCHAR(255) NOT NULL UNIQUE, -- User credentials
                                     password VARCHAR(255) NOT NULL, -- Implement hashing later
                                     manager_id INTEGER, -- Id of manager
                                     FOREIGN KEY (manager_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Create the projects table
CREATE TABLE IF NOT EXISTS projects (
                                        id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                        name VARCHAR(255), -- Project name
                                        description TEXT, -- Project description
                                        manager_id INTEGER, -- Project manager
                                        client_name VARCHAR(255), -- Client name
                                        start_date DATE, -- Contract start date
                                        end_date DATE, -- Contract end date
                                        FOREIGN KEY (manager_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Create the time_table
CREATE TABLE IF NOT EXISTS time_table (
                                          id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                          user_id INTEGER, -- Foreign key for users
                                          project_id INTEGER, -- Foreign key for project
                                          assignment_id INTEGER, -- Foreign key for project
                                          start_time DATETIME, -- Start of logging
                                          end_time DATETIME, -- End of logging
                                          worked_hours DOUBLE GENERATED ALWAYS AS (TIMESTAMPDIFF(SECOND, start_time, end_time) / 3600) STORED, -- Automatically calculate worked hours
                                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                          FOREIGN KEY (assignment_id) REFERENCES assignments(id) ON DELETE CASCADE,
                                          FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS assignments (
                                           id INTEGER PRIMARY KEY AUTO_INCREMENT, -- Assignment id
                                           user_id INTEGER NOT NULL, -- User id
                                           project_id INTEGER NOT NULL, -- Project id
                                           rate DOUBLE, -- Assignment Hourly rate
                                           description TEXT, -- Assignment desc
                                           assigned_by INTEGER, -- Manager id
                                           assigned_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                           FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                           FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
                                           FOREIGN KEY (assigned_by) REFERENCES users(id) ON DELETE SET NULL
);

-- Insert initial data
INSERT INTO users (username, password, manager) VALUES ('admin1', '$2y$10$f48C9/lnCr63yFnGGmAT1eXpkTwzvdItqffxTWDaT/Xj7Qfq2J8nO', 1);
INSERT INTO users (username, password, manager) VALUES ('user1', '$2y$10$f48C9/lnCr63yFnGGmAT1eXpkTwzvdItqffxTWDaT/Xj7Qfq2J8nO', 0);
INSERT INTO projects (name, description, manager_id, client_name, start_date, end_date) VALUES ('Time Quill', 'Time tracking app', 1, 'Client A', '2023-05-01', '2023-05-31');
INSERT INTO assignments (id, user_id, project_id, rate, description, assigned_by) VALUES (1, 1, 1, 100, 'Description', 1);
INSERT INTO assignments (id, user_id, project_id, rate, description, assigned_by) VALUES (2, 1, 1, 105, 'Description2', 1);

CREATE TABLE IF NOT EXISTS users (
                                     id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                     manager TINYINT(1), -- Is the user a manager (0 or 1)
                                     username VARCHAR(255) NOT NULL UNIQUE, -- User credentials
                                     password VARCHAR(255) NOT NULL, -- Implement hashing later
                                     manager_id INTEGER, -- Id of manager
                                     is_locked TINYINT(1),
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

-- Create the time_table
CREATE TABLE IF NOT EXISTS time_table (
                                          id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                          user_id INTEGER, -- Foreign key for users
                                          project_id INTEGER, -- Foreign key for project
                                          assignment_id INTEGER, -- Foreign key for assignment
                                          start_time TIME, -- Start of logging (in 24-hour time format)
                                          end_time TIME, -- End of logging (in 24-hour time format)
                                          worked_hours DOUBLE, -- Worked hours (calculated using a trigger)
                                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                          FOREIGN KEY (assignment_id) REFERENCES assignments(id) ON DELETE CASCADE,
                                          FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

DELIMITER $$

CREATE TRIGGER before_insert_time_table
    BEFORE INSERT ON time_table
    FOR EACH ROW
BEGIN
    SET NEW.worked_hours = TIMESTAMPDIFF(SECOND, CONCAT(CURDATE(), ' ', NEW.start_time), CONCAT(CURDATE(), ' ', NEW.end_time)) / 3600;
END $$

DELIMITER ;

INSERT INTO users (username, password, manager, manager_id, is_locked) VALUES ('admin1', '$2y$10$f48C9/lnCr63yFnGGmAT1eXpkTwzvdItqffxTWDaT/Xj7Qfq2J8nO', 1, NULL, 0);

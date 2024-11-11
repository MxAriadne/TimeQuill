CREATE TABLE users (
    id integer primary key autoincrement,
    -- Is the user a manager
    manager boolean,
    -- User credentials
    username text not null unique,
    -- Implement hashing later
    password text not null,
    -- Id of manager
    manager_id integer,
    FOREIGN KEY (manager_id) REFERENCES users(id)
);

CREATE TABLE projects (
    id integer primary key autoincrement,
    -- Project name
    name text,
    -- Project description
    description text,
    -- Project manager
    manager_id text,
    -- Contract start date
    start_date text,
    -- Contract end date
    end_date text,
    FOREIGN KEY (manager_id) REFERENCES users(id)
);

-- Time tables
CREATE TABLE time_table (
    id integer primary key autoincrement,
    -- Used as foreign key for users
    user_id integer,
    -- Used as foreign key for project
    project_id integer,
    -- Start of logging
    start_time text,
    -- End of logging
    end_time text,
    FOREIGN KEY (user_id) REFERENCES users(id),
    -- Foreign key for project
    FOREIGN KEY (project_id) REFERENCES projects(id)
);

CREATE TABLE invoices (
    id integer primary key autoincrement,
    -- Invoice number
    number text unique,
    -- Invoice date
    issue_date text,
    -- Invoice amount
    amount double,
    -- Invoice description
    description text,
    -- Foreign key for project
    project_id integer,
    FOREIGN KEY (project_id) REFERENCES projects(id)
);

CREATE TABLE assignments (
    -- Assignment id
    id integer primary key autoincrement,
    -- User id
    user_id integer NOT NULL,
    -- Project id
    project_id integer NOT NULL,
    -- Manager id
    assigned_by integer,
    -- Assignment date
    assigned_date text,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (project_id) REFERENCES projects(id),
    FOREIGN KEY (assigned_by) REFERENCES users(id)
);

insert into users (username, password, manager) values ('admin', 'admin', 1);
insert into projects (name, description, manager_id, start_date, end_date) values ('Time Quill', 'Time tracking app', 1, '2023-05-01', '2023-05-31');
insert into assignments (user_id, project_id, assigned_by, assigned_date) values (1, 1, 1, '2023-05-01');

CREATE TABLE user_leave_management.lm_user (
    id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    designation VARCHAR(100) NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    activated TINYINT(1) NOT NULL,
    created DATETIME NOT NULL,
    updated DATETIME NULL,
    version INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT unique_username UNIQUE (username)
);

CREATE TABLE user_leave_management.lm_user_management (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    team_lead_id INT NOT NULL,
    created DATETIME NOT NULL,
    updated DATETIME NULL,
    version INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user_leave_management.lm_user(id),
    FOREIGN KEY (team_lead_id) REFERENCES user_leave_management.lm_user(id)
);

CREATE TABLE user_leave_management.lm_leave_stat (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    sick_leave_count INT NOT NULL,
    casual_leave_count INT NOT NULL,
    created DATETIME NOT NULL,
    updated DATETIME NULL,
    version INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user_leave_management.lm_user(id)
);

CREATE TABLE user_leave_management.lm_leave_request (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    leave_type VARCHAR(100) NOT NULL,
    leave_status VARCHAR(100) NOT NULL,
    note varchar(100) NOT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    created DATETIME NOT NULL,
    updated DATETIME NULL,
    version INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user_leave_management.lm_user(id)
);

CREATE TABLE user_leave_management.lm_notification (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    message varchar (100) NOT NULL,
    seen TINYINT(1) NOT NULL,
    created DATETIME NOT NULL,
    updated DATETIME NULL,
    version INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user_leave_management.lm_user(id)
);

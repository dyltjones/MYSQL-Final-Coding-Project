DROP TABLE IF EXISTS project_category;
DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS step;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS project;


CREATE TABLE project (
    project_id INT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(128) NOT NULL,
    estimated_hours DECIMAL(7,2),
    actual_hours DECIMAL(7,2),
    difficulty INT,
    notes TEXT
);

CREATE TABLE category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(128) NOT NULL
);

CREATE TABLE step (
    step_id INT AUTO_INCREMENT PRIMARY KEY,
    project_id INT NOT NULL,
    step_text TEXT NOT NULL,
    step_order INT NOT NULL,
    FOREIGN KEY (project_id) REFERENCES project(project_id) ON DELETE CASCADE
);

CREATE TABLE material (
    material_id INT AUTO_INCREMENT PRIMARY KEY,
    project_id INT NOT NULL,
    material_name VARCHAR(128) NOT NULL,
    num_required INT,
    cost DECIMAL(7,2),
    FOREIGN KEY (project_id) REFERENCES project(project_id) ON DELETE CASCADE
);

CREATE TABLE project_category (
    project_id INT NOT NULL,
    category_id INT NOT NULL,
    UNIQUE(project_id, category_id),
    FOREIGN KEY (project_id) REFERENCES project(project_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES category(category_id) ON DELETE CASCADE    
);

-- Insert a new project witu project 1 values 
INSERT INTO project (project_name, estimated_hours, actual_hours, difficulty, notes) VALUES
('Hang a door', 4.5, 5,  3, 'User hangers from Home Depot');


--  adds a new category called 'Doors and Windows' and 'Repairs
INSERT INTO category (category_name) VALUES ('Doors and Windows');
INSERT INTO category (category_name) VALUES ('Repairs');


-- This query retrieves all project IDs from the project table, allowing you to identify existing projects.
SELECT * FROM project;

-- inserts materials required for Project 1 into the material table.
INSERT INTO material (project_id, material_name, num_required) VALUES
(1, 'Door in frame', 1),
(1, 'Package of door hangers from Home Depot', 1),
(1, '2-inch screws', 20);


-- inserts steps for Project 1 into the step table.
-- The steps are added with a specific order indicated by the step_order column.

INSERT INTO step (project_id, step_text, step_order)
VALUES
(1, 'Screw door hangers on the top and bottom of each side of the door frame', 1),
(1, 'Attach hinges to the door', 2);


--  associates Project 1 with the categories 'Doors and Windows' and 'Repairs' in the project_category table.
--  links the project_id of Project 1 with the category_id of each category.
INSERT INTO project_category (project_id, category_id) VALUES
(1, (SELECT category_id FROM category WHERE category_name = 'Doors and Windows')),
(1, (SELECT category_id FROM category WHERE category_name = 'Repairs'));



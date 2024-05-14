
--  adds a new category called 'Doors and Windows' and 'Repairs
INSERT INTO category (category_name) VALUES ('Doors and Windows');
INSERT INTO category (category_name) VALUES ('Repairs');


-- This query retrieves all project IDs from the project table, allowing you to identify existing projects.
SELECT * FROM project;


-- inserts materials required for Project 1 into the material table.
INSERT INTO material (project_id, material_name, num_required)
VALUES
(1, 'Door in frame', 1);
(1, 'Package of door hangers from Home Depot', 1);
(1, '2-inch screws', 20);


-- inserts steps for Project 1 into the step table.
-- The steps are added with a specific order indicated by the step_order column.

INSERT INTO step (project_id, step_text, step_order)
VALUES
(1, 'Screw door hangers on the top and bottom of each side of the door frame', 1),
(1, 'Attach hinges to the door', 2);


--  associates Project 1 with the categories 'Doors and Windows' and 'Repairs' in the project_category table.
--  links the project_id of Project 1 with the category_id of each category.
INSERT INTO project_category (project_id, category_id)
VALUES
(1, (SELECT category_id FROM category WHERE category_name = 'Doors and Windows')),
(1, (SELECT category_id FROM category WHERE category_name = 'Repairs'));



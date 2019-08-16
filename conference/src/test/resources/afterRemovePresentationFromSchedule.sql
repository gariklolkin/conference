INSERT INTO presentation
SELECT 1001 AS id, '11:15' AS end_time, '10:00' AS start_time, 'Andy Wilkinson' AS author, 'Spring Data REST' AS title, 11 AS hall_id
WHERE NOT EXISTS (SELECT 1 FROM presentation WHERE id = 1001);

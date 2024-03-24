SELECT f.id AS id, f.name AS name, 
    CASE
        WHEN t.parent_id='176a53bc-544a-4d93-85da-280f540d0bb3' AND COUNT(t.id) > 0 
        THEN ARRAY_AGG(ROW(t.name, t.id))  ELSE NULL
    END AS children
FROM folder f 
LEFT JOIN task t ON f.id = t.parent_id
WHERE  f.parent='176a53bc-544a-4d93-85da-280f540d0bb3' OR f.id='176a53bc-544a-4d93-85da-280f540d0bb3'
GROUP BY f.id, t.parent_id;



insert into task (parent_id, name) values('68c1684d-25c7-4b02-ace3-126fac890e67', 'eleniyan');
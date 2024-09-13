--TRUNCATE TABLE environments;
INSERT INTO environments (id, name)
VALUES
    (1, 'Dev1'),
    (2, 'Dev2'),
    (3, 'Dev3'),
    (4, 'QA1'),
    (5, 'QA2'),
    (6, 'QA3')
ON CONFLICT (id) DO NOTHING;

--TRUNCATE TABLE clusters;
INSERT INTO clusters (id, name)
VALUES
    (1, 'Profile Services (VMCP services)'),
    (2, 'Onboarding Services (OBS)'),
    (3, 'PAS Services (VCA services)'),
    (4, 'VDCA Gateway (VDCA external)'),
    (5, 'DPS Services'),
    (6, 'Mobile Services'),
    (7, 'PIPS'),
    (8, 'VDCA Mock bank'),
    (9, 'VDES'),
    (10, 'VGES'),
    (11, 'VMACS'),
    (12, 'VMPRS')
ON CONFLICT (id) DO NOTHING;

INSERT INTO userGroups (id, name)
VALUES
    (1, 'MEP BE Warsaw'),
    (2, 'MEP BE Austin'),
    (3, 'MEP BE Bangalore'),
    (4, 'MEP FE'),
    (5, 'RE'),
    (6, 'RISE')
ON CONFLICT (id) DO NOTHING;

INSERT INTO users (id, username, password)
VALUES
    (1, 'Radek', 'radek123')
ON CONFLICT (id) DO NOTHING;

INSERT INTO usergroups_users (usergroup_id, user_id)
VALUES
    (1, 1),
    (2, 1),
    (3, 1),
    (4, 1),
    (5, 1),
    (6, 1);

INSERT INTO reservations (usergroup_id, environment_id, cluster_id, start_time, end_time)
VALUES
    (1, 1, 1, '2024-08-15T13:45:30Z', '2024-08-15T15:45:30Z'),
    (2, 2, 1, '2024-08-15T14:45:30', '2024-08-15T16:45:30'),
    (3, 3, 1, '2024-08-15T12:15:00', '2024-08-15T15:15:00')
ON CONFLICT (id) DO NOTHING;

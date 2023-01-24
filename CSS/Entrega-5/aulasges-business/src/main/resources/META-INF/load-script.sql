DELETE FROM Facility
DELETE FROM Facility_SportModality
DELETE FROM Lesson
DELETE FROM LESSON_DAYS_OF_WEEK
DELETE FROM Occupation
DELETE FROM Registration
DELETE FROM SportModality
DELETE FROM User

INSERT INTO SportModality (ID, NAME, MINDURATION, COSTPERHOUR) VALUES (1,'Pilates', 50, 7)
INSERT INTO SportModality (ID, NAME, MINDURATION, COSTPERHOUR) VALUES (2, 'Step', 45, 10)
INSERT INTO SportModality (ID, NAME, MINDURATION, COSTPERHOUR) VALUES (3, 'GAP', 50, 10)
INSERT INTO SportModality (ID, NAME, MINDURATION, COSTPERHOUR) VALUES (4, 'Indoor Cycling', 55, 10)
INSERT INTO SportModality (ID, NAME, MINDURATION, COSTPERHOUR) VALUES (5, 'Hidroginastica', 45, 15)

INSERT INTO Facility (ID, NAME, MAXCAPACITY, FACILITYTYPE) VALUES (1, 'Estudio 1', 20, 'ESTUDIO')
INSERT INTO Facility (ID, NAME, MAXCAPACITY, FACILITYTYPE) VALUES (2, 'Estudio 2', 15, 'ESTUDIO')
INSERT INTO Facility (ID, NAME, MAXCAPACITY, FACILITYTYPE) VALUES (3, 'Biclas', 10, 'SALA_DE_BICICLETAS')
INSERT INTO Facility (ID, NAME, MAXCAPACITY, FACILITYTYPE) VALUES (4, 'Piscina 25', 20, 'PISCINA')

INSERT INTO User (ID, USERID, NAME, NIF) VALUES (1, 1, 'Ulisses', 223842389)
INSERT INTO User (ID, USERID, NAME, NIF) VALUES (2, 2, 'David', 256039682)
INSERT INTO User (ID, USERID, NAME, NIF) VALUES (3, 3, 'Teresa', 269901841)
INSERT INTO User (ID, USERID, NAME, NIF) VALUES (4, 4, 'Querubim', 197672337)
INSERT INTO User (ID, USERID, NAME, NIF) VALUES (5, 5, 'Cícero', 221057552)

INSERT INTO Facility_SportModality (Facility_id, sportModalities_id) VALUES (1, 1)
INSERT INTO Facility_SportModality (Facility_id, sportModalities_id) VALUES (1, 2)
INSERT INTO Facility_SportModality (Facility_id, sportModalities_id) VALUES (1, 3)
INSERT INTO Facility_SportModality (Facility_id, sportModalities_id) VALUES (2, 1)
INSERT INTO Facility_SportModality (Facility_id, sportModalities_id) VALUES (3, 4)
INSERT INTO Facility_SportModality (Facility_id, sportModalities_id) VALUES (4, 5)

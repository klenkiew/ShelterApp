DELIMITER $$
-- -----------------------------------------------------------------------------------------------------------------------------
--     Triggery utrzymujace spojnosc kolumn zliczajacych LiczbaZachorowan i LiczbaZgonow w tabeli Choroba
-- ---------------------------------------------------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS ChorobaLiczbaZachorowanInsert;
CREATE TRIGGER ChorobaLiczbaZachorowanInsert
AFTER INSERT ON HistoriaChorob
FOR EACH ROW
BEGIN
  UPDATE Choroba SET LiczbaZachorowan = LiczbaZachorowan + 1,
  LiczbaZgonow = LiczbaZgonow + NEW.CzySmiertelna
  WHERE Choroba.Id = NEW.ChorobaId;
END;

DROP TRIGGER IF EXISTS ChorobaLiczbaZachorowanDelete;
CREATE TRIGGER ChorobaLiczbaZachorowanDelete
AFTER DELETE ON HistoriaChorob
FOR EACH ROW
BEGIN
  UPDATE Choroba SET LiczbaZachorowan = LiczbaZachorowan - 1,
  LiczbaZgonow = LiczbaZgonow - OLD.CzySmiertelna
  WHERE Choroba.Id = OLD.ChorobaId;
END;

DROP TRIGGER IF EXISTS ChorobaLiczbaZachorowanUpdate;
CREATE TRIGGER ChorobaLiczbaZachorowanUpdate
AFTER UPDATE ON HistoriaChorob
FOR EACH ROW
BEGIN
  IF NEW.ChorobaId != OLD.ChorobaId THEN
  BEGIN
    UPDATE Choroba SET LiczbaZachorowan = LiczbaZachorowan + 1,
    LiczbaZgonow = LiczbaZgonow + NEW.CzySmiertelna 
    WHERE Id = NEW.ChorobaId;
    UPDATE Choroba SET LiczbaZachorowan = LiczbaZachorowan - 1 ,
    LiczbaZgonow = LiczbaZgonow - OLD.CzySmiertelna
    WHERE Id = OLD.ChorobaId;
  END;
  ELSEIF NEW.CzySmiertelna != OLD.CzySmiertelna THEN
    UPDATE Choroba SET LiczbaZgonow = LiczbaZgonow + NEW.CzySmiertelna - OLD.CzySmiertelna
    WHERE Id = NEW.ChorobaId;
  END IF;
END; 
-- ---------------------------------------------------------------------------------------------------------------------------
--
-- ---------------------------------------------------------------------------------------------------------------------------



-- ---------------------------------------------------------------------------------------------------------------------------
-- -- Dla kazdej choroby przechowuje statystyki liczby zachorowan i zgonow
-- -- ogolnie, dla kazdego roku i miesiaca
-- -- jesli rok = 0 to dotyczy calosci zachorowan, a jesli miesiac = 0 a rok
-- -- np. 2016 to dotyczy calego 2016 roku
-- ---------------------------------------------------------------------------------------------------------------------------

DROP TRIGGER IF EXISTS StatystykaChorobInsert;
CREATE TRIGGER StatystykaChorobInsert
AFTER INSERT ON HistoriaChorob
FOR EACH ROW
BEGIN
  UPDATE StatystykaChorob SET LiczbaZachorowan = LiczbaZachorowan + 1,
  LiczbaZgonow = LiczbaZgonow + NEW.CzySmiertelna
  WHERE Rok = 0 AND ChorobaId = NEW.ChorobaId;
  
  IF ROW_COUNT() = 0 THEN
  INSERT INTO StatystykaChorob 
  VALUES (NEW.ChorobaId, 0, 0, 1, NEW.CzySmiertelna);
  END IF;
  
  UPDATE StatystykaChorob SET LiczbaZachorowan = LiczbaZachorowan + 1,
  LiczbaZgonow = LiczbaZgonow + NEW.CzySmiertelna
  WHERE Rok = EXTRACT(YEAR FROM NEW.DataZachorowania) AND Miesiac = 0 AND ChorobaId = NEW.ChorobaId;
  
  IF ROW_COUNT() = 0 THEN
  INSERT INTO StatystykaChorob 
  VALUES (NEW.ChorobaId, EXTRACT(YEAR FROM NEW.DataZachorowania), 0, 1, NEW.CzySmiertelna);
  END IF;
  
  UPDATE StatystykaChorob SET LiczbaZachorowan = LiczbaZachorowan + 1,
  LiczbaZgonow = LiczbaZgonow + NEW.CzySmiertelna
  WHERE Rok = EXTRACT(YEAR FROM NEW.DataZachorowania) AND Miesiac = EXTRACT(MONTH FROM NEW.DataZachorowania) 
  AND ChorobaId = NEW.ChorobaId;
  
  IF ROW_COUNT() = 0 THEN
  INSERT INTO StatystykaChorob 
  VALUES (NEW.ChorobaId, EXTRACT(YEAR FROM NEW.DataZachorowania), EXTRACT(MONTH FROM NEW.DataZachorowania), 1, NEW.CzySmiertelna);
  END IF;
END;

DROP TRIGGER IF EXISTS StatystykaChorobDelete;
CREATE TRIGGER StatystykaChorobDelete
AFTER DELETE ON HistoriaChorob
FOR EACH ROW
BEGIN
  UPDATE StatystykaChorob SET LiczbaZachorowan = LiczbaZachorowan - 1,
  LiczbaZgonow = LiczbaZgonow - OLD.CzySmiertelna
  WHERE ChorobaId = OLD.ChorobaId AND (Rok = 0 OR 
  Rok = EXTRACT(YEAR FROM OLD.DataZachorowania) AND 
  (Miesiac = 0 OR Miesiac = EXTRACT(MONTH FROM OLD.DataZachorowania)));
END;

DROP TRIGGER IF EXISTS StatystykaChorobUpdate;
CREATE TRIGGER StatystykaChorobUpdate
AFTER UPDATE ON HistoriaChorob
FOR EACH ROW
BEGIN
  IF NEW.ChorobaId != OLD.ChorobaId THEN
  BEGIN
  -- to samo co w StatystykaChorobInsert, mozna by przeniesc do jakiejs procedury
  -- i wywolywac
    UPDATE StatystykaChorob SET LiczbaZachorowan = LiczbaZachorowan + 1,
    LiczbaZgonow = LiczbaZgonow + NEW.CzySmiertelna
    WHERE Rok = 0 AND ChorobaId = NEW.ChorobaId;
    
    IF ROW_COUNT() = 0 THEN
    INSERT INTO StatystykaChorob 
    VALUES (NEW.ChorobaId, 0, 0, 1, NEW.CzySmiertelna);
    END IF;
    
    UPDATE StatystykaChorob SET LiczbaZachorowan = LiczbaZachorowan + 1,
    LiczbaZgonow = LiczbaZgonow + NEW.CzySmiertelna
    WHERE Rok = EXTRACT(YEAR FROM NEW.DataZachorowania) AND Miesiac = 0 AND ChorobaId = NEW.ChorobaId;
    
    IF ROW_COUNT() = 0 THEN
    INSERT INTO StatystykaChorob 
    VALUES (NEW.ChorobaId, EXTRACT(YEAR FROM NEW.DataZachorowania), 0, 1, NEW.CzySmiertelna);
    END IF;
    
    UPDATE StatystykaChorob SET LiczbaZachorowan = LiczbaZachorowan + 1,
    LiczbaZgonow = LiczbaZgonow + NEW.CzySmiertelna
    WHERE Rok = EXTRACT(YEAR FROM NEW.DataZachorowania) AND Miesiac = EXTRACT(MONTH FROM NEW.DataZachorowania) 
    AND ChorobaId = NEW.ChorobaId;
    
    IF ROW_COUNT() = 0 THEN
    INSERT INTO StatystykaChorob 
    VALUES (NEW.ChorobaId, EXTRACT(YEAR FROM NEW.DataZachorowania), EXTRACT(MONTH FROM NEW.DataZachorowania), 1, NEW.CzySmiertelna);
    END IF;
    
    -- zmniejszamy wartosci dotyczace starej choroby
    UPDATE StatystykaChorob SET LiczbaZachorowan = LiczbaZachorowan - 1,
    LiczbaZgonow = LiczbaZgonow - OLD.CzySmiertelna
    WHERE ChorobaId = OLD.ChorobaId AND (Rok = 0 OR 
    Rok = EXTRACT(YEAR FROM OLD.DataZachorowania) AND 
    (Miesiac = 0 OR Miesiac = EXTRACT(MONTH FROM OLD.DataZachorowania)));
    END;
    
  ELSEIF NEW.CzySmiertelna != OLD.CzySmiertelna THEN
    UPDATE Choroba SET LiczbaZgonow = LiczbaZgonow + NEW.CzySmiertelna - OLD.CzySmiertelna
    WHERE ChorobaId = NEW.ChorobaId AND (Rok = 0 OR 
    Rok = EXTRACT(YEAR FROM NEW.DataZachorowania) AND 
    (Miesiac = 0 OR Miesiac = EXTRACT(MONTH FROM NEW.DataZachorowania)));
  END IF;
END; 

DROP TRIGGER IF EXISTS SzczepionkaUstawChorobaNazwa;
CREATE TRIGGER SzczepionkaUstawChorobaNazwa
BEFORE INSERT ON Szczepionka
FOR EACH ROW
BEGIN
  DECLARE NazwaChoroby VARCHAR(50);
  SELECT Nazwa INTO NazwaChoroby FROM Choroba WHERE
  Choroba.Id = NEW.ChorobaId;
  SET NEW.ChorobaNazwa = NazwaChoroby;
END;

DROP TRIGGER IF EXISTS SzczepionkaZmienChorobaNazwa;
CREATE TRIGGER SzczepionkaZmienChorobaNazwa
BEFORE UPDATE ON Szczepionka
FOR EACH ROW
BEGIN
  DECLARE NazwaChoroby VARCHAR(50);
  IF NEW.ChorobaId != OLD.ChorobaId THEN
    SELECT Nazwa INTO NazwaChoroby FROM Choroba WHERE
    Choroba.Id = NEW.ChorobaId;
    SET NEW.ChorobaNazwa = NazwaChoroby;
  END IF;
END;

DROP TRIGGER IF EXISTS SzczepionkaZmienChorobaNazwa2;
CREATE TRIGGER SzczepionkaZmienChorobaNazwa2
AFTER UPDATE ON Choroba
FOR EACH ROW
BEGIN
  IF NEW.Nazwa != OLD.Nazwa THEN
    UPDATE Szczepionka SET ChorobaNazwa = NEW.Nazwa WHERE
    Szczepionka.ChorobaId = NEW.Id;
  END IF;
END;

DROP TRIGGER IF EXISTS ChorobaSmiertelnoscWartosciConstraintInsert;
CREATE TRIGGER ChorobaSmiertelnoscWartosciConstraintInsert
BEFORE INSERT ON Choroba
FOR EACH ROW
BEGIN
    IF !(NEW.Smiertelnosc IN ('Znikoma','Niska','Srednia', 'Wysoka')) THEN
        SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'Check constraint on Choroba.Smiertelnosc failed.';
    END IF;
END;

DROP TRIGGER IF EXISTS ChorobaSmiertelnoscWartosciConstraintUpdate;
CREATE TRIGGER ChorobaSmiertelnoscWartosciConstraintUpdate
BEFORE UPDATE ON Choroba
FOR EACH ROW
BEGIN
    IF !(NEW.Smiertelnosc IN ('Znikoma','Niska','Srednia', 'Wysoka')) THEN
        SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'Check constraint on Choroba.Smiertelnosc failed.';
    END IF;
END;

$$
DELIMITER ;
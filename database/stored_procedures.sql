delimiter $$

-- lista psow (razem ze szczepieniami) ktore nie mialy w ogole nigdy
-- danego szczepienia
DROP PROCEDURE IF EXISTS nieZaszczepione; 
CREATE PROCEDURE nieZaszczepione(IN tylkoObowiazkowe BOOLEAN)
BEGIN
  SELECT * FROM Pies as p, Szczepionka as s WHERE s.CzyObowiazkowa >= tylkoObowiazkowe AND NOT EXISTS
  (
        SELECT * FROM Szczepienie where Szczepienie.PiesId = p.id
      AND Szczepienie.SzczepionkaId = s.Id
  );
END;

call nieZaszczepione(0);

-- psy ktore trzeba bedzie zaszczepic w ciagu <ileMiesiecyWPrzod> miesiecy
-- nie bierze pod uwage psow, ktore w ogole nie mialy nigdy danego szczepienia
-- te trzeba znalezc procedura powyzej
DROP PROCEDURE IF EXISTS dawnoSzczepione; 
CREATE PROCEDURE dawnoSzczepione(IN ileMiesiecyWPrzod INT,IN tylkoObowiazkowe BOOLEAN)
BEGIN
  SELECT * FROM Pies as p JOIN Szczepienie ON p.Id = Szczepienie.PiesId
  JOIN 
  (
    SELECT * FROM Szczepionka 
    WHERE CzyObowiazkowa >= tylkoObowiazkowe
  )as s ON Szczepienie.SzczepionkaId = s.Id
  GROUP BY PiesId, SzczepionkaId
  HAVING DataSzczepienia < DATE_SUB(SYSDATE(), INTERVAL (s.CoIleMiesiecy - ileMiesiecyWPrzod) MONTH);
END;

call dawnoSzczepione(6, 1); 
$$

delimiter ;
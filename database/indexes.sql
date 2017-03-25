ALTER TABLE Pies DROP INDEX Imie;
ALTER TABLE Pies DROP INDEX Wiek;

ALTER TABLE HistoriaChorob DROP INDEX DataZachorowania;
ALTER TABLE HistoriaChorob DROP INDEX DataWyzdrowienia;

ALTER TABLE Choroba DROP INDEX Nazwa;
ALTER TABLE Choroba DROP INDEX Smiertelnosc;

ALTER TABLE Szczepienie DROP INDEX DataSzczepienia;

ALTER TABLE Szczepionka DROP INDEX ChorobaNazwa;
ALTER TABLE Szczepionka DROP INDEX CoIleMiesiecy;

ALTER TABLE Pies ADD INDEX (Imie);
ALTER TABLE Pies ADD INDEX (Wiek);

ALTER TABLE HistoriaChorob ADD INDEX (DataZachorowania);
ALTER TABLE HistoriaChorob ADD INDEX (DataWyzdrowienia);

ALTER TABLE Choroba ADD INDEX (Nazwa);
ALTER TABLE Choroba ADD INDEX (Smiertelnosc);

ALTER TABLE Szczepienie ADD INDEX (DataSzczepienia);

ALTER TABLE Szczepionka ADD INDEX (ChorobaNazwa);
ALTER TABLE Szczepionka ADD INDEX (CoIleMiesiecy);
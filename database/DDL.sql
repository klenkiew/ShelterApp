SET autocommit = 0;

START TRANSACTION;

DROP TABLE IF EXISTS StatystykaChorob;
DROP TABLE IF EXISTS HistoriaZalecen;
DROP TABLE IF EXISTS Zalecenie;
DROP TABLE IF EXISTS Wyzywienie;
DROP TABLE IF EXISTS WizytaLekarska;
DROP TABLE IF EXISTS Szczepienie;
DROP TABLE IF EXISTS Szczepionka;
DROP TABLE IF EXISTS Karma;
DROP TABLE IF EXISTS RodzajKarmy;
DROP TABLE IF EXISTS Adopcja;
DROP TABLE IF EXISTS HistoriaChorob;
DROP TABLE IF EXISTS Opieka;
DROP TABLE IF EXISTS Pies;
DROP TABLE IF EXISTS Rasa;
DROP TABLE IF EXISTS Kojec;
DROP TABLE IF EXISTS Pomieszczenie;
DROP TABLE IF EXISTS Lek;
DROP TABLE IF EXISTS KontrolaAdopcyjna;
DROP TABLE IF EXISTS Choroba;
DROP TABLE IF EXISTS Lekarz;
DROP TABLE IF EXISTS Pracownik;
DROP TABLE IF EXISTS Adoptant;
DROP TABLE IF EXISTS Osoba;


CREATE TABLE Adopcja
  (
    Id           INTEGER NOT NULL AUTO_INCREMENT ,
    DataAdopcji  DATE NOT NULL ,
    DataKontroli DATE NOT NULL ,
    AdoptantId   INTEGER NOT NULL ,
    PiesId       INTEGER NOT NULL ,
    PRIMARY KEY ( Id )
  ) ;


CREATE TABLE Adoptant
  (
    Id           INTEGER NOT NULL AUTO_INCREMENT ,
    Zastrzezenia VARCHAR (255) ,
    IloscAdopcji SMALLINT NOT NULL ,
    PRIMARY KEY ( Id )
  ) ;


CREATE TABLE Choroba
  (
    Id           INTEGER NOT NULL AUTO_INCREMENT ,
    Nazwa        VARCHAR (50) NOT NULL ,
    Smiertelnosc VARCHAR (50) NOT NULL ,
    Objawy       VARCHAR (255) ,
    Opis         VARCHAR (255),
    LiczbaZachorowan INTEGER NOT NULL DEFAULT 0,
    LiczbaZgonow INTEGER NOT NULL DEFAULT 0 ,
    PRIMARY KEY ( Id )
  ) ;
  

CREATE TABLE HistoriaChorob
  (
    Id               INTEGER NOT NULL AUTO_INCREMENT ,
    DataZachorowania DATE NOT NULL ,
    DataWyzdrowienia DATE ,
    PiesId           INTEGER NOT NULL ,
    ChorobaId        INTEGER NOT NULL ,
    CzySmiertelna    CHAR (1) NOT NULL ,
    PRIMARY KEY ( Id )
  ) ;


CREATE TABLE Karma
  (
    Id                INTEGER NOT NULL AUTO_INCREMENT ,
    Nazwa             VARCHAR (100) NOT NULL ,
    Firma             VARCHAR (50) ,
    AktualnyZapasKg   INTEGER NOT NULL ,
    CenaKg            DECIMAL (6,2) NOT NULL ,
    RodzajKarmyNazwa  VARCHAR (50) NOT NULL ,
    PRIMARY KEY ( Id )
  ) ;


CREATE TABLE Kojec
  (
    Id               INTEGER NOT NULL AUTO_INCREMENT ,
    PowierzchniaM2   INTEGER NOT NULL ,
    IleMiesciPsow    SMALLINT NOT NULL ,
    IleMieszkaPsow   SMALLINT NOT NULL ,
    PomieszczenieId  INTEGER NOT NULL ,
    PRIMARY KEY ( Id )
  ) ;


CREATE TABLE KontrolaAdopcyjna
  (
    Id            INTEGER NOT NULL AUTO_INCREMENT ,
    Data          DATE NOT NULL ,
    PracownikId   INTEGER NOT NULL ,
    AdoptantId    INTEGER NOT NULL ,
    Wynik         CHAR (1) NOT NULL ,
    Komentarz     VARCHAR (255) ,
    PRIMARY KEY ( Id )
  ) ;


CREATE TABLE Lek
  (
    Id           INTEGER NOT NULL AUTO_INCREMENT ,
    Nazwa        VARCHAR (50) NOT NULL ,
    Rodzaj       VARCHAR (50) NOT NULL ,
    CzySzkodliwy CHAR (1) NOT NULL ,
    PRIMARY KEY ( Id )
  ) ;


CREATE TABLE Lekarz
  (
    Id          INTEGER NOT NULL AUTO_INCREMENT ,
    Specjalnosc VARCHAR (50) ,
    PRIMARY KEY ( Id )
  ) ;


CREATE TABLE Opieka
  (
    Id              INTEGER NOT NULL AUTO_INCREMENT ,
    DataRozpoczecia DATE NOT NULL ,
    PracownikId     INTEGER NOT NULL ,
    PiesId          INTEGER NOT NULL ,
    DataZakonczenia DATE ,
    PRIMARY KEY ( Id )
  ) ;


CREATE TABLE Osoba
  (
    Id            INTEGER NOT NULL AUTO_INCREMENT ,
    Imie          VARCHAR (50) NOT NULL ,
    Nazwisko      VARCHAR (50) NOT NULL ,
    Adres         VARCHAR (50) NOT NULL ,
    NumerTelefonu NUMERIC (18) NOT NULL ,
    NrDowodu      NVARCHAR (10) ,
    PRIMARY KEY ( Id )
  ) ;


CREATE TABLE Pies
  (
    Id           INTEGER NOT NULL AUTO_INCREMENT ,
    Imie         VARCHAR (50) ,
    CzyAgresywny CHAR (1) NOT NULL ,
    RasaId       INTEGER NOT NULL ,
    KojecId      INTEGER NOT NULL ,
    Wiek         SMALLINT NOT NULL ,
    CzyOtwarty   CHAR (1) ,
    CzyChorowity CHAR (1) ,
    KolorSiersci VARCHAR (50) ,
    Opis         VARCHAR (255) ,
    PRIMARY KEY ( Id )
  ) ;


CREATE TABLE Pomieszczenie
  (
    Id             INTEGER NOT NULL AUTO_INCREMENT ,
    PowierzchniaM2 DECIMAL (6,2) NOT NULL ,
    PRIMARY KEY ( Id )
  ) ;


CREATE TABLE Pracownik
  (
    Id      INTEGER NOT NULL AUTO_INCREMENT ,
    Zawod   VARCHAR (50) NOT NULL ,
    Pensja  DECIMAL (6,2) NOT NULL ,
    Pozycja VARCHAR (50) ,
    Urlopy  INTEGER ,
    PRIMARY KEY ( Id )
  ) ;


CREATE TABLE Rasa
  (
    Id                  INTEGER NOT NULL AUTO_INCREMENT ,
    Nazwa               VARCHAR (50) NOT NULL ,
    RodzajSiersci       VARCHAR (50) NOT NULL ,
    SredniaDlugoscZycia INTEGER ,
    Wielkosc            VARCHAR (50) ,
    PRIMARY KEY ( Id )
  ) ;


CREATE TABLE RodzajKarmy
  ( 
    Nazwa VARCHAR (50) NOT NULL ,
    PRIMARY KEY ( Nazwa )
  ) ;


CREATE TABLE StatystykaChorob
(
    ChorobaId             INTEGER NOT NULL ,
    Rok                   INTEGER NOT NULL ,
    Miesiac               INTEGER NOT NULL ,
    LiczbaZachorowan      INTEGER NOT NULL ,
    LiczbaZgonow          INTEGER NOT NULL ,
    PRIMARY KEY ( ChorobaId, Rok, Miesiac )
);


CREATE TABLE Szczepienie
  (
    Id              INTEGER NOT NULL AUTO_INCREMENT ,
    DataSzczepienia DATE NOT NULL ,
    PiesId          INTEGER NOT NULL ,
    SzczepionkaId   INTEGER NOT NULL ,
    PRIMARY KEY ( Id )
  ) ;


CREATE TABLE Szczepionka
  (
    Id             INTEGER NOT NULL AUTO_INCREMENT ,
    CoIleMiesiecy  INTEGER NOT NULL ,
    CzyObowiazkowa CHAR (1) NOT NULL ,
    ChorobaId      INTEGER NOT NULL ,
    ChorobaNazwa   VARCHAR (50) NOT NULL,
    PRIMARY KEY ( Id )
  ) ;


CREATE TABLE WizytaLekarska
  (
    Id            INTEGER NOT NULL AUTO_INCREMENT ,
    Data          DATE NOT NULL ,
    KosztWizyty   DECIMAL (6,2) NOT NULL ,
    Komentarz     VARCHAR (255) ,
    PiesId        INTEGER NOT NULL ,
    PracownikId   INTEGER NOT NULL ,
    LekarzId      INTEGER NOT NULL ,
    PRIMARY KEY ( Id )
  ) ;


CREATE TABLE Wyzywienie
  (
    IleGramDziennie   INTEGER NOT NULL ,
    RasaId            INTEGER NOT NULL ,
    RodzajKarmyNazwa  VARCHAR (50) NOT NULL ,
    PRIMARY KEY ( RasaId, RodzajKarmyNazwa )
  ) ;


CREATE TABLE Zalecenie
  (
    Id                   INTEGER NOT NULL AUTO_INCREMENT ,
    CzestotliwoscPodania VARCHAR (50) NOT NULL ,
    DlugoscKuracji       VARCHAR (50) NOT NULL ,
    Komentarz            VARCHAR (255) ,
    LekId                INTEGER ,
    RodzajKarmyNazwa     VARCHAR (50) ,
    PRIMARY KEY ( Id )
  ) ;
ALTER TABLE Zalecenie ADD CONSTRAINT Tylko1 CHECK ( ( (RodzajKarmyNazwa IS NOT NULL) AND (LekId IS NULL) ) OR ( (LekId IS NOT NULL) AND (RodzajKarmyNazwa IS NULL) ) ) ;


CREATE TABLE HistoriaZalecen
  (
    ChorobaId   INTEGER NOT NULL ,
    ZalecenieId INTEGER NOT NULL ,
    PRIMARY KEY ( ChorobaId, ZalecenieId )
  ) ;


ALTER TABLE Adopcja ADD CONSTRAINT Adopcja_Adoptant_FK FOREIGN KEY ( AdoptantId ) REFERENCES Adoptant ( Id ) ;

ALTER TABLE Adopcja ADD CONSTRAINT Adopcja_Pies_FK FOREIGN KEY ( PiesId ) REFERENCES Pies ( Id ) ;

ALTER TABLE Adoptant ADD CONSTRAINT Adoptant_Osoba_FK FOREIGN KEY ( Id ) REFERENCES Osoba ( Id ) ;

ALTER TABLE HistoriaZalecen ADD CONSTRAINT HistoriaZalecen_Choroba_FK FOREIGN KEY ( ChorobaId ) REFERENCES HistoriaChorob ( Id ) ;

ALTER TABLE HistoriaZalecen ADD CONSTRAINT HistoriaZalecen_Zalecenie_FK FOREIGN KEY ( ZalecenieId ) REFERENCES Zalecenie ( Id ) ;

ALTER TABLE HistoriaChorob ADD CONSTRAINT HistoriaChorob_Choroba_FK FOREIGN KEY ( ChorobaId ) REFERENCES Choroba ( Id ) ;

ALTER TABLE HistoriaChorob ADD CONSTRAINT HistoriaChorob_Pies_FK FOREIGN KEY ( PiesId ) REFERENCES Pies ( Id ) ;

ALTER TABLE Karma ADD CONSTRAINT Karma_RodzajKarmy_FK FOREIGN KEY ( RodzajKarmyNazwa ) REFERENCES RodzajKarmy ( Nazwa ) ;

ALTER TABLE Kojec ADD CONSTRAINT Kojec_Pomieszczenie_FK FOREIGN KEY ( PomieszczenieId ) REFERENCES Pomieszczenie ( Id ) ;

ALTER TABLE KontrolaAdopcyjna ADD CONSTRAINT KontrolaAdopcyjna_Adoptant_FK FOREIGN KEY ( AdoptantId ) REFERENCES Adoptant ( Id ) ;

ALTER TABLE KontrolaAdopcyjna ADD CONSTRAINT KontrolaAdopcyjna_Pracownik_FK FOREIGN KEY ( PracownikId ) REFERENCES Pracownik ( Id ) ;

ALTER TABLE Lekarz ADD CONSTRAINT Lekarz_Osoba_FK FOREIGN KEY ( Id ) REFERENCES Osoba ( Id ) ;

ALTER TABLE Opieka ADD CONSTRAINT Opieka_Pies_FK FOREIGN KEY ( PiesId ) REFERENCES Pies ( Id ) ;

ALTER TABLE Opieka ADD CONSTRAINT Opieka_Pracownik_FK FOREIGN KEY ( PracownikId ) REFERENCES Pracownik ( Id ) ;

ALTER TABLE Pies ADD CONSTRAINT Pies_Kojec_FK FOREIGN KEY ( KojecId ) REFERENCES Kojec ( Id ) ;

ALTER TABLE Pies ADD CONSTRAINT Pies_Rasa_FK FOREIGN KEY ( RasaId ) REFERENCES Rasa ( Id ) ;

ALTER TABLE Pracownik ADD CONSTRAINT Pracownik_Osoba_FK FOREIGN KEY ( Id ) REFERENCES Osoba ( Id ) ;

ALTER TABLE StatystykaChorob ADD CONSTRAINT StatystykaChorob_Choroba_FK FOREIGN KEY ( ChorobaId ) REFERENCES Choroba ( Id ) ;

ALTER TABLE Szczepienie ADD CONSTRAINT Szczepienie_Pies_FK FOREIGN KEY ( PiesId ) REFERENCES Pies ( Id ) ;

ALTER TABLE Szczepienie ADD CONSTRAINT Szczepienie_Szczepionka_FK FOREIGN KEY ( SzczepionkaId ) REFERENCES Szczepionka ( Id ) ;

ALTER TABLE Szczepionka ADD CONSTRAINT Szczepionka_Choroba_FK FOREIGN KEY ( ChorobaId ) REFERENCES Choroba ( Id ) ;

ALTER TABLE WizytaLekarska ADD CONSTRAINT WizytaLekarska_Lekarz_FK FOREIGN KEY ( LekarzId ) REFERENCES Lekarz ( Id ) ;

ALTER TABLE WizytaLekarska ADD CONSTRAINT WizytaLekarska_Pies_FK FOREIGN KEY ( PiesId ) REFERENCES Pies ( Id ) ;

ALTER TABLE WizytaLekarska ADD CONSTRAINT WizytaLekarska_Pracownik_FK FOREIGN KEY ( PracownikId ) REFERENCES Pracownik ( Id ) ;

ALTER TABLE Wyzywienie ADD CONSTRAINT Wyzywienie_Rasa_FK FOREIGN KEY ( RasaId ) REFERENCES Rasa ( Id ) ;

ALTER TABLE Wyzywienie ADD CONSTRAINT Wyzywienie_RodzajKarmy_FK FOREIGN KEY ( RodzajKarmyNazwa ) REFERENCES RodzajKarmy ( Nazwa ) ;

ALTER TABLE Zalecenie ADD CONSTRAINT Zalecenie_Lek_FK FOREIGN KEY ( LekId ) REFERENCES Lek ( Id ) ;

ALTER TABLE Zalecenie ADD CONSTRAINT Zalecenie_RodzajKarmy_FK FOREIGN KEY ( RodzajKarmyNazwa ) REFERENCES RodzajKarmy ( Nazwa ) ;

COMMIT;
SET autocommit = 1;
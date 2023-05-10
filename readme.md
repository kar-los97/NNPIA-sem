# Hodnocení restaurací
Tato práce je zpracovávána pro předmět Programování internetových aplikací
v rámci nazazujícího studia IT Fakulty Elektrotechniky a Informatiky Univerzity Pardubice.
* **Autor:** Bc. Karel Andres
* **Rok:** 2023
* **
## Použité technologie
* Databáze:
    * Relační PostgreSQL databáze
    * Schéma vytvořeno v MySQL WorkBench
* BackEnd:
    * Java Spring Boot
* FrontEnd:
    * React s využitím Bootstrap

* **    
## Popis práce
Obsahem práce je jednoduchá webová aplikace věnovaná hodnocení restaurací. 
Co mě vedlo k právě tomuto tématu? Z vlastní zkušenosti vím, že spousta restaurací
díky covidové době ztratila svou prestiž. Hodnocení, která jsou umístěna
na již známých serverech (Google, SeznamMapy, atp.) již nemusí odpovídat aktuálnímu stavu.


Jedná se o opravdu velmi triviální aplikaci, měla by fungovat na podobném principu
jako jsou hodnocení již existující. Uživatel si otevře webovou stránku, na které si může vyhledat
již vytvořené restaurace. Existuje zde možnost se přihlásit.
Registrovat se můžete jako běžný uživatel, ale taktéž jako "admin" budoucí restuarace.
Jako běžný uživatel můžete pouze vyhledávat a hodnotit restaurace.
Jako "admin" pak můžete restauraci vytvořit.
Hodnocení jednotlivých restaurací je realizováno pomocí hvězdiček (0 - 5). Hodnocení můžete vytvořit ke každé restauraci.
K hodnocení můžete i zanechat komentář, jak se vám tam líbilo.

* **
## Postup práce
### 1. Schéma databáze
Provdel jsem návrh schématu databáze. Které naleznete ve složce db-model.
### 2. BackEnd
Na základě schématu jsem vytvořil BackEnd této aplikace. Aplikace je napsána v Java Spring Boot. Celý Backend je umístěn
ve složce be.
### 4. FrontEnd
Pro vytvořený backend jsem vytvořil uživatelské rozhraní ve formě webové aplikace, které je napsáno v Reactu, ke stylování
byl využit Bootstrap. Celý FrontEnd se nachází ve složce fe.
### 3. Testování BackEndu
Na závěr všeho jsem vytvořil pár základních testů, pro otestování operací prováděných na BE.

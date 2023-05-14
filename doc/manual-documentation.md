# Manuális tesztek készítésének dokumentációja

## Feladat (céljának) rövid leírása

A feladat során a szoftver konzolos be-/kimenetét használva definiáltunk manuális teszteket. Konkrét felhasználási eseteket definiáltunk, melyekhez való eljutáshoz megadtuk az elvárt 
bemenetet, illetve a megfelelő parancsok után elvárt kimenetet. Minden teszt sikerességét JUnit keretrendszerrel ellenőrizzük.

## Megvalósítás főbb lépései

1) Tesztesetek definiálása
	* 01_StealFromParalyzedThenKillWithAxe.txt
		- Két virológus van a pályán
		- Az egyik virológus paralizálva van (nem tud semmit sem csinálni), és van egy táskája meg egy baltája
		- A másik virológus ellopja a paralizált virológustól a táskáját és a baltáját
		- A másik virológus megöli a baltájával a kifosztott virológust.
	* 02_SynthesiseAgentEnoughMaterialThenApplyAgentToUnprotected.txt
		- Két virológus van a pályán
		- Az egyik virológus megtanulja az adott mezőn (laboron) lévő táncoló ágenst
		- A virológusnak amelyik most megtanulta az ágenst van elég nukleotidja és aminosava hogy legyártsa az ágenst amit tanult
		- A virológus amelyik most megtanulta az ágenst le is gyártja azt
		- A virológus amelyik most legyártotta az ágenst ráteszi azt a másik (védtelen) virológusra
		- A védtelen virológus a táncoló ágens miatt elkezd lépkedni össze-vissza, emiatt végül a másik mezőn lesz
2) Tesztesetekhez a releváns be-/kimeneti fájlok elkészítése.
3) Tesztesetek JUnit-al való ellenőrzése.

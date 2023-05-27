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
	* 03_TryLootingBunkerEnoughSpaceThenMakeGlovesBroken.txt
		- Két virológus van a pályán
		- Az egyik virológus rendelkezik 2 kesztyűvel és egy baltával, emellett tulajdonában áll egy dance ágens
		- A másik virológus pedig egy dementia ágenssel rendelkezik
		- Mind a két játékos azonos mezőn áll
		- A második játékos használja az elsőre a dementia ágensét és rákeni azt
		- Az első játékos a kesztyű segítségével visszadobja az ágenst a küldőjére ezzel csökkentve a kesztyű élettartamát
		- Az első virológus a körében magára varázsolja a dance ágensét és szintén a kesztyűvel visszadobja a küldőre (magára)
		- Addig történik ez az iteráció amíg el nem törik a kesztyű
		- Ezt követően elmegy a bunker mezőre és felveszi az ott lévő eszközt (kesztyűt)
	* 04_TryLootingLaboratoryNewAgentAndApplyAgentToUnprotected.txt
		- Két virológus van a pályán
		- Az első virológus egy dementia ágenssel ellátott laboron kezdi a játékot
		- A másik pedig rendelkezik egy elkészített dance ágenssel és egy egyszerű mezőn áll 
		- Az első virológus a laborban megtanulja az ott lévő dementia ágenst, így ez bekerül a megtanult ágensei közé
		- Ezt követően átlép a sima mezőre, ahol a másik virológus áll
		- A másik játékos pedig a már létrehozott dance ágenst így hogy egy mezőn állnak a másik virológusra keni
		- Majd elmozdul a labor mezőre
2) Tesztesetekhez a releváns be-/kimeneti fájlok elkészítése.
3) Tesztesetek JUnit-al való ellenőrzése.

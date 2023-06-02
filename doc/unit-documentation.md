# Egységtesztek készítésének dokumentációja

## Feladat (céljának) rövid leírása

A feladat során egységteszteket írtunk az üzleti logika szempontjából két legfontosabb osztályra: Game, Virologist. A feladat megvalósítása során nem törekedtünk 100%-os kódlefedettségre, 
viszont minden fontosabb metódust leteszteltünk.

## Megvalósítás főbb lépései

1) JUnit keretrendszer hozzáadása a projekthez Maven-t használva.
	* Fontos volt hogy a tesztek a `src/test/java` alkönyvtárban legyenek, hogy Maven-el is futtathatóak legyenek.
2) Felosztottuk a két tesztelendő osztály tesztjeinek megírását:
	* Game: Pfemeter Márton
                * Minden teszt előtt lefut a 'setUp' metódus, mely egy új Game példányt hoz létre az adott teszthez
                * Ami Game osztály kivételével minden más modellbeli osztály mock-olva van Mockito keretrendszerrel
                * Az alábbi főbb csoportokra oszlanak a megírt tesztek, melyek a GameValidator.java fájlban kommentekkel is el vannak választva:
                        * map generation tests
                                * Letesztelik különböző kezdeti konfigurációk után elvártan indul e el a játék
                                * Keletkezik e kivétel vagy sem
                        * user input tests
                                * Letesztelik, amennyiben a Game osztály bemenetet kér a felhasználótól, az arra adott válaszokra hogyan reagál a játék
                        * UniqueObject tests
                                * Letesztelik a egyedi azonosítók generálása a játékbeli objektumokra tényleg szabályszerűen működik e
                        * round updating tests
                                * Letesztelik már futó játék esetén a megfelelő callback metódusok meghívódnak e, illetve a Virologist (játékosok) állapota is helyen változik e
	* Virologist: Födémesi Lili
3) Megvalósítottuk a teszteket, illetve a tesztelés során a Mockito keretrendszert is hozzáadtuk a projekthez Maven-t használva.

## Releváns képek

### A sikeresen lefutott egységtesztek eredménye.

![passed-tests](passed-unit-tests.png)

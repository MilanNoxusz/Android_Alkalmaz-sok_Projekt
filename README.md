# Android_Alkalmazasok_Projekt


Európa országai

Alkalmazás funkciója
Európa országairól olvashatunk információkat, statisztikákat, érdekességeket.
A főoldalon megjelennek csempéken az országok neve, ez szűrhető különböző szempontok (régió, schengeni övezet része-e, stb.), illetve rendezhető különböző adatok (népesség, terület...) szerint. (Alapértelmezett: ABC növekvő, szűrés nélkül)
Egy csempét kiválasztva megjelenik az ország "profilja", itt jelennek meg az adatok, itt adható a kedvencekhez.
Szintén a főoldalon található egy kereső.
Egyik sarokba helyet kap egy lenyitható menü, innen érhető el a kedvencek oldal, illetve a fejlesztők listája.
Az alkalmazás egy adatbázisból kéri le az adatokat, a kedvenceket egy offline adatbázisba menti.

Cs. Nagy Dániel
Létrehozza a projektet Android Studio-ban.
Beállítja a képernyők közötti váltást
Megcsinálja a fő képernyőt, menüsávot.


Muskó Milán
API és adatok
Beállítja a REST Countries API-t (Retrofit-tel).
Lekéri az európai országokat.
Létrehozza a modelleket (ország neve, zászló, főváros, stb.).
Glide-dal megjeleníti a zászlókat.
Ő felel az adatokért és a hálózati kapcsolatokért.

Sári Bence
Felhasználói felület és lista
Megcsinálja a listanézetet (RecyclerView).
Készít egy részletes nézetet, ahol látszanak az ország adatai.
Szépíti a felületet ConstraintLayout-tal.
Ő csinálja az UI-t, amit a felhasználó lát és kezel.

Rafai Roland 
Kedvencek és adatbázis
Ő felel a Room adatbázis beállításáért, vagyis az adatmentésért és az offline funkciókért.
Megvalósítja, hogy a felhasználó hozzáadhasson országokat a kedvencekhez, és ezek megjelenjenek egy külön „Kedvencek” listában.
Keresés és rendezés funkció hozzáadása (pl. név szerint).
Arra is ügyel, hogy az alkalmazás internet nélkül is működjön, vagyis az adatok elmentődjenek és visszatölthetők legyenek offline módban is.






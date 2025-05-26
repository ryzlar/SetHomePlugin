# SetHomePlugin

Een eenvoudige maar krachtige Minecraft plugin waarmee spelers hun eigen teleportatiepunten kunnen instellen, beheren en gebruiken. Deze plugin is ideaal voor survival-servers en biedt een intuïtieve ervaring voor spelers die hun favoriete plekken willen onthouden.

## 📦 Features

- ✅ Spelers kunnen meerdere homes instellen met unieke namen
- ✅ Teleporteer naar ingestelde homes
- ✅ Bekijk een lijst van alle ingestelde homes
- ✅ Verwijder specifieke homes
- ✅ Hernoem een home
- ✅ Alle data wordt opgeslagen in een overzichtelijk `homes.json` bestand per speler
- ✅ Ondersteuning voor kleurcodes in berichten
- ✅ UUID-ondersteuning voor spelers (geen verlies bij naamsverandering)

## 📜 Commands

| Command | Omschrijving |
|--------|--------------|
| `/sethome <naam>` | Stelt een home in op de huidige locatie met een naam |
| `/home <naam>` | Teleporteert je naar de opgegeven home |
| `/homelist` | Laat een lijst zien van al je ingestelde homes |
| `/delhome <naam>` | Verwijdert de opgegeven home |
| `/renamehome <oude_naam> <nieuwe_naam>` | Hernoemt een bestaande home |
| `/homes reload` | (Optioneel) Herlaadt de pluginconfiguratie zonder de server te herstarten (alleen voor operators) |

## 🗃️ Dataopslag

De plugin gebruikt een JSON-bestand (`homes.json`) dat automatisch per speler alle homes opslaat:

```json
{
  "uuid-van-speler": {
    "homeName1": {
      "world": "world",
      "x": 123.4,
      "y": 64.0,
      "z": -456.7
    },
    "homeName2": {
      "world": "world_nether",
      "x": 10.0,
      "y": 55.0,
      "z": 20.0
    }
  }
}


 Installatie
Download de plugin .jar en plaats het in je plugins map.

Start de server opnieuw of gebruik /reload.

De plugin maakt automatisch het homes.json bestand aan.

Klaar! Je kunt nu homes instellen en beheren.

🔐 Permissions (optioneel)
Als je met een permissions plugin werkt (zoals LuckPerms), kun je de volgende rechten instellen:

sethome.use – Toegang tot /sethome en /home

sethome.list – Toegang tot /homelist

sethome.delete – Toegang tot /delhome

sethome.rename – Toegang tot /renamehome

sethome.reload – (Admin) herlaadt de configuratie

✅ Compatibiliteit
✔ Minecraft 1.21.1 (getest)

✔ Spigot & PaperMC ondersteuning

❌ Niet getest met Forge of Fabric

🧑‍💻 Auteur
Deze plugin is ontwikkeld door Marouan El Marnissy als praktijkproject voor zijn MBO Software Development opleiding aan Curio.

# OreSpawn Port (1.12.2)

OreSpawn — hundreds of mobs, bosses (Mobzilla, The King, The Queen…), dimensions, ores, armor and weapons — brought to **Minecraft 1.12.2 on Cleanroom Loader**.
Requires [ArchaicLib](https://github.com/ElectrifiedDed/ArchaicLib) 1.13.0+.

> **This is not a re-upload.** OreSpawn was created by **TheyCallMeDanger** for Minecraft 1.7.10. This repository contains only the port's own code. It ships **none of the original textures or sounds** — they are read at runtime from your own copy of the original mod.

## Original mod (required for textures and sounds)

Get `orespawn-1.7.10-20.3.jar` (OreSpawn for 1.7.10, version 20.3), e.g. from the [Danger Zone archive](https://dangerzone-archive.weebly.com/orespawn.html).

1. Put it in `<minecraft folder>/archaic_originals/` (create the folder). **Not** in `mods/`.
2. Start the game. ArchaicLib serves textures and sounds straight from that jar; nothing is extracted or copied.

If the jar is missing, a window on the main menu shows the link and the exact folder. A dedicated server does not need it.

## Installation

1. Minecraft 1.12.2 + [Cleanroom Loader](https://github.com/CleanroomMC/Cleanroom).
2. Put `archaiclib-1.13.0.jar` and `orespawn-1.0.0.jar` into `mods/`.
3. Put the original jar into `archaic_originals/` (see above).

## Building

Needs a sibling folder `../ArchaicLib` built beforehand:

```
cd ../ArchaicLib && ./gradlew build
cd ../OreSpawn && ./gradlew build
```

## License

HardEvolution Restricted Source-Available License (HE-RSAL) 1.1 — see [LICENSE.en.md](LICENSE.en.md) and [LICENSE.ru.md](LICENSE.ru.md).
OreSpawn and all its assets belong to their author, TheyCallMeDanger; this project neither contains nor modifies them.

Author: **electrifiedded** / [HardEvolution](https://github.com/HardEvolution-team) · [Discord](https://discord.gg/92JTPmfGSx)

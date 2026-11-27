# Changelog

## 1.1.5
### Fixed
- Boss/target health bars were drawn darkened: leftover GL colour/lighting state is now reset before drawing.
- Easter Bunny now lays eggs on land too (it only did it while swimming).
### Changed
- Health bar: smooth fill, lingering damage trail, HP numbers under the bar, low-health pulse.
- Bar moves below vanilla boss bars (Wither, Ender Dragon, other mods) instead of overlapping them.
- The last fought boss stays shown while nearby even if you look away.

## 1.1.4
### Changed
- The original mod file is now downloaded automatically on first start (needs ArchaicLib 1.42.0+): sources and mirrors are listed in `config/archaiclib/originals/` and can be edited there. If the download fails, a banner on the main menu links to the manual download.

## 1.1.3
### Fixed
- Village Mania (Rainbow Ant dimension) villages now generate like vanilla overworld villages: the generators go through Forge's InitMapGenEvent and the chunk provider fires PopulateChunkEvent, so village mods (Village Names and similar) replace/extend them correctly instead of producing broken villages.
- OreSpawn's own features (apple trees, dungeons, ant/spider hangouts, ant nests) no longer spawn on top of village houses and roads.
- /locate Village (and Mineshaft/Temple) works in the Village Mania dimension.

## 1.1.2
### Fixed
- Crash "OreSpawn dimension ID is already registered: 80" when another mod uses the same dimension id: OreSpawn now automatically picks a free block of 6 ids.

## 1.1.1
### Fixed
- Missing English names for ~150 items and blocks (Thunder Staff, Irukandji Arrow, Ray Gun, Wrench, Hoverboard, caged mobs, foods, repellents, etc.).

## 1.1.0
### Changed
- modid changed from `orespawn` to `orespawndedsport` (the old id clashed with other mods). Assets, lang keys and registry names moved to the new namespace; textures/sounds still come from the original jar via ArchaicLib 1.37.0 domain mapping. Worlds saved under the old id are remapped automatically (blocks, items, biomes, mobs, sounds); config is now `orespawndedsport.cfg`.

## 1.0.0
First release of the 1.12.2 port (Cleanroom Loader). Based on OreSpawn 20.3 for 1.7.10 by TheyCallMeDanger.

- Ships no original assets: textures and sounds load from the original jar in `archaic_originals/`.
- Dedicated server works (fixed client-only biome name crash).
- New en_us / ru_ru translations.

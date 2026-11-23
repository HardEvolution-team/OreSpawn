# Changelog

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

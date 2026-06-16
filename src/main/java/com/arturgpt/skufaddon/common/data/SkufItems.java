package com.arturgpt.skufaddon.common.data;

import com.arturgpt.skufaddon.SkufAddon;

import net.minecraft.world.item.Item;

import com.tterrag.registrate.util.entry.ItemEntry;

/**
 * Предметы Skuf Addon / ArthurTech, регистрируются через Registrate-инстанс GT ({@code SkufAddon.REGISTRATE}).
 *
 * <p>Текстуры лежат в {@code assets/skufaddon/textures/item/<name>.png}, модели предметов —
 * в {@code assets/skufaddon/models/item/<name>.json} (поставляются готовыми, чтобы предметы
 * отображались без data-gen).</p>
 *
 * <p>Спец-предметы эндгейма (Капсула «Лирика в Падике» и Сингулярность «Деревенский Покой») здесь
 * НЕ заводятся: они завязаны на стазис/энтропию (домен друга — аура/тильт) и переносятся отдельным
 * коммитом вместе с эндгейм-логикой и ПГТ.</p>
 */
public class SkufItems {

    // --- Промежуточные компоненты ЧПУ-линии ---
    public static ItemEntry<Item> ITEM_CNC_BIT;
    public static ItemEntry<Item> COMPONENT_CNC_CUTTER;

    // --- Ядро индикатора Пукана ---
    public static ItemEntry<Item> ITEM_PUKAN_INDICATOR_CORE;

    // --- Финальный предмет ранней прогрессии — «Правильная Вещь» ---
    public static ItemEntry<Item> ITEM_PRAVILNAYA_VESH;

    // --- Обломки после катастрофы «Горящий пукан» (Фаза 5) — сырьё для ремонта ---
    public static ItemEntry<Item> MELTED_CAPACITOR;
    public static ItemEntry<Item> BURNT_CABLE_DEBRIS;
    public static ItemEntry<Item> CHARRED_DEVELOPER_CIRCUIT;

    // --- Скрипт методики Мыпошко (Фаза 6) — компонент портов и терминала ---
    public static ItemEntry<Item> ITEM_MYPOSHKO_SCRIPT;

    // --- Ядро Егора (Фаза 7) — сердце мультиблока «Сауна Егора» ---
    public static ItemEntry<Item> ITEM_EGOR_CORE;

    // --- Эндгейм-компоненты (Фаза 8, IV+) ---
    /** Микрокапсула Правильной Материи — промежуточный компонент капсулы стазиса. */
    public static ItemEntry<Item> ITEM_CORRECT_MATTER_MICROCAPSULE;
    /** Антизумерное ядро — компонент Абсолютного Похуита (§8.5). */
    public static ItemEntry<Item> ITEM_ANTIZOOMER_CORE;
    /** Правильная разрабская схема — стабильная схема для финальной цепочки (§8.5). */
    public static ItemEntry<Item> ITEM_CORRECT_DEVELOPER_SCHEMATIC;
    /** Нормисная Сингулярность — сжатая энтропия нормиса, компонент финала (§23.2). */
    public static ItemEntry<Item> ITEM_NORMIS_SINGULARITY;
    /** Абсолютный Похуит — высшая устойчивость из истины, региона, вайба и неоднозначности (§8.5). */
    public static ItemEntry<Item> ITEM_ABSOLUTE_POHUIT;
    /** Артурийский мейнфрейм — управляющее ядро финальной сборки (§23.2). */
    public static ItemEntry<Item> ITEM_ARTURIAN_MAINFRAME;

    public static void init() {
        ITEM_CNC_BIT = SkufAddon.REGISTRATE
                .item("item_cnc_bit", Item::new)
                .register();

        COMPONENT_CNC_CUTTER = SkufAddon.REGISTRATE
                .item("component_cnc_cutter", Item::new)
                .register();

        ITEM_PUKAN_INDICATOR_CORE = SkufAddon.REGISTRATE
                .item("item_pukan_indicator_core", Item::new)
                .register();

        ITEM_PRAVILNAYA_VESH = SkufAddon.REGISTRATE
                .item("item_pravilnaya_vesh", Item::new)
                .register();

        // --- Обломки катастрофы (Фаза 5) ---
        MELTED_CAPACITOR = SkufAddon.REGISTRATE
                .item("melted_capacitor", Item::new)
                .register();

        BURNT_CABLE_DEBRIS = SkufAddon.REGISTRATE
                .item("burnt_cable_debris", Item::new)
                .register();

        CHARRED_DEVELOPER_CIRCUIT = SkufAddon.REGISTRATE
                .item("charred_developer_circuit", Item::new)
                .register();

        // --- Слой Мыпошко (Фаза 6) ---
        ITEM_MYPOSHKO_SCRIPT = SkufAddon.REGISTRATE
                .item("item_myposhko_script", Item::new)
                .register();

        // --- Сауна Егора (Фаза 7) ---
        ITEM_EGOR_CORE = SkufAddon.REGISTRATE
                .item("item_egor_core", Item::new)
                .register();

        // --- Эндгейм-компоненты (Фаза 8) ---
        ITEM_CORRECT_MATTER_MICROCAPSULE = SkufAddon.REGISTRATE
                .item("item_correct_matter_microcapsule", Item::new)
                .register();

        ITEM_ANTIZOOMER_CORE = SkufAddon.REGISTRATE
                .item("item_antizoomer_core", Item::new)
                .register();

        ITEM_CORRECT_DEVELOPER_SCHEMATIC = SkufAddon.REGISTRATE
                .item("item_correct_developer_schematic", Item::new)
                .register();

        ITEM_NORMIS_SINGULARITY = SkufAddon.REGISTRATE
                .item("item_normis_singularity", Item::new)
                .register();

        ITEM_ABSOLUTE_POHUIT = SkufAddon.REGISTRATE
                .item("item_absolute_pohuit", Item::new)
                .register();

        ITEM_ARTURIAN_MAINFRAME = SkufAddon.REGISTRATE
                .item("item_arturian_mainframe", Item::new)
                .register();
    }
}

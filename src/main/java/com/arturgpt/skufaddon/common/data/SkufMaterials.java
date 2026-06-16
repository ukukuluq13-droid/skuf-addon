package com.arturgpt.skufaddon.common.data;

import com.arturgpt.skufaddon.SkufAddon;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.gregtechceu.gtceu.api.fluids.attribute.FluidAttributes;

import net.minecraft.resources.ResourceLocation;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;

/**
 * Материалы Skuf Addon / ArthurTech.
 *
 * <p>Базовые материалы (skufit, pokhuit, sweat, puff_smoke, jizhnyak) заведены в исходном шаблоне
 * kalashnikov-dev/skuf-addon — сохранены как есть. Ниже к ним добавлена полная материальная цепочка
 * ArthurTech (нормис-пыль → честная сталь → правильная материя → … → эндгейм) из ветки 0.9.1.</p>
 *
 * <p>Разблокировка старта (концепция v3, §1.1, ответы Derek A1–A2): skufit и pokhuit получают флаг
 * {@code .ore()} и генерируются рудными жилами (см. data/skufaddon/.../ore_veins). chelyabinsk_shale
 * остаётся рудой-источником ural_isotope.</p>
 */
public class SkufMaterials {

    // --- Базовые материалы шаблона (наработки kalashnikov-dev — НЕ удалять) ---
    public static Material skufit;
    public static Material pokhuit;
    public static Material sweat;
    public static Material puffSmoke;
    public static Material jizhnyak;

    // --- MVP-материалы ArthurTech ---
    public static Material normieDust;
    public static Material honestSteel;
    public static Material correctMatter;
    public static Material uralIsotope;

    /** Стабилизированный Вайб — «положительная противоположность энтропии» как ресурс. */
    public static Material stabilizedVibe;

    /** Челябинский сланец — радиоактивная руда HV-этапа, источник Уральского Изотопа. */
    public static Material chelyabinskShale;

    // --- Слой «Ваще похуй» ---
    /** Шлак Игнора — мёртвый остаток ресурса, «убитого» режимом игнора. */
    public static Material slagIgnore;
    /** Жижняк Потерь — слитая в никуда жидкость застрявшей линии. */
    public static Material zhizhnyakLoss;
    /** Газ Угара — выхлоп скрытого перегрева во время игнора. */
    public static Material ugarGas;
    /** Скрытый Пот — невидимое напряжение линии, копящееся под игнором. */
    public static Material hiddenSweat;
    /** Сгущённый Пот — сконденсированный скрытый пот, годен в переработку. */
    public static Material condensedSweat;

    // --- Слой Мыпошко / «Разбор геймплея» ---
    /** Технические Слёзы — кристаллический осадок «плачущих механизмов» после Разбора геймплея. */
    public static Material technicalTears;

    // --- Сауна Егора / контролируемая термодинамика (EV) ---
    /** Охладитель Отрицания — рабочая жидкость анти-тильт контура Сауны Егора. */
    public static Material coolantOfDenial;
    /** Тёплый Вайбовый Пар — побочный газ Сауны, конденсируется обратно в воду + угар. */
    public static Material warmVibeSteam;

    // --- Эндгейм (IV+) ---
    /** Благородный Газ Падика — полностью вымышленный инертный газ для капсулы стазиса. */
    public static Material padikNobleGas;
    /** Плотный Жижняк — сгущённая биомасса, компонент капсулы «Лирика в Падике». */
    public static Material denseJizhnyak;

    public static void init() {
        // Скуфит — ранний грязный металл. Теперь добывается рудой (разблокировка старта).
        skufit = new Material.Builder(ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "skufit"))
                .ingot()
                .ore()
                .liquid(new FluidBuilder().temperature(1200))
                .color(0x7A5C3A)
                .iconSet(MaterialIconSet.DULL)
                .flags(
                        GENERATE_PLATE,
                        GENERATE_ROD,
                        GENERATE_GEAR,
                        GENERATE_BOLT_SCREW,
                        GENERATE_FOIL)
                .buildAndRegister();

        // Похуит — стабильный сплав-металл. Теперь добывается рудой (нужен для корпусов мультиблоков).
        pokhuit = new Material.Builder(ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "pokhuit"))
                .ingot()
                .ore()
                .liquid(new FluidBuilder().temperature(2400))
                .color(0x3A7A5C)
                .iconSet(MaterialIconSet.SHINY)
                .flags(
                        GENERATE_PLATE,
                        GENERATE_ROD,
                        GENERATE_GEAR,
                        GENERATE_BOLT_SCREW,
                        GENERATE_FOIL)
                .buildAndRegister();

        sweat = new Material.Builder(ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "sweat"))
                .liquid(new FluidBuilder()
                        .temperature(310)
                        .attribute(FluidAttributes.ACID))
                .color(0xD4C84A)
                .buildAndRegister();

        puffSmoke = new Material.Builder(ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "puff_smoke"))
                .gas(new FluidBuilder()
                        .temperature(600))
                .color(0x2A2A2A)
                .buildAndRegister();

        jizhnyak = new Material.Builder(ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "jizhnyak"))
                .liquid(new FluidBuilder()
                        .temperature(340)
                        .attribute(FluidAttributes.ACID))
                .color(0x5F5E41)
                .buildAndRegister();

        // Нормис-пыль: серая безликая масса, перемолотый «мусор нормиса».
        normieDust = new Material.Builder(ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "normie_dust"))
                .dust()
                .color(0x8A8A8A)
                .iconSet(MaterialIconSet.ROUGH)
                .buildAndRegister();

        // Честная Сталь: сплав скуфита и очищенной нормис-пыли.
        honestSteel = new Material.Builder(ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "honest_steel"))
                .ingot()
                .liquid(new FluidBuilder().temperature(1700))
                .color(0x9AA4AD)
                .iconSet(MaterialIconSet.METALLIC)
                .flags(
                        GENERATE_PLATE,
                        GENERATE_ROD,
                        GENERATE_FOIL)
                .buildAndRegister();

        // Правильная Материя: кристалл «правильности», выделенный из жижняка.
        correctMatter = new Material.Builder(ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "correct_matter"))
                .gem()
                .color(0x36C9B0)
                .iconSet(MaterialIconSet.GEM_VERTICAL)
                .flags(GENERATE_PLATE)
                .buildAndRegister();

        // Уральский Изотоп: радиоактивный остаток жижняка.
        uralIsotope = new Material.Builder(ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "ural_isotope"))
                .dust()
                .color(0x66FF33)
                .iconSet(MaterialIconSet.RADIOACTIVE)
                .radioactiveHazard(2.0f)
                .buildAndRegister();

        // Стабилизированный Вайб: светящаяся жидкость спокойствия, гасит энтропию/пукан.
        stabilizedVibe = new Material.Builder(
                ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "stabilized_vibe"))
                .liquid(new FluidBuilder()
                        .temperature(295))
                .color(0x49E0D0)
                .iconSet(MaterialIconSet.SHINY)
                .buildAndRegister();

        // Челябинский сланец: тёмно-зелёная радиоактивная руда. Дробится в Уральский Изотоп.
        // .ore() создаёт рудный блок; побочкой при обогащении идёт изотоп.
        chelyabinskShale = new Material.Builder(
                ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "chelyabinsk_shale"))
                .dust()
                .ore()
                .color(0x4C7A2E)
                .iconSet(MaterialIconSet.ROUGH)
                .radioactiveHazard(1.0f)
                .addOreByproducts(uralIsotope)
                .buildAndRegister();

        // --- Слой «Ваще похуй» ---

        // Шлак Игнора: серо-бурый спёкшийся остаток того, что «проигнорировали».
        slagIgnore = new Material.Builder(
                ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "slag_ignore"))
                .dust()
                .color(0x4A4038)
                .iconSet(MaterialIconSet.ROUGH)
                .buildAndRegister();

        // Жижняк Потерь: мутный сток, в который утекли ресурсы залипшей линии.
        zhizhnyakLoss = new Material.Builder(
                ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "zhizhnyak_loss"))
                .liquid(new FluidBuilder().temperature(330))
                .color(0x3E3A2A)
                .buildAndRegister();

        // Газ Угара: едкий тёмно-оранжевый выхлоп скрытого перегрева.
        ugarGas = new Material.Builder(
                ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "ugar_gas"))
                .gas(new FluidBuilder().temperature(720))
                .color(0xB85C1E)
                .buildAndRegister();

        // Скрытый Пот: «невидимое» напряжение, копящееся под игнором (горячая жидкость).
        hiddenSweat = new Material.Builder(
                ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "hidden_sweat"))
                .liquid(new FluidBuilder()
                        .temperature(360)
                        .attribute(FluidAttributes.ACID))
                .color(0xC0A83A)
                .buildAndRegister();

        // Сгущённый Пот: сконденсированный скрытый пот, пригоден к переработке.
        condensedSweat = new Material.Builder(
                ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "condensed_sweat"))
                .liquid(new FluidBuilder().temperature(305))
                .color(0xE6D24A)
                .iconSet(MaterialIconSet.SHINY)
                .buildAndRegister();

        // Технические Слёзы: грустный кристаллический осадок «плачущих механизмов»
        // после жёсткого «Разбора геймплея». Пыль (собирается) + жидкость (для труб).
        technicalTears = new Material.Builder(
                ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "technical_tears"))
                .dust()
                .liquid(new FluidBuilder().temperature(285))
                .color(0x4F7FB5)
                .iconSet(MaterialIconSet.SHINY)
                .buildAndRegister();

        // Охладитель Отрицания: холодная рабочая жидкость анти-тильт контура (EV+).
        coolantOfDenial = new Material.Builder(
                ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "coolant_of_denial"))
                .liquid(new FluidBuilder().temperature(255))
                .color(0x2FB7C9)
                .iconSet(MaterialIconSet.SHINY)
                .buildAndRegister();

        // Тёплый Вайбовый Пар: горячий побочный газ Сауны (конденсируется обратно).
        warmVibeSteam = new Material.Builder(
                ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "warm_vibe_steam"))
                .gas(new FluidBuilder().temperature(380))
                .color(0xC9B98F)
                .buildAndRegister();

        // --- Эндгейм ---
        // Благородный Газ Падика: холодный инертный газ, полностью вымышленный.
        padikNobleGas = new Material.Builder(
                ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "padik_noble_gas"))
                .gas(new FluidBuilder().temperature(120))
                .color(0x6B5E8C)
                .iconSet(MaterialIconSet.SHINY)
                .buildAndRegister();

        // Плотный Жижняк: сгущённая до киселя биомасса, тяжёлый компонент капсулы стазиса.
        denseJizhnyak = new Material.Builder(
                ResourceLocation.fromNamespaceAndPath(SkufAddon.MOD_ID, "dense_jizhnyak"))
                .liquid(new FluidBuilder().temperature(330))
                .color(0x3E5A2A)
                .buildAndRegister();
    }
}

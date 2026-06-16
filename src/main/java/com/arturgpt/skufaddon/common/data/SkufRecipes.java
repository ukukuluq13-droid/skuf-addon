package com.arturgpt.skufaddon.common.data;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;

/**
 * Производственная цепочка ArthurTech (перенос под шаблон друга, аддитивно).
 *
 * <p>Базовые рецепты друга (jizhnyak_mixing / jizhnyak_separation / normis_filtration) сохранены
 * один-в-один в {@link #baseRecipes}. Поверх них добавлена цепочка ArthurTech:
 * мусор → нормис-пыль → честная сталь / жижняк → правильная материя → компоненты → Правильная Вещь,
 * далее стабилизатор, Челябинский Провал, переработка, методика Мыпошко, Сауна Егора и эндгейм.</p>
 *
 * <p><b>Разблокировка старта ({@link #bootstrapFix}):</b> добавлены первичные, негейтовые источники
 * нормис-пыли и Дыма от Пыхчения на штатных ранних машинах GT (Дробилка/Центрифуга). Без них цепочка
 * жижняка не запускалась (нормис-пыль и дым были замкнуты сами на себя — известный затык загрузки).</p>
 *
 * <p><b>Отложено</b> (до интеграции ауры/тильта и измерения ПГТ — домен друга): рецепты крафта
 * отложенных машин (Индикатор Пукана, порты/шина Мыпошко, терминал «Разбор», Модуль «Ваще похуй»),
 * рецепты типа VSEM_POHUI, а также эндгейм-предметы стазиса (Лирика в Падике, Деревенский Покой).</p>
 */
public class SkufRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        baseRecipes(provider);
        bootstrapFix(provider);
        machineCrafting(provider);
        productionChain(provider);
        stabilizerChain(provider);
        chelyabinskChain(provider);
        recyclingChain(provider);
        myposhkoChain(provider);
        saunaChain(provider);
        endgameChain(provider);
    }

    /** Базовые рецепты друга — сохранены без изменений. */
    private static void baseRecipes(Consumer<FinishedRecipe> provider) {
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("jizhnyak_mixing")
                .inputFluids(SkufMaterials.sweat.getFluid(1000))
                .inputFluids(SkufMaterials.puffSmoke.getFluid(1000))
                .outputFluids(SkufMaterials.jizhnyak.getFluid(1000))
                .duration(200)
                .EUt(30)
                .save(provider);

        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder("jizhnyak_separation")
                .inputFluids(SkufMaterials.jizhnyak.getFluid(1000))
                .outputFluids(SkufMaterials.sweat.getFluid(1000))
                .outputFluids(SkufMaterials.puffSmoke.getFluid(1000))
                .duration(400)
                .EUt(30)
                .save(provider);

        SkufRecipeTypes.NORMIS_FILTRATION_RECIPES.recipeBuilder("normis_filtration")
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputFluids(SkufMaterials.sweat.getFluid(1000))
                .duration(200)
                .EUt(30)
                .save(provider);
    }

    /**
     * Разблокировка старта: первичные источники нормис-пыли и Дыма от Пыхчения на ранних штатных
     * машинах GT (не требуют машин ArthurTech и не зависят от жижнякового цикла).
     */
    private static void bootstrapFix(Consumer<FinishedRecipe> provider) {
        // Первичная нормис-пыль: перемол мусора нормиса (тухлятины) в Дробилке.
        // Разрывает цикл: раньше нормис-пыль давал только нормис-фильтр, а его крафт требовал нормис-пыль.
        GTRecipeTypes.MACERATOR_RECIPES.recipeBuilder("normie_dust_maceration")
                .inputItems(Items.ROTTEN_FLESH)
                .outputItems(dust, SkufMaterials.normieDust)
                .duration(120)
                .EUt(16)
                .save(provider);

        // Первичный Дым от Пыхчения: отгонка остаточного дыма из нормис-пыли в Центрифуге.
        // Разрывает цикл: раньше дым давал только жижняк, а жижняк требовал дым.
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder("puff_smoke_extraction")
                .inputItems(dust, SkufMaterials.normieDust)
                .circuitMeta(5)
                .outputFluids(SkufMaterials.puffSmoke.getFluid(250))
                .duration(160)
                .EUt(16)
                .save(provider);
    }

    /** Крафт кастомных машин (LV) на Сборщике из Честной Стали. */
    private static void machineCrafting(Consumer<FinishedRecipe> provider) {
        // Нормис-фильтр друга получает рецепт крафта (раньше его не было).
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("craft_normis_filtration_machine")
                .inputItems(plate, SkufMaterials.honestSteel, 4)
                .inputItems(dust, SkufMaterials.normieDust, 2)
                .circuitMeta(5)
                .outputItems(SkufMachines.NORMIS_FILTRATION_MACHINE[GTValues.LV].asStack())
                .duration(200)
                .EUt(30)
                .save(provider);

        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("craft_cnc_machine")
                .inputItems(plate, SkufMaterials.honestSteel, 4)
                .inputItems(SkufItems.ITEM_CNC_BIT, 2)
                .circuitMeta(6)
                .outputItems(SkufMachines.CNC_MACHINE[GTValues.LV].asStack())
                .duration(200)
                .EUt(30)
                .save(provider);

        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("craft_pot_distillery")
                .inputItems(plate, SkufMaterials.honestSteel, 4)
                .inputItems(plate, SkufMaterials.correctMatter)
                .circuitMeta(7)
                .outputItems(SkufMachines.POT_DISTILLERY[GTValues.LV].asStack())
                .duration(200)
                .EUt(30)
                .save(provider);

        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("craft_vibe_stabilizer")
                .inputItems(plate, SkufMaterials.honestSteel, 4)
                .inputItems(plate, SkufMaterials.correctMatter)
                .inputItems(SkufBlocks.CASING_POHUIT_REINFORCED.asStack())
                .circuitMeta(9)
                .outputItems(SkufMachines.VIBE_STABILIZER[GTValues.LV].asStack())
                .duration(300)
                .EUt(60)
                .save(provider);
    }

    /** Основная производственная цепочка. */
    private static void productionChain(Consumer<FinishedRecipe> provider) {
        // 1) [НОРМИС-ФИЛЬТР друга] Мусор нормиса -> нормис-пыль + капля пота
        SkufRecipeTypes.NORMIS_FILTRATION_RECIPES.recipeBuilder("normie_dust_from_trash")
                .inputItems(Items.ROTTEN_FLESH)
                .outputItems(dust, SkufMaterials.normieDust)
                .outputFluids(SkufMaterials.sweat.getFluid(100))
                .duration(160)
                .EUt(16)
                .save(provider);

        // 2) Скуфит + нормис-пыль -> Честная Сталь (Легирующая печь)
        GTRecipeTypes.ALLOY_SMELTER_RECIPES.recipeBuilder("honest_steel_alloy")
                .inputItems(ingot, SkufMaterials.skufit)
                .inputItems(dust, SkufMaterials.normieDust, 2)
                .outputItems(ingot, SkufMaterials.honestSteel, 2)
                .duration(240)
                .EUt(30)
                .save(provider);

        // 3) Нормис-пыль + пот + дым -> жижняк (Смеситель) — альтернативный путь
        GTRecipeTypes.MIXER_RECIPES.recipeBuilder("jizhnyak_from_normie")
                .inputItems(dust, SkufMaterials.normieDust)
                .inputFluids(SkufMaterials.sweat.getFluid(1000))
                .inputFluids(SkufMaterials.puffSmoke.getFluid(1000))
                .outputFluids(SkufMaterials.jizhnyak.getFluid(2000))
                .duration(180)
                .EUt(30)
                .save(provider);

        // 4) Жижняк -> пыль правильной материи + дым (Электролизёр)
        GTRecipeTypes.ELECTROLYZER_RECIPES.recipeBuilder("correct_matter_electrolysis")
                .inputFluids(SkufMaterials.jizhnyak.getFluid(2000))
                .outputItems(dust, SkufMaterials.correctMatter)
                .outputFluids(SkufMaterials.puffSmoke.getFluid(1000))
                .duration(300)
                .EUt(60)
                .save(provider);

        // 4-альт) [ПОТ-ДИСТИЛЛЯТОР] Жижняк -> пыль правильной материи + дым
        SkufRecipeTypes.POT_DISTILLERY_RECIPES.recipeBuilder("jizhnyak_distillation")
                .inputFluids(SkufMaterials.jizhnyak.getFluid(1000))
                .outputItems(dust, SkufMaterials.correctMatter)
                .outputFluids(SkufMaterials.puffSmoke.getFluid(500))
                .duration(260)
                .EUt(48)
                .save(provider);

        // 5) Пыль правильной материи + вода -> кристалл (Автоклав)
        GTRecipeTypes.AUTOCLAVE_RECIPES.recipeBuilder("correct_matter_crystallization")
                .inputItems(dust, SkufMaterials.correctMatter)
                .inputFluids(GTMaterials.Water.getFluid(250))
                .outputItems(gem, SkufMaterials.correctMatter)
                .duration(400)
                .EUt(60)
                .save(provider);

        // 6) Жижняк -> Уральский Изотоп + пот (Центрифуга, схема 2)
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder("ural_isotope_extraction")
                .inputFluids(SkufMaterials.jizhnyak.getFluid(1000))
                .circuitMeta(2)
                .outputItems(dust, SkufMaterials.uralIsotope)
                .outputFluids(SkufMaterials.sweat.getFluid(500))
                .duration(500)
                .EUt(60)
                .save(provider);

        // 7) [ЧПУ-СТАНОК] Стержень честной стали -> ЧПУ-резец
        SkufRecipeTypes.CNC_RECIPES.recipeBuilder("cnc_bit_from_rod")
                .inputItems(rod, SkufMaterials.honestSteel)
                .circuitMeta(1)
                .outputItems(SkufItems.ITEM_CNC_BIT, 2)
                .duration(120)
                .EUt(16)
                .save(provider);

        // 8) [ЧПУ-СТАНОК] ЧПУ-резцы + пластина -> Резак ЧПУ
        SkufRecipeTypes.CNC_RECIPES.recipeBuilder("cnc_cutter_assembly")
                .inputItems(SkufItems.ITEM_CNC_BIT, 2)
                .inputItems(plate, SkufMaterials.honestSteel)
                .circuitMeta(2)
                .outputItems(SkufItems.COMPONENT_CNC_CUTTER)
                .duration(200)
                .EUt(30)
                .save(provider);

        // 9) Изотоп + сталь + правильная материя -> Ядро Индикатора Пукана (Сборщик)
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("pukan_indicator_core_assembly")
                .inputItems(dust, SkufMaterials.uralIsotope)
                .inputItems(plate, SkufMaterials.honestSteel, 2)
                .inputItems(plate, SkufMaterials.correctMatter)
                .circuitMeta(3)
                .outputItems(SkufItems.ITEM_PUKAN_INDICATOR_CORE)
                .duration(300)
                .EUt(120)
                .save(provider);

        // 10) ФИНАЛ: кристаллы + сталь + ядро + резак + Стабилизированный Вайб -> Правильная Вещь
        // (собирается только в мультиблоке «Мини-Фабрика»).
        SkufRecipeTypes.CORRECT_FACTORY_RECIPES.recipeBuilder("pravilnaya_vesh_assembly")
                .inputItems(gem, SkufMaterials.correctMatter, 2)
                .inputItems(plate, SkufMaterials.honestSteel, 2)
                .inputItems(SkufItems.ITEM_PUKAN_INDICATOR_CORE)
                .inputItems(SkufItems.COMPONENT_CNC_CUTTER)
                .inputFluids(SkufMaterials.stabilizedVibe.getFluid(250))
                .circuitMeta(4)
                .outputItems(SkufItems.ITEM_PRAVILNAYA_VESH)
                .duration(600)
                .EUt(120)
                .save(provider);
    }

    /** Фаза 3 — Стабилизатор Вайба: «положительная противоположность энтропии» как ресурс. */
    private static void stabilizerChain(Consumer<FinishedRecipe> provider) {
        // Корпус из Похуита (конструкционный блок мультиблоков) — на Сборщике.
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("casing_pohuit_reinforced")
                .inputItems(plate, SkufMaterials.pokhuit, 6)
                .inputItems(rod, SkufMaterials.honestSteel, 2)
                .circuitMeta(6)
                .outputItems(SkufBlocks.CASING_POHUIT_REINFORCED.asStack(2))
                .duration(200)
                .EUt(30)
                .save(provider);

        // [СТАБИЛИЗАТОР] Пот + Правильная Материя -> Стабилизированный Вайб.
        SkufRecipeTypes.VIBE_STABILIZER_RECIPES.recipeBuilder("stabilized_vibe_synthesis")
                .inputItems(dust, SkufMaterials.correctMatter)
                .inputFluids(SkufMaterials.sweat.getFluid(1000))
                .outputFluids(SkufMaterials.stabilizedVibe.getFluid(1000))
                .duration(240)
                .EUt(48)
                .save(provider);

        // Применение: вайб улучшает кристаллизацию Правильной Материи (выше выход).
        GTRecipeTypes.AUTOCLAVE_RECIPES.recipeBuilder("vibe_infused_crystallization")
                .inputItems(dust, SkufMaterials.correctMatter, 2)
                .inputFluids(SkufMaterials.stabilizedVibe.getFluid(500))
                .outputItems(gem, SkufMaterials.correctMatter, 3)
                .duration(300)
                .EUt(60)
                .save(provider);
    }

    /** Фаза 4 — Челябинский Провал: HV-цепочка сланец → изотоп → обезврежен в Правильную Материю. */
    private static void chelyabinskChain(Consumer<FinishedRecipe> provider) {
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("craft_chelyabinsk_proval")
                .inputItems(SkufBlocks.CASING_POHUIT_REINFORCED.asStack(2))
                .inputItems(plate, SkufMaterials.honestSteel, 4)
                .inputItems(gem, SkufMaterials.correctMatter)
                .circuitMeta(10)
                .outputItems(SkufMachines.CHELYABINSK_PROVAL.asStack())
                .duration(400)
                .EUt(120)
                .save(provider);

        // 1) [ПРОВАЛ] Дроблёный сланец + пот(кислота) -> Уральский Изотоп + нормис-пыль + жижняк-сток.
        SkufRecipeTypes.CHELYABINSK_PROVAL_RECIPES.recipeBuilder("shale_enrichment")
                .inputItems(dust, SkufMaterials.chelyabinskShale, 2)
                .inputFluids(SkufMaterials.sweat.getFluid(1000))
                .outputItems(dust, SkufMaterials.uralIsotope, 2)
                .outputItems(dust, SkufMaterials.normieDust)
                .outputFluids(SkufMaterials.jizhnyak.getFluid(500))
                .duration(400)
                .EUt(480)
                .save(provider);

        // 2) [ПРОВАЛ] Изотоп + Стабилизированный Вайб -> обезврежен в Правильную Материю + дым.
        SkufRecipeTypes.CHELYABINSK_PROVAL_RECIPES.recipeBuilder("isotope_neutralization")
                .inputItems(dust, SkufMaterials.uralIsotope, 2)
                .inputFluids(SkufMaterials.stabilizedVibe.getFluid(1000))
                .outputItems(gem, SkufMaterials.correctMatter, 2)
                .outputFluids(SkufMaterials.puffSmoke.getFluid(250))
                .duration(360)
                .EUt(480)
                .save(provider);
    }

    /**
     * Фаза 5 (частично) — переработка и ремонт после катастрофы «Горящий пукан» на штатных машинах GT.
     * Сам Модуль «Ваще похуй» и его рецепты (тип VSEM_POHUI), а также переработка Шлака Игнора отложены
     * до интеграции ауры/тильта (домен друга).
     */
    private static void recyclingChain(Consumer<FinishedRecipe> provider) {
        // Жижняк Потерь центрифугируется обратно в пот + немного нормис-пыли.
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder("reclaim_zhizhnyak_loss")
                .inputFluids(SkufMaterials.zhizhnyakLoss.getFluid(1000))
                .circuitMeta(4)
                .outputFluids(SkufMaterials.sweat.getFluid(400))
                .outputItems(dust, SkufMaterials.normieDust)
                .duration(220)
                .EUt(60)
                .save(provider);

        // Оплавленный конденсатор переплавляется в Честную Сталь с помощью Сгущённого Пота.
        GTRecipeTypes.ARC_FURNACE_RECIPES.recipeBuilder("repair_melted_capacitor")
                .inputItems(SkufItems.MELTED_CAPACITOR.asStack(2))
                .inputFluids(SkufMaterials.condensedSweat.getFluid(250))
                .outputItems(ingot, SkufMaterials.honestSteel)
                .duration(200)
                .EUt(90)
                .save(provider);

        // Кабельные обломки перемалываются в нормис-пыль + выпускают Газ Угара.
        GTRecipeTypes.MACERATOR_RECIPES.recipeBuilder("repair_burnt_cable_debris")
                .inputItems(SkufItems.BURNT_CABLE_DEBRIS.asStack(3))
                .outputItems(dust, SkufMaterials.normieDust, 2)
                .outputFluids(SkufMaterials.ugarGas.getFluid(500))
                .duration(180)
                .EUt(48)
                .save(provider);

        // Обугленная плата восстанавливается Стабилизированным Вайбом в новое Ядро Пукана.
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("repair_charred_developer_circuit")
                .inputItems(SkufItems.CHARRED_DEVELOPER_CIRCUIT.asStack(1))
                .inputItems(dust, SkufMaterials.normieDust, 2)
                .inputFluids(SkufMaterials.stabilizedVibe.getFluid(500))
                .circuitMeta(5)
                .outputItems(SkufItems.ITEM_PUKAN_INDICATOR_CORE)
                .duration(300)
                .EUt(120)
                .save(provider);
    }

    /**
     * Фаза 6 (частично) — методика Мыпошко: скрипт-компонент и «успокоение» технических слёз.
     * Порты/шина/терминал «Разбор геймплея» отложены до интеграции ауры/тильта (домен друга).
     */
    private static void myposhkoChain(Consumer<FinishedRecipe> provider) {
        // Скрипт Мыпошко — общий компонент костыльной автоматизации.
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("craft_myposhko_script")
                .inputItems(dust, SkufMaterials.normieDust, 2)
                .inputItems(gem, SkufMaterials.correctMatter)
                .inputFluids(SkufMaterials.stabilizedVibe.getFluid(250))
                .circuitMeta(7)
                .outputItems(SkufItems.ITEM_MYPOSHKO_SCRIPT)
                .duration(160)
                .EUt(48)
                .save(provider);

        // «Успокоить» технические слёзы Стабилизированным Вайбом → возврат нормис-пыли + дым.
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("comfort_technical_tears")
                .inputItems(dust, SkufMaterials.technicalTears, 2)
                .inputFluids(SkufMaterials.stabilizedVibe.getFluid(500))
                .outputItems(dust, SkufMaterials.normieDust)
                .outputFluids(SkufMaterials.puffSmoke.getFluid(250))
                .duration(220)
                .EUt(120)
                .save(provider);
    }

    /** Фаза 7 — Сауна Егора (EV): ядро Егора, цикл сауны, конденсация пара, Охладитель Отрицания. */
    private static void saunaChain(Consumer<FinishedRecipe> provider) {
        // Ядро Егора — сердце мультиблока (EV-компонент).
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("craft_egor_core")
                .inputItems(gem, SkufMaterials.correctMatter, 2)
                .inputItems(plate, SkufMaterials.honestSteel, 4)
                .inputItems(SkufItems.ITEM_PUKAN_INDICATOR_CORE)
                .inputFluids(SkufMaterials.stabilizedVibe.getFluid(1000))
                .circuitMeta(8)
                .outputItems(SkufItems.ITEM_EGOR_CORE)
                .duration(400)
                .EUt(1920)
                .save(provider);

        // Основной цикл сауны: вода + Стабилизированный Вайб → Тёплый Вайбовый Пар + слёзы.
        SkufRecipeTypes.SAUNA_EGORA_RECIPES.recipeBuilder("sauna_steam_cycle")
                .inputFluids(GTMaterials.Water.getFluid(2000))
                .inputFluids(SkufMaterials.stabilizedVibe.getFluid(500))
                .outputFluids(SkufMaterials.warmVibeSteam.getFluid(1500))
                .outputItems(dust, SkufMaterials.technicalTears, 1)
                .duration(120)
                .EUt(512)
                .save(provider);

        // Конденсация пара: Тёплый Вайбовый Пар → вода (calm) + остаточный угар.
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder("condense_warm_vibe_steam")
                .inputFluids(SkufMaterials.warmVibeSteam.getFluid(1500))
                .outputFluids(GTMaterials.Water.getFluid(1000))
                .outputFluids(SkufMaterials.ugarGas.getFluid(250))
                .duration(100)
                .EUt(120)
                .save(provider);

        // Охладитель Отрицания: технические слёзы + пыль Похуита → анти-тильт хладагент.
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("brew_coolant_of_denial")
                .inputItems(dust, SkufMaterials.technicalTears, 2)
                .inputItems(dust, SkufMaterials.pokhuit, 1)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputFluids(SkufMaterials.coolantOfDenial.getFluid(1000))
                .duration(160)
                .EUt(480)
                .save(provider);
    }

    /**
     * Фаза 8 — Эндгейм (IV+, §8.5/§23): сырьё, цепочка Абсолютного Похуита и Артурийский мейнфрейм.
     * Капсула «Лирика в Падике» и финальная Сингулярность «Деревенский Покой» отложены вместе с их
     * предметами (стазис/энтропия — домен друга, ключ измерения ПГТ — отдельный коммит).
     */
    private static void endgameChain(Consumer<FinishedRecipe> provider) {
        // Плотный Жижняк из обычного жижняка (центрифуга отжимает воду → потери).
        GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder("condense_dense_jizhnyak")
                .inputFluids(SkufMaterials.jizhnyak.getFluid(2000))
                .circuitMeta(3)
                .outputFluids(SkufMaterials.denseJizhnyak.getFluid(1000))
                .outputFluids(SkufMaterials.zhizhnyakLoss.getFluid(500))
                .duration(160)
                .EUt(120)
                .save(provider);

        // Благородный Газ Падика: ферментация плотного жижняка с вайбом.
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("synthesize_padik_noble_gas")
                .inputFluids(SkufMaterials.denseJizhnyak.getFluid(1000))
                .inputFluids(SkufMaterials.stabilizedVibe.getFluid(200))
                .outputFluids(SkufMaterials.padikNobleGas.getFluid(500))
                .outputItems(dust, SkufMaterials.normieDust, 1)
                .duration(200)
                .EUt(1920)
                .save(provider);

        // Нормисная Сингулярность: спрессованная энтропия нормиса.
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("compress_normis_singularity")
                .inputItems(dust, SkufMaterials.normieDust, 16)
                .inputItems(dust, SkufMaterials.slagIgnore, 4)
                .circuitMeta(16)
                .outputItems(SkufItems.ITEM_NORMIS_SINGULARITY)
                .duration(400)
                .EUt(1920)
                .save(provider);

        // Антизумерное ядро: истина + регион + изотоп.
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("craft_antizoomer_core")
                .inputItems(gem, SkufMaterials.correctMatter, 2)
                .inputItems(plate, SkufMaterials.honestSteel, 2)
                .inputItems(dust, SkufMaterials.uralIsotope, 4)
                .circuitMeta(10)
                .outputItems(SkufItems.ITEM_ANTIZOOMER_CORE)
                .duration(300)
                .EUt(2048)
                .save(provider);

        // Правильная разрабская схема: восстановление обугленной схемы в стабильную.
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("craft_correct_developer_schematic")
                .inputItems(SkufItems.CHARRED_DEVELOPER_CIRCUIT)
                .inputItems(gem, SkufMaterials.correctMatter, 1)
                .inputFluids(SkufMaterials.stabilizedVibe.getFluid(500))
                .circuitMeta(11)
                .outputItems(SkufItems.ITEM_CORRECT_DEVELOPER_SCHEMATIC)
                .duration(300)
                .EUt(2048)
                .save(provider);

        // Абсолютный Похуит (§8.5): высшая устойчивость из истины, региона, вайба и неоднозначности.
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("craft_absolute_pohuit")
                .inputItems(SkufItems.ITEM_PRAVILNAYA_VESH)
                .inputItems(dust, SkufMaterials.uralIsotope, 2)
                .inputItems(SkufItems.ITEM_ANTIZOOMER_CORE)
                .inputItems(SkufItems.ITEM_CORRECT_DEVELOPER_SCHEMATIC)
                .inputFluids(SkufMaterials.stabilizedVibe.getFluid(1000))
                .circuitMeta(12)
                .outputItems(SkufItems.ITEM_ABSOLUTE_POHUIT)
                .duration(600)
                .EUt(8192)
                .save(provider);

        // Микрокапсула Правильной Материи: оболочка с газом Падика и плотным жижняком.
        GTRecipeTypes.CHEMICAL_RECIPES.recipeBuilder("craft_correct_matter_microcapsule")
                .inputItems(gem, SkufMaterials.correctMatter, 1)
                .inputFluids(SkufMaterials.denseJizhnyak.getFluid(500))
                .inputFluids(SkufMaterials.padikNobleGas.getFluid(250))
                .outputItems(SkufItems.ITEM_CORRECT_MATTER_MICROCAPSULE)
                .duration(200)
                .EUt(1920)
                .save(provider);

        // Артурийский мейнфрейм: управляющее ядро финальной сборки.
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("craft_arturian_mainframe")
                .inputItems(plate, SkufMaterials.honestSteel, 8)
                .inputItems(gem, SkufMaterials.correctMatter, 4)
                .inputItems(SkufItems.ITEM_MYPOSHKO_SCRIPT)
                .inputItems(SkufItems.ITEM_PUKAN_INDICATOR_CORE)
                .inputFluids(SkufMaterials.stabilizedVibe.getFluid(2000))
                .circuitMeta(14)
                .outputItems(SkufItems.ITEM_ARTURIAN_MAINFRAME)
                .duration(800)
                .EUt(8192)
                .save(provider);
    }
}

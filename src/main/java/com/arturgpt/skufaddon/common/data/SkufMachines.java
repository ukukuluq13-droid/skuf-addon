package com.arturgpt.skufaddon.common.data;

import com.arturgpt.skufaddon.SkufAddon;
import com.arturgpt.skufaddon.common.machine.tilt.SkufTiltMachine;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.SimpleTieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;

import net.minecraft.network.chat.Component;

import static com.gregtechceu.gtceu.common.data.machines.GTMachineUtils.ELECTRIC_TIERS;

/**
 * Машины Skuf Addon / ArthurTech.
 *
 * <p><b>Базовые (домен друга):</b> {@link #NORMIS_FILTRATION_MACHINE} — нормис-фильтрация на тильт-логике
 * {@link SkufTiltMachine}. Сохранена без изменений.</p>
 *
 * <p><b>ArthurTech (вариант «а»):</b> простые машины-обработчики переведены на ту же тильт-логику друга
 * ({@link SkufTiltMachine} + {@code SkufTiltRecipeLogic}) — БЕЗ нашей чанковой ауры/энтропии/режимов
 * линии (это домен друга, переносится отдельно). Мультиблоки — штатные
 * {@link WorkableElectricMultiblockMachine}.</p>
 *
 * <p><b>Отложено до интеграции ауры/тильта (домен друга):</b> Индикатор Пукана, порты/шина Мыпошко,
 * терминал «Разбор геймплея», Модуль «Ваще похуй». Они завязаны на чанковое состояние (Дихотомия Пота
 * и Угара, методика Мыпошко, тумблер игнора), которое строится поверх тильт-системы друга. А также
 * отдельный фильтр {@code NORMIS_FILTER}: его роль уже закрывает базовый {@link #NORMIS_FILTRATION_MACHINE}.</p>
 */
public class SkufMachines {

    // --- Базовая машина друга (без изменений) ---
    public static final MachineDefinition[] NORMIS_FILTRATION_MACHINE = new MachineDefinition[GTValues.TIER_COUNT];

    // --- Простые машины-обработчики ArthurTech (на тильт-логике друга) ---
    /** ЧПУ-станок — точная обработка деталей. */
    public static final MachineDefinition[] CNC_MACHINE = new MachineDefinition[GTValues.TIER_COUNT];
    /** Пот-Дистиллятор — перегонка пота/жижняка. */
    public static final MachineDefinition[] POT_DISTILLERY = new MachineDefinition[GTValues.TIER_COUNT];
    /** Стабилизатор Вайба — производит Стабилизированный Вайб. */
    public static final MachineDefinition[] VIBE_STABILIZER = new MachineDefinition[GTValues.TIER_COUNT];

    // --- Мультиблоки ArthurTech (штатные рецептурные мультиблоки) ---
    /** Мини-Фабрика Правильных Вещей (Фаза 3) — финальная сборка. */
    public static MultiblockMachineDefinition MINI_FACTORY;
    /** Челябинский Провал (Фаза 4) — HV-переработка сланца в Уральский Изотоп. */
    public static MultiblockMachineDefinition CHELYABINSK_PROVAL;
    /** Сауна Егора (Фаза 7) — EV-мультиблок термодинамики (вода + вайб → пар + слёзы). */
    public static MultiblockMachineDefinition SAUNA_EGORA;

    public static void init() {
        // --- Базовая машина друга: нормис-фильтрация (сохранена один-в-один) ---
        for (int tier : ELECTRIC_TIERS) {
            String name = GTValues.VN[tier].toLowerCase() + "_normis_filtration_machine";

            NORMIS_FILTRATION_MACHINE[tier] = SkufAddon.REGISTRATE
                    .machine(name, holder -> new SkufTiltMachine(holder, tier))
                    .langValue(GTValues.VNF[tier] + " Normis Filtration Machine")
                    .recipeType(SkufRecipeTypes.NORMIS_FILTRATION_RECIPES)
                    .editableUI(SimpleTieredMachine.EDITABLE_UI_CREATOR.apply(
                            SkufAddon.id("normis_filtration_machine"),
                            SkufRecipeTypes.NORMIS_FILTRATION_RECIPES))
                    .workableTieredHullModel(SkufAddon.id("block/machines/normis_filtration_machine"))
                    .register();
        }

        // --- Простые машины-обработчики ArthurTech на тильт-логике друга ---
        registerTiltMachines(CNC_MACHINE, "cnc_machine", SkufRecipeTypes.CNC_RECIPES,
                " CNC Machine");
        registerTiltMachines(POT_DISTILLERY, "pot_distillery", SkufRecipeTypes.POT_DISTILLERY_RECIPES,
                " Pot Distillery");
        registerTiltMachines(VIBE_STABILIZER, "vibe_stabilizer", SkufRecipeTypes.VIBE_STABILIZER_RECIPES,
                " Vibe Stabilizer");

        // --- Мультиблоки ArthurTech ---
        MINI_FACTORY = SkufAddon.REGISTRATE
                .multiblock("mini_factory", WorkableElectricMultiblockMachine::new)
                .langValue("Mini-Factory of Correct Things")
                .rotationState(RotationState.NON_Y_AXIS)
                .recipeType(SkufRecipeTypes.CORRECT_FACTORY_RECIPES)
                .recipeModifier(GTRecipeModifiers.OC_NON_PERFECT)
                .appearanceBlock(SkufBlocks.CASING_POHUIT_REINFORCED)
                .pattern(definition -> FactoryBlockPattern.start()
                        .aisle("XXX", "XXX", "XXX")
                        .aisle("XXX", "X#X", "XXX")
                        .aisle("XXX", "XSX", "XXX")
                        .where('S', Predicates.controller(Predicates.blocks(definition.getBlock())))
                        .where('X', Predicates.blocks(SkufBlocks.CASING_POHUIT_REINFORCED.get())
                                .setMinGlobalLimited(14)
                                .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                                .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                                .or(Predicates.autoAbilities(true, false, false)))
                        .where('#', Predicates.air())
                        .build())
                .workableCasingModel(
                        SkufAddon.id("block/casing_pohuit_reinforced"),
                        GTCEu.id("block/multiblock/assembly_line"))
                .tooltips(
                        Component.literal("Финальная сборка Правильной Вещи"),
                        Component.literal("Структура: куб 3×3×3 из Усиленного Корпуса из Похуита"),
                        Component.literal("Нужны люки ввода/вывода и энергии"))
                .register();

        CHELYABINSK_PROVAL = SkufAddon.REGISTRATE
                .multiblock("chelyabinsk_proval", WorkableElectricMultiblockMachine::new)
                .langValue("Chelyabinsk Sinkhole")
                .rotationState(RotationState.NON_Y_AXIS)
                .recipeType(SkufRecipeTypes.CHELYABINSK_PROVAL_RECIPES)
                .recipeModifier(GTRecipeModifiers.OC_NON_PERFECT)
                .appearanceBlock(SkufBlocks.CASING_POHUIT_REINFORCED)
                .pattern(definition -> FactoryBlockPattern.start()
                        .aisle("XXX", "XXX", "XXX")
                        .aisle("XXX", "X#X", "XXX")
                        .aisle("XXX", "XSX", "XXX")
                        .where('S', Predicates.controller(Predicates.blocks(definition.getBlock())))
                        .where('X', Predicates.blocks(SkufBlocks.CASING_POHUIT_REINFORCED.get())
                                .setMinGlobalLimited(18)
                                .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                                .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                                .or(Predicates.autoAbilities(true, false, false)))
                        .where('#', Predicates.air())
                        .build())
                .workableCasingModel(
                        SkufAddon.id("block/casing_pohuit_reinforced"),
                        GTCEu.id("block/multiblock/bedrock_ore_miner"))
                .tooltips(
                        Component.literal("HV-переработка Челябинского сланца в Уральский Изотоп"),
                        Component.literal("Структура: куб 3×3×3 из Усиленного Корпуса из Похуита"),
                        Component.literal("Осторожно: радиоактивные побочные продукты"))
                .register();

        SAUNA_EGORA = SkufAddon.REGISTRATE
                .multiblock("sauna_egora", WorkableElectricMultiblockMachine::new)
                .langValue("Sauna of Egor")
                .rotationState(RotationState.NON_Y_AXIS)
                .recipeType(SkufRecipeTypes.SAUNA_EGORA_RECIPES)
                .recipeModifier(GTRecipeModifiers.OC_NON_PERFECT)
                .appearanceBlock(SkufBlocks.CASING_POHUIT_REINFORCED)
                .pattern(definition -> FactoryBlockPattern.start()
                        .aisle("XXXXXXX", "XXXXXXX", "XXXXXXX", "XXXSXXX", "XXXXXXX", "XXXXXXX", "XXXXXXX")
                        .aisle("XXXXXXX", "X#####X", "X#####X", "X#####X", "X#####X", "X#####X", "XXXXXXX")
                        .aisle("XXXXXXX", "X#####X", "X#####X", "X#####X", "X#####X", "X#####X", "XXXXXXX")
                        .aisle("XXXXXXX", "X#####X", "X#####X", "X#####X", "X#####X", "X#####X", "XXXXXXX")
                        .aisle("XXXXXXX", "X#####X", "X#####X", "X#####X", "X#####X", "X#####X", "XXXXXXX")
                        .aisle("XXXXXXX", "X#####X", "X#####X", "X#####X", "X#####X", "X#####X", "XXXXXXX")
                        .aisle("XXXXXXX", "XXXXXXX", "XXXXXXX", "XXXXXXX", "XXXXXXX", "XXXXXXX", "XXXXXXX")
                        .where('S', Predicates.controller(Predicates.blocks(definition.getBlock())))
                        .where('X', Predicates.blocks(SkufBlocks.CASING_POHUIT_REINFORCED.get())
                                .setMinGlobalLimited(180)
                                .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                                .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(3))
                                .or(Predicates.autoAbilities(true, false, false)))
                        .where('#', Predicates.air())
                        .build())
                .workableCasingModel(
                        SkufAddon.id("block/casing_pohuit_reinforced"),
                        GTCEu.id("block/multiblock/distillation_tower"))
                .tooltips(
                        Component.literal("EV-термодинамика: вода + Стабилизированный Вайб → пар + слёзы"),
                        Component.literal("Структура: полый куб 7×7×7 из Усиленного Корпуса из Похуита"),
                        Component.literal("Дорогая по замыслу — не бесплатная кнопка"))
                .register();
    }

    /**
     * Регистрирует простую машину-обработчик ArthurTech на всех электрических тирах, используя
     * тильт-логику друга ({@link SkufTiltMachine}). Аналог его {@code NORMIS_FILTRATION_MACHINE}.
     */
    private static void registerTiltMachines(MachineDefinition[] target, String baseName,
                                             GTRecipeType recipeType, String langSuffix) {
        for (int tier : ELECTRIC_TIERS) {
            String name = GTValues.VN[tier].toLowerCase() + "_" + baseName;
            target[tier] = SkufAddon.REGISTRATE
                    .machine(name, holder -> new SkufTiltMachine(holder, tier))
                    .langValue(GTValues.VNF[tier] + langSuffix)
                    .recipeType(recipeType)
                    .editableUI(SimpleTieredMachine.EDITABLE_UI_CREATOR.apply(
                            SkufAddon.id(baseName),
                            recipeType))
                    .workableTieredHullModel(SkufAddon.id("block/machines/" + baseName))
                    .register();
        }
    }
}

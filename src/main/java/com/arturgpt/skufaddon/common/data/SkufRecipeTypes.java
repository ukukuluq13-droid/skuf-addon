package com.arturgpt.skufaddon.common.data;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;

import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ELECTRIC;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.register;

/**
 * Типы рецептов Skuf Addon / ArthurTech.
 *
 * <p>Базовый тип {@code NORMIS_FILTRATION_RECIPES} — наработка шаблона (kalashnikov-dev), не трогаем:
 * это рецепты «Нормис-фильтра» (Normis Filtration Machine на SkufTiltMachine). Наш старый отдельный
 * тип normis_filter намеренно НЕ переносится — он дублировал бы этот, машина мапится на него.</p>
 *
 * <p>Ниже добавлены типы рецептов под остальные машины ArthurTech. Каждый повторяет конфигурацию
 * аналогичной ванильной GregTech-машины (слоты/EU/прогресс-бар/звук), чтобы авто-GUI и категории
 * JEI/EMI работали из коробки.</p>
 */
public class SkufRecipeTypes {

    // --- Базовый тип шаблона (наработка kalashnikov-dev — НЕ удалять) ---
    public static final GTRecipeType NORMIS_FILTRATION_RECIPES = register("normis_filtration", ELECTRIC)
            .setMaxIOSize(1, 1, 1, 1)
            .setSlotOverlay(true, true, GuiTextures.FLUID_SLOT)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL)
            .setEUIO(IO.IN);

    // --- Типы рецептов ArthurTech ---

    /** ЧПУ-станок — точная обработка (биты, фрезы, «Правильная Вещь»). */
    public static final GTRecipeType CNC_RECIPES = register("cnc_machine", ELECTRIC)
            .setMaxIOSize(3, 1, 1, 0)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_EXTRACT, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MACERATOR)
            .setMaxTooltips(3);

    /** Пот-Дистиллятор — разгоняет пот/жижняк на более чистые фракции. */
    public static final GTRecipeType POT_DISTILLERY_RECIPES = register("pot_distillery", ELECTRIC)
            .setMaxIOSize(1, 1, 1, 1)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_DISTILLATION_TOWER, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL)
            .setMaxTooltips(3);

    /** Стабилизатор Вайба — рафинирует пот/материю в Стабилизированный Вайб (Фаза 3). */
    public static final GTRecipeType VIBE_STABILIZER_RECIPES = register("vibe_stabilizer", ELECTRIC)
            .setMaxIOSize(1, 1, 1, 1)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MIXER, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL)
            .setMaxTooltips(3);

    /** Мини-Фабрика Правильных Вещей — мультиблок, финальная сборка Правильной Вещи (Фаза 3). */
    public static final GTRecipeType CORRECT_FACTORY_RECIPES = register("correct_factory", ELECTRIC)
            .setMaxIOSize(6, 2, 3, 1)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.ASSEMBLER)
            .setMaxTooltips(4);

    /** Челябинский Провал — HV-мультиблок: дробит/обогащает сланец в Уральский Изотоп (Фаза 4). */
    public static final GTRecipeType CHELYABINSK_PROVAL_RECIPES = register("chelyabinsk_proval", ELECTRIC)
            .setMaxIOSize(4, 4, 2, 2)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MACERATOR)
            .setMaxTooltips(4);

    /** Модуль «Ваще похуй» — мультиблок: конденсирует скрытый пот в сгущённый (Фаза 5). */
    public static final GTRecipeType VSEM_POHUI_RECIPES = register("vsem_pohui", ELECTRIC)
            .setMaxIOSize(0, 0, 1, 1)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MIXER, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.CHEMICAL)
            .setMaxTooltips(3);

    /** Сауна Егора — EV-мультиблок: вода + вайб → тёплый пар + технические слёзы (Фаза 7). */
    public static final GTRecipeType SAUNA_EGORA_RECIPES = register("sauna_egora", ELECTRIC)
            .setMaxIOSize(1, 1, 3, 2)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_BATH, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.COOLING)
            .setMaxTooltips(4);

    public static void init() {
        // Гарантируем инициализацию static-полей во время регистрации типов рецептов GTCEu.
        var unused = NORMIS_FILTRATION_RECIPES;
    }
}

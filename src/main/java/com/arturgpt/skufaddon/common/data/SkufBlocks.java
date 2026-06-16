package com.arturgpt.skufaddon.common.data;

import com.arturgpt.skufaddon.SkufAddon;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import com.tterrag.registrate.util.entry.BlockEntry;

/**
 * Блоки Skuf Addon / ArthurTech.
 *
 * <p>Пока что — корпуса (casings) для мультиблоков «Дихотомии Пота и Угара». Регистрируются через тот
 * же {@code SkufAddon.REGISTRATE}, что материалы/машины. Блок-модель и предмет блока поставляются
 * готовыми ресурсами (assets/skufaddon/{blockstates,models,textures}), чтобы не требовать data-gen.</p>
 *
 * <p>Корпус — конструкционная основа мультиблоков Фазы 3+ (Мини-Фабрика Правильных Вещей,
 * Стабилизатор-колонна и т.д.): работает как «стена» структуры в
 * {@code com.gregtechceu.gtceu.api.pattern.Predicates#blocks}.</p>
 */
public class SkufBlocks {

    /** Усиленный Корпус из Похуита — базовый конструкционный блок для мультиблоков. */
    public static BlockEntry<Block> CASING_POHUIT_REINFORCED;

    public static void init() {
        CASING_POHUIT_REINFORCED = SkufAddon.REGISTRATE
                .block("casing_pohuit_reinforced", Block::new)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.requiresCorrectToolForDrops())
                .simpleItem()
                .register();
    }
}

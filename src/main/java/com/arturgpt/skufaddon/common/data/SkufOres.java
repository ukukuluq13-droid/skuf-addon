package com.arturgpt.skufaddon.common.data;

import com.arturgpt.skufaddon.SkufAddon;

import com.gregtechceu.gtceu.api.data.worldgen.WorldGenLayers;
import com.gregtechceu.gtceu.common.data.GTOres;

import net.minecraft.tags.BiomeTags;
import net.minecraft.util.valueproviders.UniformInt;

/**
 * Рудные жилы Skuf Addon / ArthurTech.
 *
 * <p>Разблокировка старта (концепция v3 §1.1, ответы Derek A1–A2): базовые металлы skufit и pokhuit,
 * а также радиоактивный chelyabinsk_shale теперь генерируются в мире жилами. Дизайн распределения —
 * в духе Monifactory: ранний доступный металл у поверхности, второй металл и опасная руда — глубже.</p>
 *
 * <p>Регистрация: вызывается из {@code SkufGTAddon.registerOreVeins()} — это хук GTCEu для аддонов
 * (GTOreLoader → {@code IGTAddon::registerOreVeins}). {@link GTOres#create} конфигурирует и
 * регистрирует жилу в {@code GTRegistries.ORE_VEINS}.</p>
 */
public class SkufOres {

    public static void init() {
        // Скуфит — ранний доступный металл. Часто, неглубоко, в камне (как медь/олово у Monifactory).
        GTOres.create(SkufAddon.id("skufit_vein"), vein -> vein
                .clusterSize(UniformInt.of(24, 40))
                .density(0.35f)
                .weight(70)
                .layer(WorldGenLayers.STONE)
                .heightRangeUniform(16, 90)
                .biomes(BiomeTags.IS_OVERWORLD)
                .cuboidVeinGenerator(generator -> generator
                        .top(b -> b.mat(SkufMaterials.skufit).size(2))
                        .middle(b -> b.mat(SkufMaterials.skufit).size(3))
                        .bottom(b -> b.mat(SkufMaterials.skufit).size(2))
                        .spread(b -> b.mat(SkufMaterials.skufit))));

        // Похуит — стабильный сплав-металл, нужен для корпусов мультиблоков. Глубже, в сланце.
        GTOres.create(SkufAddon.id("pokhuit_vein"), vein -> vein
                .clusterSize(UniformInt.of(20, 32))
                .density(0.28f)
                .weight(45)
                .layer(WorldGenLayers.DEEPSLATE)
                .heightRangeUniform(-50, 24)
                .biomes(BiomeTags.IS_OVERWORLD)
                .cuboidVeinGenerator(generator -> generator
                        .top(b -> b.mat(SkufMaterials.pokhuit).size(2))
                        .middle(b -> b.mat(SkufMaterials.pokhuit).size(3))
                        .bottom(b -> b.mat(SkufMaterials.pokhuit).size(2))
                        .spread(b -> b.mat(SkufMaterials.pokhuit))));

        // Челябинский сланец — радиоактивная руда HV-этапа (источник uralIsotope). Редкая, глубокая.
        GTOres.create(SkufAddon.id("chelyabinsk_shale_vein"), vein -> vein
                .clusterSize(UniformInt.of(16, 28))
                .density(0.2f)
                .weight(25)
                .layer(WorldGenLayers.DEEPSLATE)
                .heightRangeUniform(-58, 8)
                .biomes(BiomeTags.IS_OVERWORLD)
                .cuboidVeinGenerator(generator -> generator
                        .top(b -> b.mat(SkufMaterials.chelyabinskShale).size(2))
                        .middle(b -> b.mat(SkufMaterials.chelyabinskShale).size(3))
                        .bottom(b -> b.mat(SkufMaterials.chelyabinskShale).size(2))
                        .spread(b -> b.mat(SkufMaterials.chelyabinskShale))));
    }
}

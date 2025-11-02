package com.falaut.kylin_arm.event;

import com.falaut.kylin_arm.KylinArm;
import com.falaut.kylin_arm.config.KylinArmConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Optional;

public class KylinArmHandler {
    public static boolean isKylinArmEquipped(LivingEntity entity) {
        return CuriosApi.getCuriosInventory(entity).flatMap(curiosInventory -> curiosInventory.findFirstCurio(KylinArm.KYLIN_ARM.get())).isPresent();
    }

    public static Optional<Tier> getToolTier() {
        return switch (KylinArmConfig.HARVEST_LEVEL.get()) {
            case 0 -> Optional.empty();
            case 1 -> Optional.of(Tiers.WOOD);
            case 2 -> Optional.of(Tiers.STONE);
            case 3 -> Optional.of(Tiers.IRON);
            case 4 -> Optional.of(Tiers.DIAMOND);
            default -> Optional.of(Tiers.NETHERITE);
        };
    }

    public static boolean isCorrectTierForDrops(int harvestLevel, BlockState state) {
        if (!state.requiresCorrectToolForDrops()) {
            return true;
        }

        if (state.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return harvestLevel >= 4;
        } else if (state.is(BlockTags.NEEDS_IRON_TOOL)) {
            return harvestLevel >= 3;
        } else if (state.is(BlockTags.NEEDS_STONE_TOOL)) {
            return harvestLevel >= 2;
        } else {
            return harvestLevel >= 1;
        }
    }

    public static boolean canKylinArmHarvest(LivingEntity entity, BlockState state) {
        if (isKylinArmEquipped(entity) && entity instanceof Player player) {
            Optional<Tier> tier = getToolTier();

            if (tier.isPresent()) {
                int harvestLevel = KylinArmConfig.HARVEST_LEVEL.get();

                return state.is(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("kylin_arm", "mineable/kylin_arm")))
                        && isCorrectTierForDrops(harvestLevel, state);
            }
        }
        return false;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onHarvestCheck(PlayerEvent.HarvestCheck event) {
        Player player = event.getEntity();
        event.setCanHarvest(event.canHarvest() || canKylinArmHarvest(player, event.getTargetBlock()));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();

        if (isKylinArmEquipped(player)) {
            float baseSpeed = event.getOriginalSpeed() * KylinArmConfig.BREAK_SPEED_BONUS.get().floatValue();
            float multiplier = 1;

            boolean isInWater = player.isEyeInFluidType(NeoForgeMod.WATER_TYPE.value());
            boolean isInAir = !player.onGround();

            if (isInWater || isInAir) {
                multiplier *= 5;
            }
            if (isInWater && isInAir) {
                multiplier *= 5;
            }

            event.setNewSpeed(baseSpeed * multiplier);
        }
    }
}
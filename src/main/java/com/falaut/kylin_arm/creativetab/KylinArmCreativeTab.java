package com.falaut.kylin_arm.creativetab;

import com.falaut.kylin_arm.KylinArm;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class KylinArmCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, KylinArm.MODID);

    public static final Supplier<CreativeModeTab> KYLIN_ARM_TAB = CREATIVE_MODE_TABS.register("kylin_arm_creative_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("item.kylin_arm.kylin_arm"))
                    .icon(() -> KylinArm.KYLIN_ARM.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(KylinArm.KYLIN_ARM.get());
                    }).build());
}
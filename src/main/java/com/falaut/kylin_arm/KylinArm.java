package com.falaut.kylin_arm;

import com.falaut.kylin_arm.config.KylinArmConfig;
import com.falaut.kylin_arm.creativetab.KylinArmCreativeTab;
import com.falaut.kylin_arm.event.KylinArmHandler;
import com.falaut.kylin_arm.item.KylinArmItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@Mod(KylinArm.MODID)
public class KylinArm {
    public static final String MODID = "kylin_arm";
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MODID);
    public static final Supplier<Item> KYLIN_ARM = ITEMS.register("kylin_arm", () -> new KylinArmItem(new Item.Properties().stacksTo(1)));

    public KylinArm(IEventBus modEventBus, ModContainer modContainer) {
        ITEMS.register(modEventBus);

        KylinArmCreativeTab.CREATIVE_MODE_TABS.register(modEventBus);

        NeoForge.EVENT_BUS.register(KylinArmHandler.class);

        modContainer.registerConfig(ModConfig.Type.COMMON, KylinArmConfig.SPEC, "kylin_arm.toml");
    }
}
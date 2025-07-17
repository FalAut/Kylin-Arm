package com.falaut.kylin_arm;

import com.falaut.kylin_arm.config.KylinArmConfig;
import com.falaut.kylin_arm.creativetab.KylinArmCreativeTab;
import com.falaut.kylin_arm.event.KylinArmHandler;
import com.falaut.kylin_arm.item.KylinArmItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(KylinArm.MODID)
public class KylinArm {
    public static final String MODID = "kylin_arm";
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<Item> KYLIN_ARM = ITEMS.register("kylin_arm", () -> new KylinArmItem(new Item.Properties().stacksTo(1)));

    public KylinArm() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ITEMS.register(modEventBus);
        KylinArmCreativeTab.CREATIVE_MODE_TABS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(KylinArmHandler.class);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, KylinArmConfig.SPEC, "kylin_arm.toml");
    }
}
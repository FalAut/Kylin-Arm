package com.falaut.kylin_arm.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class KylinArmConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.IntValue HARVEST_LEVEL;
    public static final ModConfigSpec.DoubleValue BREAK_SPEED_BONUS;

    static {
        BUILDER.push("Kylin Arm Configuration");

        HARVEST_LEVEL = BUILDER
                .comment("The harvest level provided by Kylin Arm (0=none, 1=wood, 2=stone, 3=iron, 4=diamond, 5=netherite)")
                .defineInRange("harvestLevel", 5, 0, 5);

        BREAK_SPEED_BONUS = BUILDER
                .comment("break speed bonus when Kylin Arm is equipped")
                .defineInRange("breakSpeedBonus", 10.0, 1.0, 100.0);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
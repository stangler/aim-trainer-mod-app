package com.aimtrainer;

import com.aimtrainer.command.CommandAimTrainer;
import com.aimtrainer.entity.EntityAimDummy;
import com.aimtrainer.proxy.ClientProxy;
import com.aimtrainer.proxy.IProxy;
import com.aimtrainer.proxy.ServerProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid = AimTrainerMod.MODID, version = AimTrainerMod.VERSION, name = AimTrainerMod.NAME)
public class AimTrainerMod {

    public static final String MODID   = "aimtrainer";
    public static final String VERSION = "1.0.0";
    public static final String NAME    = "AimTrainer";

    @Instance(MODID)
    public static AimTrainerMod instance;

    @SidedProxy(
        clientSide = "com.aimtrainer.proxy.ClientProxy",
        serverSide  = "com.aimtrainer.proxy.ServerProxy"
    )
    public static IProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // エンティティ登録（ID=1, 追跡距離64, 更新頻度1tick）
        EntityRegistry.registerModEntity(
            EntityAimDummy.class, "AimDummy", 1, this, 64, 1, true
        );
        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandAimTrainer());
    }
}

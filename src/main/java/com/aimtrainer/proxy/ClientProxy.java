package com.aimtrainer.proxy;

import com.aimtrainer.AimTrainerEventHandler;
import com.aimtrainer.entity.EntityAimDummy;
import com.aimtrainer.entity.RenderAimDummy;
import com.aimtrainer.gui.AimTrainerHUD;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy implements IProxy {

    @Override
    public void preInit() {}

    @Override
    public void init() {
        // ダミーエンティティのレンダラー登録
        RenderingRegistry.registerEntityRenderingHandler(
            EntityAimDummy.class,
            manager -> new RenderAimDummy(manager)
        );

        // HUD（統計オーバーレイ）登録
        MinecraftForge.EVENT_BUS.register(new AimTrainerHUD());

        // ヒット/ミス検出イベント登録
        MinecraftForge.EVENT_BUS.register(new AimTrainerEventHandler());
    }
}

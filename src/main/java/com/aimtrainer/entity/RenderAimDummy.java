package com.aimtrainer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

/**
 * ダミーエンティティをヒューマノイド（バイペッド）として描画する
 * ゾンビのスキンを流用（変更可能）
 */
public class RenderAimDummy extends RenderBiped<EntityAimDummy> {

    // 難易度ごとに色分けしたスキン（好みで変更可）
    private static final ResourceLocation TEX_EASY   =
        new ResourceLocation("textures/entity/zombie/zombie.png");
    private static final ResourceLocation TEX_NORMAL =
        new ResourceLocation("textures/entity/zombie/zombie.png");
    private static final ResourceLocation TEX_HARD   =
        new ResourceLocation("textures/entity/zombie/zombie.png");

    public RenderAimDummy(RenderManager renderManager) {
        super(renderManager, new ModelBiped(0f), 0.5f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAimDummy entity) {
        switch (entity.getDifficulty()) {
            case EASY:   return TEX_EASY;
            case HARD:   return TEX_HARD;
            default:     return TEX_NORMAL;
        }
    }
}

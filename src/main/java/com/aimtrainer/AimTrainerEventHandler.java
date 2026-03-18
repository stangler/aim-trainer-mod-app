package com.aimtrainer;

import com.aimtrainer.entity.EntityAimDummy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AimTrainerEventHandler {

    private final AimTrainerStats stats = AimTrainerStats.getInstance();

    /**
     * プレイヤーがエンティティを攻撃した瞬間に発火（attackEntityFromより前）
     * → ダミーへのヒットとして登録
     */
    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        if (!stats.isActive()) return;
        if (event.target instanceof EntityAimDummy
                && event.entityPlayer instanceof EntityPlayer) {
            stats.registerHit();
        }
    }

    /**
     * 空振り（LEFT_CLICK_AIR）の代替：ブロックを左クリックした場合をミスとして登録
     * ※ 空中スイングはMinecraft側のイベントでは検出が難しいため暫定対応
     */
    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!stats.isActive()) return;
        if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
            stats.registerMiss();
        }
    }
}
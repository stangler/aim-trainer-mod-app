package com.aimtrainer.gui;

import com.aimtrainer.AimTrainerStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 画面右上にAimTrainer統計を常時表示するHUDオーバーレイ
 * セッションが開始されていない場合は何も描画しない
 */
public class AimTrainerHUD extends Gui {

    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        // TEXT レイヤーのみに描画（他のHUD要素と競合しない）
        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) return;

        AimTrainerStats stats = AimTrainerStats.getInstance();
        if (!stats.isActive()) return;

        ScaledResolution sr = new ScaledResolution(mc);
        int screenW = sr.getScaledWidth();

        // 表示する統計行
        String[] lines = {
            "§6§l● AimTrainer §r§aACTIVE",
            "§7ヒット   §a" + stats.getHits(),
            "§7ミス     §c" + stats.getMisses(),
            "§7命中率   §e" + String.format("%.1f%%", stats.getAccuracy()),
            "§7スイング §b" + stats.getTotalSwings(),
            "§7経過     §b" + formatTime(stats.getElapsedSeconds()),
        };

        int lineHeight = 10;
        int padding    = 4;
        int boxW       = 110;
        int boxH       = lines.length * lineHeight + padding * 2;
        int boxX       = screenW - boxW - 4;
        int boxY       = 4;

        // 半透明背景
        drawRect(boxX - 2, boxY - 2, boxX + boxW, boxY + boxH, 0x88000000);

        // テキスト描画
        for (int i = 0; i < lines.length; i++) {
            mc.fontRendererObj.drawStringWithShadow(
                lines[i],
                boxX,
                boxY + padding + i * lineHeight,
                0xFFFFFF
            );
        }
    }

    private String formatTime(long seconds) {
        long m = seconds / 60;
        long s = seconds % 60;
        return String.format("%d:%02d", m, s);
    }
}

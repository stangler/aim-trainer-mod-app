package com.aimtrainer;

/**
 * セッション統計（ヒット数・スイング数・精度）を管理するシングルトン
 */
public class AimTrainerStats {

    private static final AimTrainerStats INSTANCE = new AimTrainerStats();

    private int hits          = 0;  // ダミーに当たった回数
    private int totalSwings   = 0;  // 総スイング数（ヒット + ミス）
    private long sessionStart = 0;
    private boolean active    = false;

    private AimTrainerStats() {}

    public static AimTrainerStats getInstance() {
        return INSTANCE;
    }

    /** セッション開始 */
    public void startSession() {
        hits        = 0;
        totalSwings = 0;
        sessionStart = System.currentTimeMillis();
        active      = true;
    }

    /** セッション終了 */
    public void endSession() {
        active = false;
    }

    /** ダミーへのヒット登録 */
    public void registerHit() {
        if (active) {
            hits++;
            totalSwings++;
        }
    }

    /** 空振り（ミス）登録 */
    public void registerMiss() {
        if (active) {
            totalSwings++;
        }
    }

    public int getHits()        { return hits; }
    public int getMisses()      { return Math.max(0, totalSwings - hits); }
    public int getTotalSwings() { return totalSwings; }

    /** 命中率（%）*/
    public float getAccuracy() {
        if (totalSwings == 0) return 0f;
        return (float) hits / totalSwings * 100f;
    }

    /** 経過時間（秒）*/
    public long getElapsedSeconds() {
        if (!active) return 0;
        return (System.currentTimeMillis() - sessionStart) / 1000L;
    }

    /** 1分あたりのヒット数 */
    public float getHitsPerMinute() {
        long sec = getElapsedSeconds();
        if (sec == 0) return 0f;
        return hits / (sec / 60f);
    }

    public boolean isActive() { return active; }

    public void reset() {
        hits        = 0;
        totalSwings = 0;
    }
}

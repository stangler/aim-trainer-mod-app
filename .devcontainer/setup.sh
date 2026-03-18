#!/usr/bin/env bash
set -e

echo "================================================="
echo "  AimTrainer Mod - Dev Container セットアップ"
echo "================================================="

# gradlew に実行権限を付与
chmod +x ./gradlew

# ForgeGradle の依存関係 + Minecraft ソースを解凍・逆コンパイル
# ※ 初回は15〜20分かかります
echo ""
echo "[1/2] Forge 依存関係をセットアップ中..."
echo "      (初回のみ時間がかかります、しばらくお待ちください)"
./gradlew setupDecompWorkspace --refresh-dependencies

# VS Code / Eclipse 向けの .classpath, .project を生成
echo ""
echo "[2/2] VS Code 向けプロジェクトファイルを生成中..."
./gradlew eclipse

echo ""
echo "================================================="
echo "  ✅ セットアップ完了！"
echo ""
echo "  よく使うコマンド:"
echo "    ./gradlew build          - MODをビルド (.jar)"
echo "    ./gradlew runClient      - Minecraft クライアントを起動"
echo "    ./gradlew eclipse        - プロジェクトファイルを再生成"
echo "================================================="

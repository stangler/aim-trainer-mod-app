# AimTrainer Mod

BedWars PvP のエイム練習を個人でできる Minecraft 1.8.9 Forge Mod です。
動くダミーエンティティを召喚し、ヒット率・命中率をリアルタイムで計測します。

---

## 動作環境

- Minecraft 1.8.9
- Forge 11.15.1.2318

---

## インストール

1. [Forge 1.8.9](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.8.9.html) をインストール
2. `AimTrainer-1.0.0.jar` を `.minecraft/mods/` フォルダに入れる
3. Forge プロファイルで Minecraft を起動

---

## 遊び方

### 基本の流れ

1. `/aim start` でダミーをスポーン・セッション開始
2. ダミーを殴り続ける（ヒット/ミスが自動記録）
3. `/aim stop` で終了・最終統計を確認

---

## コマンド一覧

| コマンド | 説明 |
|---|---|
| `/aim start` | ノーマル難易度・1体でスタート |
| `/aim start easy` | イージー難易度でスタート |
| `/aim start normal` | ノーマル難易度でスタート |
| `/aim start hard` | ハード難易度でスタート |
| `/aim start normal 3` | 指定難易度・指定体数でスタート（最大5体） |
| `/aim stop` | セッション終了・最終統計を表示 |
| `/aim stats` | 現在の統計を表示 |
| `/aim help` | コマンド一覧を表示 |

エイリアス: `/aimtrainer`, `/at`

---

## 難易度

| 難易度 | 移動速度 | 方向転換 | ジャンプ |
|---|---|---|---|
| easy   | 遅い | ゆっくり | ほぼなし |
| normal | 普通 | 普通     | たまに   |
| hard   | 速い | 頻繁     | よくする |

---

## HUD（画面右上）

セッション中は以下の統計がリアルタイム表示されます。

| 項目 | 説明 |
|---|---|
| ヒット数 | ダミーに当たった回数 |
| ミス数 | 外した・空振りした回数 |
| 総スイング | ヒット + ミスの合計 |
| 命中率 | ヒット ÷ 総スイング × 100 (%) |
| 経過時間 | セッション開始からの経過時間 |

---

## おすすめ練習法

- まず `easy 1体` から始めて操作に慣れる
- 命中率 **80% 以上**を安定して出せたら難易度を上げる
- `normal 3体` は BedWars の乱戦を想定した練習に最適
- 1セッション **3〜5分** を目安に繰り返す

---

## 注意事項

- ダミーはダメージを受けず倒せません（練習専用）
- `/aim start` を再実行すると統計がリセットされます
- `/aim stop` で終了しないと統計が記録されたままになります
- チートオフのワールドでも使用可能です

---

## 開発環境のセットアップ

### 必要なもの

- Docker
- VS Code + [Dev Containers 拡張](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers)

### 手順

```bash
# 1. VS Code でプロジェクトを開く
code .

# 2. 右下の「Reopen in Container」をクリック
#    （または F1 → Dev Containers: Reopen in Container）

# 3. コンテナ起動後、ターミナルで実行
./gradlew setupDecompWorkspace
./gradlew eclipse
./gradlew build
```

ビルド成功後、`build/libs/AimTrainer-1.0.0.jar` が生成されます。

---

## ライセンス

MIT License
# Forge 1.8.9 は Java 8 が必須
FROM eclipse-temurin:8-jdk-jammy

# 基本ツールのインストール
RUN apt-get update && apt-get install -y \
    git \
    curl \
    unzip \
    wget \
    bash \
    && rm -rf /var/lib/apt/lists/*

# Gradle 2.14 をインストール（ForgeGradle 2.x と互換）
ARG GRADLE_VERSION=2.14
RUN wget -q https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -O /tmp/gradle.zip \
    && unzip -q /tmp/gradle.zip -d /opt \
    && ln -s /opt/gradle-${GRADLE_VERSION}/bin/gradle /usr/local/bin/gradle \
    && rm /tmp/gradle.zip

# vscode ユーザー作成（VS Code Dev Container 標準）
RUN groupadd --gid 1000 vscode \
    && useradd --uid 1000 --gid vscode --shell /bin/bash --create-home vscode

# 作業ディレクトリ
WORKDIR /workspace
RUN chown vscode:vscode /workspace

USER vscode

# Gradle キャッシュを事前に温めるためのディレクトリ
RUN mkdir -p /home/vscode/.gradle

package com.aimtrainer.command;

import com.aimtrainer.AimTrainerStats;
import com.aimtrainer.entity.EntityAimDummy;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class CommandAimTrainer extends CommandBase {

    @Override
    public String getCommandName() { return "aim"; }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/aim <start|stop|stats|help> [easy|normal|hard] [count]";
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("aimtrainer", "at");
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true; // 全プレイヤーに許可
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sendHelp(sender);
            return;
        }
        switch (args[0].toLowerCase()) {
            case "start": cmdStart(sender, args); break;
            case "stop":  cmdStop(sender);        break;
            case "stats": cmdStats(sender);       break;
            default:
                sendMsg(sender, EnumChatFormatting.RED + "不明なサブコマンド: " + args[0]);
                sendHelp(sender);
        }
    }

    private void cmdStart(ICommandSender sender, String[] args) {
        EntityPlayer player = getPlayer(sender);
        if (player == null) {
            sendMsg(sender, EnumChatFormatting.RED + "プレイヤーのみ使用可能です");
            return;
        }

        EntityAimDummy.Difficulty diff = EntityAimDummy.Difficulty.NORMAL;
        if (args.length >= 2) {
            switch (args[1].toLowerCase()) {
                case "easy":   diff = EntityAimDummy.Difficulty.EASY;   break;
                case "normal": diff = EntityAimDummy.Difficulty.NORMAL; break;
                case "hard":   diff = EntityAimDummy.Difficulty.HARD;   break;
                default:
                    sendMsg(sender, EnumChatFormatting.RED + "難易度は easy / normal / hard で指定してください");
                    return;
            }
        }

        int count = 1;
        if (args.length >= 3) {
            try {
                count = Math.min(5, Math.max(1, Integer.parseInt(args[2])));
            } catch (NumberFormatException e) {
                sendMsg(sender, EnumChatFormatting.RED + "count は数値で指定してください（1〜5）");
                return;
            }
        }

        removeAllDummies(player.worldObj);
        AimTrainerStats.getInstance().startSession();

        World world = player.worldObj;
        for (int i = 0; i < count; i++) {
            EntityAimDummy dummy = new EntityAimDummy(world);
            dummy.setDifficulty(diff);
            double offsetX = (world.rand.nextDouble() - 0.5) * 6;
            double offsetZ = (world.rand.nextDouble() - 0.5) * 6;
            dummy.setLocationAndAngles(
                player.posX + offsetX, player.posY, player.posZ + offsetZ, 0, 0
            );
            world.spawnEntityInWorld(dummy);
        }

        sendMsg(sender, EnumChatFormatting.GREEN + "✔ AimTrainer開始！"
            + EnumChatFormatting.YELLOW + " 難易度: " + difficultyJP(diff)
            + EnumChatFormatting.AQUA   + " | ダミー: " + count + "体");
        sendMsg(sender, EnumChatFormatting.GRAY + "終了: /aim stop   統計: /aim stats");
    }

    private void cmdStop(ICommandSender sender) {
        EntityPlayer player = getPlayer(sender);
        if (player == null) return;

        int removed = removeAllDummies(player.worldObj);
        AimTrainerStats stats = AimTrainerStats.getInstance();
        stats.endSession();

        sendMsg(sender, EnumChatFormatting.RED + "✖ セッション終了");
        printStats(sender, stats);
        sendMsg(sender, EnumChatFormatting.GRAY + "（" + removed + "体のダミーを削除しました）");
    }

    private void cmdStats(ICommandSender sender) {
        AimTrainerStats stats = AimTrainerStats.getInstance();
        if (!stats.isActive()) {
            sendMsg(sender, EnumChatFormatting.YELLOW + "セッションが開始されていません。/aim start で開始してください");
            return;
        }
        printStats(sender, stats);
    }

    private void printStats(ICommandSender sender, AimTrainerStats s) {
        sendMsg(sender, EnumChatFormatting.GOLD  + "========= AimTrainer 統計 =========");
        sendMsg(sender, EnumChatFormatting.WHITE + "  ヒット数   : " + EnumChatFormatting.GREEN  + s.getHits());
        sendMsg(sender, EnumChatFormatting.WHITE + "  ミス数     : " + EnumChatFormatting.RED    + s.getMisses());
        sendMsg(sender, EnumChatFormatting.WHITE + "  総スイング : " + EnumChatFormatting.AQUA   + s.getTotalSwings());
        sendMsg(sender, EnumChatFormatting.WHITE + "  命中率     : " + EnumChatFormatting.YELLOW + String.format("%.1f%%", s.getAccuracy()));
        sendMsg(sender, EnumChatFormatting.WHITE + "  経過時間   : " + EnumChatFormatting.AQUA   + s.getElapsedSeconds() + "秒");
        sendMsg(sender, EnumChatFormatting.GOLD  + "===================================");
    }

    @SuppressWarnings("unchecked")
    private int removeAllDummies(World world) {
        List<EntityAimDummy> dummies = world.getEntities(EntityAimDummy.class, e -> true);
        for (EntityAimDummy d : dummies) d.setDead();
        return dummies.size();
    }

    private EntityPlayer getPlayer(ICommandSender sender) {
        try {
            return getCommandSenderAsPlayer(sender);
        } catch (PlayerNotFoundException e) {
            return null;
        }
    }

    private void sendHelp(ICommandSender sender) {
        sendMsg(sender, EnumChatFormatting.GOLD   + "=== AimTrainer コマンド ===");
        sendMsg(sender, EnumChatFormatting.YELLOW + "/aim start [easy|normal|hard] [1-5]" + EnumChatFormatting.WHITE + " - 練習開始");
        sendMsg(sender, EnumChatFormatting.YELLOW + "/aim stop"  + EnumChatFormatting.WHITE + " - 練習終了");
        sendMsg(sender, EnumChatFormatting.YELLOW + "/aim stats" + EnumChatFormatting.WHITE + " - 統計表示");
    }

    private void sendMsg(ICommandSender sender, String msg) {
        sender.addChatMessage(new ChatComponentText(msg));
    }

    private String difficultyJP(EntityAimDummy.Difficulty d) {
        switch (d) {
            case EASY:  return "イージー";
            case HARD:  return "ハード";
            default:    return "ノーマル";
        }
    }

    @Override
    public int getRequiredPermissionLevel() { return 0; }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1)
            return getListOfStringsMatchingLastWord(args, "start", "stop", "stats", "help");
        if (args.length == 2 && args[0].equalsIgnoreCase("start"))
            return getListOfStringsMatchingLastWord(args, "easy", "normal", "hard");
        return null;
    }
}
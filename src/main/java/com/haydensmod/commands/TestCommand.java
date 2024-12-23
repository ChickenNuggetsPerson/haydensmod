package com.haydensmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class TestCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(CommandManager.literal("test").executes(TestCommand::run));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        
        ServerPlayerEntity player = context.getSource().getPlayer();
        player.addVelocity(new Vec3d(0.0, 10.0, 0.0));
        player.velocityModified = true;

        context.getSource().sendFeedback(() -> Text.literal("Hello!"), false);
        return 1;
    }

}

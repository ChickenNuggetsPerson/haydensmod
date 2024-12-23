package com.haydensmod.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class RegisterCommands {
    public static void register() {

        // Register Test Command
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			TestCommand.register(dispatcher, environment.dedicated);
        });


    }
}

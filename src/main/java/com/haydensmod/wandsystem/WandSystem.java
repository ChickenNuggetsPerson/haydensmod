package com.haydensmod.wandsystem;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.entity.decoration.DisplayEntity.BlockDisplayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.World.ExplosionSourceType;

public class WandSystem {
    public static void register() {


        UseItemCallback.EVENT.register((player, world, hand) -> {

            if (
                player.getStackInHand(hand).getItem() == Items.STICK
                && player.isGliding()
            ) {
                
                WandSystem.onUse(player, world, hand);

                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });


        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {

            if (player.getStackInHand(hand).getItem() == Items.STICK) {

                WandSystem.attackBlock(player, world, hand, pos, direction);

                return ActionResult.SUCCESS;
            }
            
            return ActionResult.PASS;
        });

    }

    public static void onUse(PlayerEntity player, World world, Hand hand) {
        
        double speed = 100.0;
        Vec3d newVel = Vec3d.fromPolar(player.getPitch(), player.getYaw());
        newVel.multiply(speed);

        player.addVelocity(newVel);
        player.velocityModified = true;
    }

    public static void attackBlock(PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) {
        int radius = 10;
        
        for (int x = pos.getX() - radius; x <= pos.getX() + radius; x++) {
            for (int y = pos.getY() - radius; y <= pos.getY() + radius; y++) {
                for (int z = pos.getZ() - radius; z <= pos.getZ() + radius; z++) {

                    // if (
                        // pos.isWithinDistance(new BlockPos(x, y, z), 10)
                    // ) {
                        WandSystem.BtE(world, new BlockPos(x, y, z));
                    // }

                }
            }
        }
    }

    public static void BtE(World world, BlockPos pos) {
        
        if (world.getBlockState(pos).isAir()) { return; }
        if (!world.getBlockState(pos.down()).isAir()) { return; }
        
        FallingBlockEntity entity = FallingBlockEntity.spawnFromBlock(world, pos, world.getBlockState(pos));

        entity.dropItem = false;
        
        //entity.setVelocity(new Vec3d(Math.random() * 10, Math.random() * 10, Math.random() * 10));
        //entity.velocityModified = true;

        world.breakBlock(pos, false);
        world.spawnEntity(entity);
    }
}



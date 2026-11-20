package com.electrifiedded.orespawn.network;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class RiderControlMessage implements IMessage {
    public int keyState;
    public float moveForward;
    public float moveStrafing;
    public boolean dismount;

    public RiderControlMessage() {
    }

    public RiderControlMessage(int keyState, float moveForward, float moveStrafing, boolean dismount) {
        this.keyState = keyState;
        this.moveForward = moveForward;
        this.moveStrafing = moveStrafing;
        this.dismount = dismount;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.keyState = buf.readUnsignedByte();
        this.moveForward = buf.readFloat();
        this.moveStrafing = buf.readFloat();
        this.dismount = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(this.keyState);
        buf.writeFloat(this.moveForward);
        buf.writeFloat(this.moveStrafing);
        buf.writeBoolean(this.dismount);
    }
}

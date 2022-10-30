package tech.johnoneill.trekfiles.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class MessHallTable extends Block {

    public MessHallTable(Properties properties) {
        super(properties);
    }

    public VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.9375, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.46875, 0.3125, 0.46875, 0.53125, 0.9375, 0.53125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.234375, 0.1875, 0.46875, 0, 0.25, 0.53125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.46875, 0, -0.53125, 0.53125, 0.5, -0.46875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(1.25, 0.5, 0.25, 1.75, 0.5625, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.75, 0.5, 0.25, -0.25, 0.5625, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.5, 1.25, 0.75, 0.5625, 1.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.5, -0.75, 0.75, 0.5625, -0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.53125, 0, 0.46875, -0.46875, 0.5, 0.53125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.46875, 0, 1.46875, 0.53125, 0.5, 1.53125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(1.46875, 0, 0.46875, 1.53125, 0.5, 0.53125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.46875, 0.125, 0.46875, -0.234375, 0.1875, 0.53125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.25, 0.46875, 0.234375, 0.3125, 0.53125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.234375, 0.3125, 0.46875, 0.46875, 0.375, 0.53125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.46875, 0.125, 1.234375, 0.53125, 0.1875, 1.46875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(1.234375, 0.125, 0.46875, 1.46875, 0.1875, 0.53125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.46875, 0.125, -0.46875, 0.53125, 0.1875, -0.234375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.46875, 0.1875, 1, 0.53125, 0.25, 1.234375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(1, 0.1875, 0.46875, 1.234375, 0.25, 0.53125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.46875, 0.1875, -0.234375, 0.53125, 0.25, 0), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.46875, 0.25, 0.765625, 0.53125, 0.3125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.765625, 0.25, 0.46875, 1, 0.3125, 0.53125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.46875, 0.25, 0, 0.53125, 0.3125, 0.234375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.46875, 0.3125, 0.53125, 0.53125, 0.375, 0.765625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.53125, 0.3125, 0.46875, 0.765625, 0.375, 0.53125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.46875, 0.3125, 0.234375, 0.53125, 0.375, 0.46875), BooleanOp.OR);

        return shape;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        return makeShape();
    }

}

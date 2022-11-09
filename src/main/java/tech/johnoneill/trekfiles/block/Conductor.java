package tech.johnoneill.trekfiles.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Conductor extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    private static final VoxelShape SHAPE_VERTICAL = makeVerShape();
    private static final VoxelShape SHAPE_HORIZONTAL_NS = makeHorNSShape();
    private static final VoxelShape SHAPE_HORIZONTAL_EW = makeHorEWShape();

    public static VoxelShape makeVerShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.4375, 0.75, 0.1875, 0.5625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.6875, 0.5625, 0.1875, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.25, 0.5625, 0.1875, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0, 0.3125, 0.6875, 0.1875, 0.4375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0, 0.5625, 0.6875, 0.1875, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.1875, 0.0625, 0.75, 0.8125, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.1875, 0.25, 0.9375, 0.8125, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.1875, 0.375, 1, 0.8125, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.1875, 0.125, 0.875, 0.8125, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.1875, 0, 0.625, 0.8125, 0.0625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.1875, 0.625, 0.9375, 0.8125, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.1875, 0.75, 0.875, 0.8125, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.1875, 0.875, 0.75, 0.8125, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.1875, 0.9375, 0.625, 0.8125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.8125, 0.25, 0.5625, 1, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.8125, 0.3125, 0.6875, 1, 0.4375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.8125, 0.4375, 0.75, 1, 0.5625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.8125, 0.5625, 0.6875, 1, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.8125, 0.6875, 0.5625, 1, 0.75), BooleanOp.OR);
        return shape;
    }

    public static VoxelShape makeHorNSShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.25, 0.4375, 0.8125, 0.75, 0.5625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.6875, 0.8125, 0.5625, 0.75, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.25, 0.8125, 0.5625, 0.3125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.3125, 0.8125, 0.6875, 0.4375, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.5625, 0.8125, 0.6875, 0.6875, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.0625, 0.1875, 0.75, 0.125, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.25, 0.1875, 0.9375, 0.375, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.375, 0.1875, 1, 0.625, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.125, 0.1875, 0.875, 0.25, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0, 0.1875, 0.625, 0.0625, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.625, 0.1875, 0.9375, 0.75, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.75, 0.1875, 0.875, 0.875, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.875, 0.1875, 0.75, 0.9375, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.9375, 0.1875, 0.625, 1, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.25, 0, 0.5625, 0.3125, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.3125, 0, 0.6875, 0.4375, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.4375, 0, 0.75, 0.5625, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.5625, 0, 0.6875, 0.6875, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.6875, 0, 0.5625, 0.75, 0.1875), BooleanOp.OR);
        return shape;
    }

    public static VoxelShape makeHorEWShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.25, 0.1875, 0.5625, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.6875, 0.4375, 0.1875, 0.75, 0.5625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.25, 0.4375, 0.1875, 0.3125, 0.5625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.3125, 0.3125, 0.1875, 0.4375, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.5625, 0.3125, 0.1875, 0.6875, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.0625, 0.25, 0.8125, 0.125, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.25, 0.0625, 0.8125, 0.375, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.375, 0, 0.8125, 0.625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.125, 0.125, 0.8125, 0.25, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0.375, 0.8125, 0.0625, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.625, 0.0625, 0.8125, 0.75, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.75, 0.125, 0.8125, 0.875, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.875, 0.25, 0.8125, 0.9375, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.9375, 0.375, 0.8125, 1, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.25, 0.4375, 1, 0.3125, 0.5625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.3125, 0.3125, 1, 0.4375, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.4375, 0.25, 1, 0.5625, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.5625, 0.3125, 1, 0.6875, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.6875, 0.4375, 1, 0.75, 0.5625), BooleanOp.OR);

        return shape;
    }

    public Conductor(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y));
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        if (state.getValue(AXIS) == Direction.Axis.X) {
            return SHAPE_HORIZONTAL_EW;
        } else if (state.getValue(AXIS) == Direction.Axis.Z) {
            return SHAPE_HORIZONTAL_NS;
        }
        return SHAPE_VERTICAL;
    }


    public BlockState rotate(BlockState state, Rotation rotation) {
        return rotatePillar(state, rotation);
    }

    public static BlockState rotatePillar(BlockState state, Rotation rotation) {
        return switch (rotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.getValue(AXIS)) {
                case X -> state.setValue(AXIS, Direction.Axis.Z);
                case Z -> state.setValue(AXIS, Direction.Axis.X);
                default -> state;
            };
            default -> state;
        };
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis());
    }

}

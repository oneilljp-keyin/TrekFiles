package tech.johnoneill.trekfiles.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import tech.johnoneill.trekfiles.TrekFiles;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class BunkDoubleDefiant extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
    protected static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    private static final Optional<VoxelShape> SHAPE_LOWER = Stream.of(
            Block.box(-16, -16, 0, 24, -8, 16),
            Block.box(-15.75, 7.75, 0.25, 23.75, 9.75, 15.75),
            Block.box(-15.75, -8, 0.25, 23.75, -7, 15.75),
            Block.box(19, -6, 0, 24, 6, 0.25),
            Block.box(-16, -6, 0, -11, 6, 0.25),
            Block.box(-16, 6, 0, 24, 8, 0.25),
            Block.box(-16, -8, 0, 24, -6, 0.25),
            Block.box(23.75, -8, 0.25, 24, 8, 15.75),
            Block.box(-16, 8, 0.25, -15.75, 24, 15.75),
            Block.box(-16, 10, 0, -11, 22, 0.25),
            Block.box(-16, 8, 0, 24, 10, 0.25),
            Block.box(19, 10, 0, 24, 22, 0.25),
            Block.box(-16, 22, 0, 24, 24, 0.25),
            Block.box(-16, 8, 15.75, -8, 24, 16),
            Block.box(-8, 8, 15.75, 8, 24, 16),
            Block.box(8, 8, 15.75, 24, 24, 16),
            Block.box(-16, -8, 15.75, -8, 8, 16),
            Block.box(-8, -8, 15.75, 8, 8, 16),
            Block.box(8, -8, 15.75, 24, 8, 16),
            Block.box(23.75, 8, 0.25, 24, 24, 15.75),
            Block.box(-16, -8, 0.25, -15.75, 8, 15.75)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));
    private static final Optional<VoxelShape> SHAPE_UPPER = Stream.of(
            Block.box(-8, -16, 0, 32, -8, 16),
            Block.box(-7.75, 7.75, 0.25, 31.75, 9.75, 15.75),
            Block.box(-7.75, -8, 0.25, 31.75, -7, 15.75),
            Block.box(27, -6, 0, 32, 6, 0.25),
            Block.box(-8, -6, 0, -3, 6, 0.25),
            Block.box(-8, 6, 0, 32, 8, 0.25),
            Block.box(-8, -8, 0, 32, -6, 0.25),
            Block.box(31.75, -8, 0.25, 32, 8, 15.75),
            Block.box(-8, 8, 0.25, -7.75, 24, 15.75),
            Block.box(-8, 10, 0, -3, 22, 0.25),
            Block.box(-8, 8, 0, 32, 10, 0.25),
            Block.box(27, 10, 0, 32, 22, 0.25),
            Block.box(-8, 22, 0, 32, 24, 0.25),
            Block.box(-8, 8, 15.75, 0, 24, 16),
            Block.box(0, 8, 15.75, 16, 24, 16),
            Block.box(16, 8, 15.75, 32, 24, 16),
            Block.box(-8, -8, 15.75, 0, 8, 16),
            Block.box(0, -8, 15.75, 16, 8, 16),
            Block.box(16, -8, 15.75, 32, 8, 16),
            Block.box(31.75, 8, 0.25, 32, 24, 15.75),
            Block.box(-8, -8, 0.25, -7.75, 8, 15.75)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));

    public BunkDoubleDefiant(Properties properties) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(HINGE, DoorHingeSide.LEFT));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
            runCalculation(SHAPE_UPPER.orElse(Shapes.block()));
        } else {
            runCalculation(SHAPE_LOWER.orElse(Shapes.block()));
        }
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(context)) {
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(HINGE, this.getHinge(context));
        } else {
            return null;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, HINGE);
    }

    private DoorHingeSide getHinge(BlockPlaceContext context) {
        BlockGetter blockgetter = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction direction = context.getHorizontalDirection();
        BlockPos blockpos1 = blockpos.above();
        Direction direction1 = direction.getCounterClockWise();
        BlockPos blockpos2 = blockpos.relative(direction1);
        BlockState blockstate = blockgetter.getBlockState(blockpos2);
        BlockPos blockpos3 = blockpos1.relative(direction1);
        BlockState blockstate1 = blockgetter.getBlockState(blockpos3);
        Direction direction2 = direction.getClockWise();
        BlockPos blockpos4 = blockpos.relative(direction2);
        BlockState blockstate2 = blockgetter.getBlockState(blockpos4);
        BlockPos blockpos5 = blockpos1.relative(direction2);
        BlockState blockstate3 = blockgetter.getBlockState(blockpos5);
        int i = (blockstate.isCollisionShapeFullBlock(blockgetter, blockpos2) ? -1 : 0) + (blockstate1.isCollisionShapeFullBlock(blockgetter, blockpos3) ? -1 : 0) + (blockstate2.isCollisionShapeFullBlock(blockgetter, blockpos4) ? 1 : 0) + (blockstate3.isCollisionShapeFullBlock(blockgetter, blockpos5) ? 1 : 0);
        boolean flag = blockstate.is(this);
        boolean flag1 = blockstate2.is(this);
        if ((!flag || flag1) && i <= 0) {
            if ((!flag1 || flag) && i >= 0) {
                int j = direction.getStepX();
                int k = direction.getStepZ();
                Vec3 vec3 = context.getClickLocation();
                double d0 = vec3.x - (double)blockpos.getX();
                double d1 = vec3.z - (double)blockpos.getZ();
                return (j >= 0 || !(d1 < 0.5D)) && (j <= 0 || !(d1 > 0.5D)) && (k >= 0 || !(d0 > 0.5D)) && (k <= 0 || !(d0 < 0.5D)) ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
            } else {
                return DoorHingeSide.LEFT;
            }
        } else {
            return DoorHingeSide.RIGHT;
        }
    }

    protected void runCalculation(VoxelShape shape) {
        for (Direction direction : Direction.values()) {
            SHAPES.put(direction, TrekFiles.calculateShapes(direction, shape));
        }
    }
}

package tech.johnoneill.trekfiles.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
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

public class FoldedSeat extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    protected static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    private static final Optional<VoxelShape> SHAPE_LEFT = Stream.of(
            Block.box(0, 8, 0, 1, 9, 8),
            Block.box(0, 8, 8, 7, 9, 9),
            Block.box(6, 8, 0, 7, 9, 8),
            Block.box(0, 9, 1, 7, 10, 9),
            Block.box(5, 0, 7, 6, 9, 8),
            Block.box(1, 0, 7, 2, 9, 8)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));
    private static final Optional<VoxelShape> SHAPE_LEFT_FOLDED = Stream.of(
            Block.box(0, 0, 1, 7, 8, 2),
            Block.box(6, 1, 0, 7, 9, 1),
            Block.box(0, 1, 0, 1, 9, 1),
            Block.box(0, 0, 0, 7, 1, 1)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));

    private static final Optional<VoxelShape> SHAPE_RIGHT = Stream.of(
            Block.box(9, 8, 0, 10, 9, 8),
            Block.box(9, 8, 8, 16, 9, 9),
            Block.box(15, 8, 0, 16, 9, 8),
            Block.box(9, 9, 1, 16, 10, 9),
            Block.box(14, 0, 7, 15, 9, 8),
            Block.box(10, 0, 7, 11, 9, 8)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));
    private static final Optional<VoxelShape> SHAPE_RIGHT_FOLDED = Stream.of(
            Block.box(9, 0, 1, 16, 8, 2),
            Block.box(15, 1, 0, 16, 9, 1),
            Block.box(9, 1, 0, 10, 9, 1),
            Block.box(9, 0, 0, 16, 1, 1)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));

    public FoldedSeat(Properties properties) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(HINGE, DoorHingeSide.LEFT).setValue(OPEN, Boolean.FALSE));
    }

    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        DoorHingeSide flag_side = state.getValue(HINGE);
        boolean flag_closed = !state.getValue(OPEN);
        if (flag_closed) {
            if (flag_side == DoorHingeSide.LEFT) {
                runCalculation(SHAPE_LEFT_FOLDED.orElse(Shapes.block()));
            } else {
                runCalculation(SHAPE_RIGHT_FOLDED.orElse(Shapes.block()));
            }
        } else {
            if (flag_side == DoorHingeSide.LEFT) {
                runCalculation(SHAPE_LEFT.orElse(Shapes.block()));
            } else {
                runCalculation(SHAPE_RIGHT.orElse(Shapes.block()));
            }
        }

        return SHAPES.get(state.getValue(FACING));
    }

    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        BlockPos blockpos = placeContext.getClickedPos();
        Level level = placeContext.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(placeContext)) {
            return this.defaultBlockState().setValue(FACING, placeContext.getHorizontalDirection()).setValue(HINGE, this.getHinge(placeContext)).setValue(OPEN, Boolean.FALSE);
        } else {
            return null;
        }
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
            if ((!flag1 || flag) && i == 0) {
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

    public @NotNull InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
        if (this.material == Material.METAL) {
            return InteractionResult.PASS;
        } else {
            blockState = blockState.cycle(OPEN);
            level.setBlock(pos, blockState, 10);
            level.gameEvent(player, this.isOpen(blockState) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    public boolean isOpen(BlockState state) {
        return state.getValue(OPEN);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return super.getLightEmission(state, level, pos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, HINGE, OPEN);
    }

    protected void runCalculation(VoxelShape shape) {
        for (Direction direction : Direction.values()) {
            SHAPES.put(direction, TrekFiles.calculateShapes(direction, shape));
        }
    }
}

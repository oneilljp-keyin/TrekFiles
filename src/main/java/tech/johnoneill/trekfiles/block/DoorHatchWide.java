package tech.johnoneill.trekfiles.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.stream.Stream;

public class DoorHatchWide extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    protected static final VoxelShape NORTH_CLOSED = Block.box(0.0D, 0.0D, 11.0D, 16.0D, 16.0D, 13.0D);
    protected static final VoxelShape NORTH_OPEN = Block.box(0.0D, 0.0D, 11.0D, 2.0D, 16.0D, 13.0D);
    protected static final VoxelShape NORTH_OPEN_RIGHT = Block.box(14.0D, 0.0D, 11.0D, 16.0D, 16.0D, 13.0D);
    protected static final VoxelShape SOUTH_CLOSED = Block.box(0.0D, 0.0D, 3.0D, 16.0D, 16.0D, 5.0D);
    protected static final VoxelShape SOUTH_OPEN = Block.box(14.0D, 0.0D, 3.0D, 16.0D, 16.0D, 5.0D);
    protected static final VoxelShape SOUTH_OPEN_RIGHT = Block.box(0.0D, 0.0D, 3.0D, 2.0D, 16.0D, 5.0D);
    protected static final VoxelShape EAST_CLOSED = Block.box(3.0D, 0.0D, 0.0D, 5.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST_OPEN = Block.box(3.0D, 0.0D, 0.0D, 5.0D, 16.0D, 2.0D);
    protected static final VoxelShape EAST_OPEN_RIGHT = Block.box(3.0D, 0.0D, 14.0D, 5.0D, 16.0D, 16.0D);
    protected static final VoxelShape WEST_CLOSED = Block.box(11.0D, 0.0D, 0.0D, 13.0D, 16.0D, 16.0D);
    protected static final VoxelShape WEST_OPEN = Block.box(11.0D, 0.0D, 14.0D, 13.0D, 16.0D, 16.0D);
    protected static final VoxelShape WEST_OPEN_RIGHT = Block.box(11.0D, 0.0D, 0.0D, 13.0D, 16.0D, 2.0D);

    public DoorHatchWide(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.FALSE)
                .setValue(HINGE, DoorHingeSide.LEFT).setValue(POWERED, Boolean.FALSE).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    public VoxelShape getShape(BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext context) {
        Direction direction = blockState.getValue(FACING);
        boolean flag = !blockState.getValue(OPEN);
        boolean flag1 = blockState.getValue(HINGE) == DoorHingeSide.LEFT;
        switch (direction) {
            case EAST:
            default:
                return flag ? EAST_CLOSED : (flag1 ? EAST_OPEN : EAST_OPEN_RIGHT);
            case SOUTH:
                return flag ? SOUTH_CLOSED : (flag1 ? SOUTH_OPEN : SOUTH_OPEN_RIGHT);
            case WEST:
                return flag ? WEST_CLOSED : (flag1 ? WEST_OPEN : WEST_OPEN_RIGHT);
            case NORTH:
                return flag ? NORTH_CLOSED : (flag1 ? NORTH_OPEN : NORTH_OPEN_RIGHT);
        }
    }

    public BlockState updateShape(BlockState state, Direction direction, @NotNull BlockState state1, @NotNull LevelAccessor accessor, @NotNull BlockPos pos, @NotNull BlockPos pos1) {
        DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            return state1.is(this) && state1.getValue(HALF) != doubleblockhalf ? state.setValue(FACING, state1.getValue(FACING))
                    .setValue(OPEN, state1.getValue(OPEN)).setValue(HINGE, state1.getValue(HINGE))
                    .setValue(POWERED, state1.getValue(POWERED)) : Blocks.AIR.defaultBlockState();
        } else {
            return doubleblockhalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(accessor, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, state1, accessor, pos, pos1);
        }
    }

    public boolean isPathfindable(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, PathComputationType computationType) {
        return switch (computationType) {
            case LAND, AIR -> blockState.getValue(OPEN);
            default -> false;
        };
    }

    private int getCloseSound() {
        return this.material == Material.METAL ? 1011 : 1012;
    }

    private int getOpenSound() {
        return this.material == Material.METAL ? 1005 : 1006;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(context)) {
            boolean flag = level.hasNeighborSignal(blockpos) || level.hasNeighborSignal(blockpos.above());
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection())
                    .setValue(HINGE, this.getHinge(context)).setValue(POWERED, flag).setValue(OPEN, flag)
                    .setValue(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity entity, @NotNull ItemStack stack) {
        level.setBlock(blockPos.above(), blockState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
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
        boolean flag = blockstate.is(this) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
        boolean flag1 = blockstate2.is(this) && blockstate2.getValue(HALF) == DoubleBlockHalf.LOWER;
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

    public InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
        if (this.material == Material.METAL) {
            return InteractionResult.PASS;
        } else {
            blockState = blockState.cycle(OPEN);
            level.setBlock(pos, blockState, 10);
            level.levelEvent(player, blockState.getValue(OPEN) ? this.getOpenSound() : this.getCloseSound(), pos, 0);
            level.gameEvent(player, this.isOpen(blockState) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    public boolean isOpen(BlockState state) {
        return state.getValue(OPEN);
    }

    public void setOpen(@Nullable Entity entity, Level level, BlockState state, BlockPos pos, boolean b) {
        if (state.is(this) && state.getValue(OPEN) != b) {
            level.setBlock(pos, state.setValue(OPEN, b), 10);
            this.playSound(level, pos, b);
            level.gameEvent(entity, b ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        }
    }

    public void neighborChanged(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Block block, @NotNull BlockPos pos1, boolean b) {
        boolean flag = level.hasNeighborSignal(pos) || level.hasNeighborSignal(pos.relative(state.getValue(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN));
        if (!this.defaultBlockState().is(block) && flag != state.getValue(POWERED)) {
            if (flag != state.getValue(OPEN)) {
                this.playSound(level, pos, flag);
                level.gameEvent((Entity)null, flag ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
            }

            level.setBlock(pos, state.setValue(POWERED, flag).setValue(OPEN, flag), 2);
        }

    }

    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = reader.getBlockState(blockpos);
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(reader, blockpos, Direction.UP) : blockstate.is(this);
    }

    private void playSound(Level level, BlockPos pos, boolean b) {
        level.levelEvent((Player)null, b ? this.getOpenSound() : this.getCloseSound(), pos, 0);
    }

    public PushReaction getPistonPushReaction(@NotNull BlockState state) {
        return PushReaction.DESTROY;
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(@NotNull BlockState state, @NotNull Mirror mirror) {
        return mirror == Mirror.NONE ? state : state.rotate(mirror.getRotation(state.getValue(FACING))).cycle(HINGE);
    }

    public long getSeed(BlockState state, BlockPos pos) {
        return Mth.getSeed(pos.getX(), pos.below(state.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, FACING, OPEN, HINGE, POWERED);
    }

    public static boolean isWoodenDoor(Level level, BlockPos pos) {
        return isWoodenDoor(level.getBlockState(pos));
    }

    public static boolean isWoodenDoor(BlockState state) {
        return state.getBlock() instanceof DoorBlock && (state.getMaterial() == Material.WOOD || state.getMaterial() == Material.NETHER_WOOD);
    }
}

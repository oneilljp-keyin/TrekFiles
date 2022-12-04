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
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import tech.johnoneill.trekfiles.TrekFiles;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class HatchSplitVertical extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    protected static final VoxelShape EAST_SHAPE_CLOSED = Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
    protected static final VoxelShape WEST_SHAPE_CLOSED = Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SOUTH_SHAPE_CLOSED = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
    protected static final VoxelShape NORTH_SHAPE_CLOSED = Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);

    protected static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    private static final Optional<VoxelShape> SHAPE_OPEN = Stream.of(
            Block.box(-8, 0, 15, 0, 16, 16),
            Block.box(1, 5.5, 15, 1.25, 10.75, 16),
            Block.box(0.75, 5.25, 15, 1, 11, 16),
            Block.box(0.5, 4.75, 15, 0.75, 11.5, 16),
            Block.box(0.25, 4.5, 15, 0.5, 11.75, 16),
            Block.box(0, 4.25, 15, 0.25, 12, 16),
            Block.box(1.25, 6, 15, 1.5, 10.25, 16),
            Block.box(16, 0, 15, 16.25, 4.25, 16),
            Block.box(16.25, 0, 15, 16.5, 4.5, 16),
            Block.box(16.5, 0, 15, 16.75, 4.75, 16),
            Block.box(16.75, 0, 15, 17, 5.25, 16),
            Block.box(17, 0, 15, 17.25, 5.5, 16),
            Block.box(17.25, 0, 15, 17.5, 6, 16),
            Block.box(17.5, 0, 15, 24, 16, 16),
            Block.box(17.25, 10.25, 15, 17.5, 16, 16),
            Block.box(17, 10.75, 15, 17.25, 16, 16),
            Block.box(16.75, 11, 15, 17, 16, 16),
            Block.box(16.5, 11.5, 15, 16.75, 16, 16),
            Block.box(16.25, 11.75, 15, 16.5, 16, 16),
            Block.box(16, 12, 15, 16.25, 16, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));

    public HatchSplitVertical(Properties properties) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.FALSE).setValue(POWERED, Boolean.FALSE));
    }

    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        if (!state.getValue(OPEN)) {
            return switch (state.getValue(FACING)) {
                default -> NORTH_SHAPE_CLOSED;
                case SOUTH -> SOUTH_SHAPE_CLOSED;
                case WEST -> WEST_SHAPE_CLOSED;
                case EAST -> EAST_SHAPE_CLOSED;
            };
        } else {
            runCalculation(SHAPE_OPEN.orElse(Shapes.block()));
            return SHAPES.get(state.getValue(FACING));
        }
    }

    public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, PathComputationType type) {
        return switch (type) {
            case LAND, AIR -> state.getValue(OPEN);
            default -> false;
        };
    }

    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
        if (this.material == Material.METAL) {
            return InteractionResult.PASS;
        } else {
            state = state.cycle(OPEN);
            level.setBlock(pos, state, 2);
            this.playSound(player, level, pos, state.getValue(OPEN));
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    protected void playSound(@Nullable Player player, Level level, BlockPos pos, boolean b) {
        if (b) {
            int i = this.material == Material.METAL ? 1037 : 1007;
            level.levelEvent(player, i, pos, 0);
        } else {
            int j = this.material == Material.METAL ? 1036 : 1013;
            level.levelEvent(player, j, pos, 0);
        }

        level.gameEvent(player, b ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
    }

    public void neighborChanged(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Block block, @NotNull BlockPos pos1, boolean b) {
        if (!level.isClientSide) {
            boolean flag = level.hasNeighborSignal(pos);
            if (flag != state.getValue(POWERED)) {
                if (state.getValue(OPEN) != flag) {
                    state = state.setValue(OPEN, flag);
                    this.playSound(null, level, pos, flag);
                }

                level.setBlock(pos, state.setValue(POWERED, flag), 2);
            }

        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = this.defaultBlockState();
        Direction direction = context.getClickedFace();
        if (!context.replacingClickedOnBlock() && direction.getAxis().isHorizontal()) {
            blockstate = blockstate.setValue(FACING, direction);
        } else {
            blockstate = blockstate.setValue(FACING, context.getHorizontalDirection().getOpposite());
        }

        if (context.getLevel().hasNeighborSignal(context.getClickedPos())) {
            blockstate = blockstate.setValue(OPEN, Boolean.TRUE).setValue(POWERED, Boolean.TRUE);
        }

        return blockstate;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, OPEN, POWERED);
    }

    protected void runCalculation(VoxelShape shape) {
        for (Direction direction : Direction.values()) {
            SHAPES.put(direction, TrekFiles.calculateShapes(direction, shape));
        }
    }
}

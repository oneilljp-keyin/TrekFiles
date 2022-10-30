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
import tech.johnoneill.trekfiles.TrekFiles;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class HatchSplitHorizontal extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    protected static final VoxelShape BOTTOM_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    protected static final VoxelShape TOP_AABB = Block.box(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    protected static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    private static final Optional<VoxelShape> SHAPE_BOTTOM = Stream.of(
            Block.box(16, 0, 0, 16.25, 1, 4.25),
            Block.box(16.25, 0, 0, 16.5, 1, 4.5),
            Block.box(16.5, 0, 0, 16.75, 1, 4.75),
            Block.box(16.75, 0, 0, 17, 1, 5.25),
            Block.box(17, 0, 0, 17.25, 1, 5.5),
            Block.box(17.25, 0, 0, 17.5, 1, 6),
            Block.box(17.5, 0, 0, 24, 1, 16),
            Block.box(17.25, 0, 10.25, 17.5, 1, 16),
            Block.box(17, 0, 10.5, 17.25, 1, 16),
            Block.box(16.75, 0, 10.5, 17, 1, 16),
            Block.box(16.5, 0, 10.75, 16.75, 1, 16),
            Block.box(16.25, 0, 11.25, 16.5, 1, 16),
            Block.box(16, 0, 11.5, 16.25, 1, 16),
            Block.box(-8, 0, 0, 0, 1, 16),
            Block.box(1, 0, 5.5, 1.25, 1, 10.25),
            Block.box(0.75, 0, 5.25, 1, 1, 10.5),
            Block.box(0.5, 0, 4.75, 0.75, 1, 11),
            Block.box(0.25, 0, 4.5, 0.5, 1, 11.25),
            Block.box(0, 0, 4.25, 0.25, 1, 11.5),
            Block.box(1.25, 0, 6, 1.5, 1, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));

    private static final Optional<VoxelShape> SHAPE_TOP = Stream.of(
            Block.box(-8, 15, -1, 0, 16, 15),
            Block.box(1, 15, 4.5, 1.25, 16, 9.75),
            Block.box(0.75, 15, 4.25, 1, 16, 10),
            Block.box(0.5, 15, 3.75, 0.75, 16, 10.5),
            Block.box(0.25, 15, 3.5, 0.5, 16, 10.75),
            Block.box(0, 15, 3.25, 0.25, 16, 11),
            Block.box(1.25, 15, 5, 1.5, 16, 9.25),
            Block.box(16, 15, -1, 16.25, 16, 3.25),
            Block.box(16.25, 15, -1, 16.5, 16, 3.5),
            Block.box(16.5, 15, -1, 16.75, 16, 3.75),
            Block.box(16.75, 15, -1, 17, 16, 4.25),
            Block.box(17, 15, -1, 17.25, 16, 4.5),
            Block.box(17.25, 15, -1, 17.5, 16, 5),
            Block.box(17.5, 15, -1, 24, 16, 15),
            Block.box(17.25, 15, 9.25, 17.5, 16, 15),
            Block.box(17, 15, 9.75, 17.25, 16, 15),
            Block.box(16.75, 15, 10, 17, 16, 15),
            Block.box(16.5, 15, 10.5, 16.75, 16, 15),
            Block.box(16.25, 15, 10.75, 16.5, 16, 15),
            Block.box(16, 15, 11, 16.25, 16, 15)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));

    public HatchSplitHorizontal(Properties properties) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.FALSE).setValue(HALF, Half.BOTTOM).setValue(POWERED, Boolean.FALSE));
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        if (!state.getValue(OPEN)) {
            return state.getValue(HALF) == Half.TOP ? TOP_AABB : BOTTOM_AABB;
        } else {
            if (state.getValue(HALF) == Half.BOTTOM) {
                runCalculation(SHAPE_BOTTOM.orElse(Shapes.block()));
            } else {
                runCalculation(SHAPE_TOP.orElse(Shapes.block()));
            }
            return SHAPES.get(state.getValue(FACING));
        }
    }

    public boolean isPathfindable(BlockState state, BlockGetter getter, BlockPos pos, PathComputationType type) {
        switch (type) {
            case LAND:
            case AIR:
                return state.getValue(OPEN);
            default:
                return false;
        }
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
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

    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos pos1, boolean b) {
        if (!level.isClientSide) {
            boolean flag = level.hasNeighborSignal(pos);
            if (flag != state.getValue(POWERED)) {
                if (state.getValue(OPEN) != flag) {
                    state = state.setValue(OPEN, flag);
                    this.playSound((Player)null, level, pos, flag);
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
            blockstate = blockstate.setValue(FACING, direction).setValue(HALF, context.getClickLocation().y - (double)context.getClickedPos().getY() > 0.5D ? Half.TOP : Half.BOTTOM);
        } else {
            blockstate = blockstate.setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(HALF, direction == Direction.UP ? Half.BOTTOM : Half.TOP);
        }

        if (context.getLevel().hasNeighborSignal(context.getClickedPos())) {
            blockstate = blockstate.setValue(OPEN, Boolean.TRUE).setValue(POWERED, Boolean.TRUE);
        }

        return blockstate;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, OPEN, HALF, POWERED);
    }

    protected void runCalculation(VoxelShape shape) {
        for (Direction direction : Direction.values()) {
            SHAPES.put(direction, TrekFiles.calculateShapes(direction, shape));
        }
    }
}

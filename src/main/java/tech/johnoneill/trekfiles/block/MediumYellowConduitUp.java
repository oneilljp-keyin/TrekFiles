package tech.johnoneill.trekfiles.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MediumYellowConduitUp extends FenceBlock {
	public static final BooleanProperty NORTH = PipeBlock.NORTH;
	public static final BooleanProperty EAST = PipeBlock.EAST;
	public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
	public static final BooleanProperty WEST = PipeBlock.WEST;
	public static final BooleanProperty UP = PipeBlock.UP;
	public static final BooleanProperty DOWN = PipeBlock.DOWN;

	private static final VoxelShape SHAPE = makeShape();

	public MediumYellowConduitUp(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
		return SHAPE;
	}

	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockGetter blockgetter = context.getLevel();
		BlockPos blockPos = context.getClickedPos();
		BlockPos blockPos1 = blockPos.north();
		BlockPos blockPos2 = blockPos.east();
		BlockPos blockPos3 = blockPos.south();
		BlockPos blockPos4 = blockPos.west();
		BlockPos blockPos5 = blockPos.below();
		BlockPos blockPos6 = blockPos.above();
		BlockState blockState = blockgetter.getBlockState(blockPos1);
		BlockState blockState1 = blockgetter.getBlockState(blockPos2);
		BlockState blockState2 = blockgetter.getBlockState(blockPos3);
		BlockState blockState3 = blockgetter.getBlockState(blockPos4);
		BlockState blockState4 = blockgetter.getBlockState(blockPos5);
		BlockState blockState5 = blockgetter.getBlockState(blockPos6);
		return Objects.requireNonNull(super.getStateForPlacement(context))
				.setValue(NORTH, this.connectsTo(blockState, blockState.isFaceSturdy(blockgetter, blockPos1, Direction.SOUTH), Direction.SOUTH))
				.setValue(EAST, this.connectsTo(blockState1, blockState1.isFaceSturdy(blockgetter, blockPos2, Direction.WEST), Direction.WEST))
				.setValue(SOUTH, this.connectsTo(blockState2, blockState2.isFaceSturdy(blockgetter, blockPos3, Direction.NORTH), Direction.NORTH))
				.setValue(WEST, this.connectsTo(blockState3, blockState3.isFaceSturdy(blockgetter, blockPos4, Direction.EAST), Direction.EAST))
				.setValue(UP, this.connectsTo(blockState4, blockState4.isFaceSturdy(blockgetter, blockPos5, Direction.UP), Direction.UP))
				.setValue(DOWN, this.connectsTo(blockState5, blockState5.isFaceSturdy(blockgetter, blockPos6, Direction.DOWN), Direction.DOWN))
				;
	}

//	private boolean connectsTo(BlockState state, boolean bool, Direction direction) {
//		boolean flag = true;
//		boolean flag1 = true;
//		isExceptionForConnection(state);
//		return flag || flag1;
//	}

	public static VoxelShape makeShape() {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0.1875, 0.8125, 1, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.125, 0.75, 1, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.25, 0.875, 1, 0.75), BooleanOp.OR);

		return shape;
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(NORTH, EAST, WEST, SOUTH, UP, DOWN);
	}

}

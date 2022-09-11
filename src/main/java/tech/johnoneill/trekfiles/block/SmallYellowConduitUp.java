package tech.johnoneill.trekfiles.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class SmallYellowConduitUp extends Block {
	public static final VoxelShape DEFAULTSHAPE = Shapes.box(0, 0, 0, 1, 1, 1);

	public SmallYellowConduitUp(Properties properties) {
		super(properties);
//		this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.FALSE)
//				.setValue(EAST, Boolean.FALSE).setValue(SOUTH, Boolean.FALSE)
//				.setValue(WEST, Boolean.FALSE)
//				.setValue(UP, Boolean.valueOf(false)).setValue(DOWN, Boolean.valueOf(false))
//		);
	}

	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return DEFAULTSHAPE;
	}

	@Override
	public @NotNull VoxelShape getVisualShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return DEFAULTSHAPE;
	}

//	@Override
//	public boolean connectsTo(@NotNull BlockState state, boolean bool, @NotNull Direction direction) {
//		boolean flag = true;
//		boolean flag1 = true;
//		isExceptionForConnection(state);
//		return true;
//	}

//	private boolean isSameFence(BlockState state) {
//		return true;
//	}
//
//	@Override
//	protected int getAABBIndex(BlockState state) {
//		return 
//	}

//	@Override
//	public BlockState getStateForPlacement(BlockPlaceContext placement) {
//		BlockGetter blockgetter = placement.getLevel();
//		BlockPos blockpos = placement.getClickedPos();
//		BlockPos blockpos1 = blockpos.north();
//		BlockPos blockpos2 = blockpos.east();
//		BlockPos blockpos3 = blockpos.south();
//		BlockPos blockpos4 = blockpos.west();
//		BlockPos blockpos5 = blockpos.below();
//		BlockPos blockpos6 = blockpos.above();
//		BlockState blockstate = blockgetter.getBlockState(blockpos1);
//		BlockState blockstate1 = blockgetter.getBlockState(blockpos2);
//		BlockState blockstate2 = blockgetter.getBlockState(blockpos3);
//		BlockState blockstate3 = blockgetter.getBlockState(blockpos4);
//		BlockState blockstate4 = blockgetter.getBlockState(blockpos5);
//		BlockState blockstate5 = blockgetter.getBlockState(blockpos6);
//		return Objects.requireNonNull(super.getStateForPlacement(placement))
//				.setValue(NORTH,
//						this.connectsTo(blockstate,
//								blockstate.isFaceSturdy(blockgetter, blockpos1, Direction.SOUTH), Direction.SOUTH))
//				.setValue(EAST,
//						this.connectsTo(blockstate1,
//								blockstate1.isFaceSturdy(blockgetter, blockpos2, Direction.WEST), Direction.WEST))
//				.setValue(SOUTH,
//						this.connectsTo(blockstate2,
//								blockstate2.isFaceSturdy(blockgetter, blockpos3, Direction.NORTH), Direction.NORTH))
//				.setValue(WEST,
//						this.connectsTo(blockstate3,
//								blockstate3.isFaceSturdy(blockgetter, blockpos4, Direction.EAST), Direction.EAST))
////				.setValue(DOWN,
////						Boolean.valueOf(this.connectsTo(blockstate4,
////								blockstate4.isFaceSturdy(blockgetter, blockpos5, Direction.DOWN), Direction.DOWN)))
////				.setValue(UP,
////						Boolean.valueOf(this.connectsTo(blockstate5,
////								blockstate5.isFaceSturdy(blockgetter, blockpos6, Direction.UP), Direction.UP)))
//				;
//	}

//	@Override
//	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> state) {
//		state.add(NORTH, SOUTH, EAST, WEST, WATERLOGGED);
//	}
}

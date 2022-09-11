package tech.johnoneill.trekfiles.init;

import java.util.function.Function;

import com.google.common.base.Supplier;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
//import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tech.johnoneill.trekfiles.TrekFiles;
//import tech.johnoneill.trekfiles.block.ExampleBlock;
import tech.johnoneill.trekfiles.block.*;
//import tech.johnoneill.trekfiles.block.RotatableBlock;

public class BlockInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			TrekFiles.MOD_ID);

	public static final DeferredRegister<Item> ITEMS = ItemInit.ITEMS;

//	public static final RegistryObject<Block> EXAMPLE_BLOCK = register("example_block",
//			() -> new ExampleBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_PURPLE)
//					.strength(3.0f).sound(SoundType.METAL).requiresCorrectToolForDrops()
//					.emissiveRendering((state, getter, pos) -> {
//						return pos.getX() <= -197;
//					}).lightLevel(state -> 4)),
//			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
//
//	public static final RegistryObject<Block> ROTATABLE_BLOCK = register("rotatable_block",
//			() -> new RotatableBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).dynamicShape().sound(SoundType.STONE)),
//			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	public static final RegistryObject<Block> LARGE_YELLOW_CONDUIT_UP = register("large_yellow_conduit_up",
			() -> new LargeYellowConduitUp(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	public static final RegistryObject<Block> LARGE_YELLOW_CONDUIT_DOWN = register("large_yellow_conduit_down",
			() -> new LargeYellowConduitDown(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	public static final RegistryObject<FenceBlock> MEDIUM_YELLOW_CONDUIT_UP = register("medium_yellow_conduit_up",
			() -> new FenceBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	public static final RegistryObject<Block> MEDIUM_YELLOW_CONDUIT_DOWN = register("medium_yellow_conduit_down",
			() -> new MediumYellowConduitDown(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	public static final RegistryObject<Block> LARGE_BLUE_CONDUIT_UP = register("large_blue_conduit_up",
			() -> new LargeBlueConduitUp(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	public static final RegistryObject<Block> LARGE_BLUE_CONDUIT_DOWN = register("large_blue_conduit_down",
			() -> new LargeBlueConduitDown(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	public static final RegistryObject<Block> LARGE_RED_CONDUIT_UP = register("large_red_conduit_up",
			() -> new LargeRedConduitUp(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	public static final RegistryObject<Block> LARGE_RED_CONDUIT_DOWN = register("large_red_conduit_down",
			() -> new LargeRedConduitDown(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	private static <T extends Block> RegistryObject<T> registerBlock(final String name,
			final Supplier<? extends T> block) {
		return BLOCKS.register(name, block);
	}

	private static <T extends Block> RegistryObject<T> register(final String name, final Supplier<? extends T> block,
			Function<RegistryObject<T>, Supplier<? extends Item>> item) {
		RegistryObject<T> obj = registerBlock(name, block);
		ITEMS.register(name, item.apply(obj));
		return obj;
	}
}

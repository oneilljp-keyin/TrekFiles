package tech.johnoneill.trekfiles.init;

import com.google.common.base.Supplier;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tech.johnoneill.trekfiles.TrekFiles;
import tech.johnoneill.trekfiles.item.ExampleItem;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TrekFiles.MOD_ID);

	public static final RegistryObject<Item> PADD = register("padd",
			() -> new Item(new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item) {
		return ITEMS.register(name, item);
	}

}

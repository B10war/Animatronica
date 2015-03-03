package animatronica.utils.item.food;

import java.util.List;

import com.google.common.base.Preconditions;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFoodMetadataBase extends ItemFoodBase{

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	private final String[] items; 

	public ItemFoodMetadataBase(String unlocalizedName, String modId, int healAmount, float saturationModifier, boolean isWolfsFavoriteMeat, String... names){
		super(unlocalizedName, modId, healAmount, saturationModifier, isWolfsFavoriteMeat);
		items = Preconditions.checkNotNull(names, "itemNames cannot be null!");
		setHasSubtypes(true); 
	}

	public String getItemStackDisplayName(ItemStack iStack){
		return StatCollector.translateToLocal(getModId() + ".item." + items[iStack.getItemDamage()]);
	}

	public String[] getItemsNames(){
		return items;
	}

	public ItemStack getItemStack(String item){
		return getItemStack(item, 1);
	}

	public String getUnlocalizedName(ItemStack iStack){
		int metadata = iStack.getItemDamage();
		return metadata < items.length && metadata >= 0 ? "item." + items[metadata] : "BROKEN METADATA";
	}

	public ItemStack getItemStack(String item, int count){
		for(int i = 0; i < items.length; i++){
			if(item.equalsIgnoreCase(items[i])){
				return new ItemStack(this, count, i);
			}
		}
		return null;
	}

	public IIcon getIconFromDamage(int metadata){
		return metadata < icons.length && metadata >= 0 ? icons[metadata] : itemIcon;
	}

	public void registerIcons(IIconRegister reg){
		icons = new IIcon[items.length];
		for(int i = 0; i < icons.length; i++){
			icons[i] = reg.registerIcon(getModId() + ":" + items[i]);
		}
		itemIcon = icons[0];
	}

	public void getSubItems(Item tabItem, CreativeTabs tab, List list){
		for(String item : items){
			list.add(getItemStack(item));
		}
	}
}

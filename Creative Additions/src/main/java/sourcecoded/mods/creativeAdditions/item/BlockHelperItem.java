package sourcecoded.mods.creativeAdditions.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import sourcecoded.mods.creativeAdditions.common.Tabs;
import sourcecoded.mods.creativeAdditions.shared.Methods;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHelperItem extends Item {

	private static final String[] itemTypes = new String[] {"setBlock", "fallingSand"};
	private static final String[] itemNamesTex = new String[] {"setBlock", "fallingSand"};
		
	@SideOnly(Side.CLIENT)
	private IIcon[] iconArray;
	
	public BlockHelperItem() {
		setMaxStackSize(1);
		setUnlocalizedName("blockhelper");
		setTextureName("creativeadditions:blockhelper");
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(Tabs.creativeAdditions);
	}
	
	int spamTimer = 0;
	
	int mode = 0;
	
	EntityPlayer player;
	World world;
	
	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean par5) {
		spamTimer--;
	}
	
	/*@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World var2, EntityPlayer var3) {
		if (!Keyboard.isKeyDown(42) && spamTimer <=0) {	
			this.player = Methods.playerEntity;
			this.world = Methods.playerWorld;
			
			spamTimer = 10;
		}
		return itemstack;
	}*/	
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float xo, float yo, float zo) {
		if (spamTimer <= 0) {
			this.world = world;
			this.player = player;
			
			if (Keyboard.isKeyDown(42)) {				
				itemstack.stackTagCompound = new NBTTagCompound();
				set(x, y, z, itemstack);
				NBTTagCompound tags = itemstack.stackTagCompound;
				
				NBTTagCompound newTags = new NBTTagCompound();
				newTags = (NBTTagCompound) tags.copy();
				
				newTags.removeTag("id");
				newTags.removeTag("x");
				newTags.removeTag("y");
				newTags.removeTag("z");	
				newTags.removeTag("meta");
				
				String commandString = null;
				
				NBTTagCompound fallingTags = new NBTTagCompound();
				
				if (itemstack.getItemDamage() == 0) {
					commandString = "/setblock " + tags.getInteger("x") + " " + tags.getInteger("y") + " " + tags.getInteger("z") + " " + tags.getString("id") + " " + tags.getInteger("meta") + " replace " + newTags.toString();					
				} else if (itemstack.getItemDamage() == 1) {
					
					fallingTags.setInteger("TileID", Block.getIdFromBlock(Block.getBlockFromName(tags.getString("id"))));
					fallingTags.setInteger("Data", tags.getInteger("meta"));
					fallingTags.setInteger("Time", 1);
					fallingTags.setTag("TileEntityData", newTags);
					
					commandString = "/summon FallingSand " + tags.getInteger("x") + " " + (tags.getInteger("y") + 0.5) + " " + tags.getInteger("z") + " " + fallingTags.toString();
				}

				Methods.addChatMessage(player, "Location Bound. Command copied to clipboard");
				
				Methods.setClip(commandString);	
			}
			
			spamTimer = 10;
		}
		return false;
	}
		
	
	private void set(int x, int y, int z, ItemStack item) {
		if (world.getTileEntity(x, y, z) != null) {	
			TileEntity tile = world.getTileEntity(x, y, z);		
			tile.writeToNBT(item.stackTagCompound);
		}
		setBlockTags(x, y, z, item);
	}
	
	private void setBlockTags(int x, int y, int z, ItemStack item) {
		Block block = world.getBlock(x, y, z);
		
		item.stackTagCompound.setString("id", Block.blockRegistry.getNameForObject(block));
		item.stackTagCompound.setInteger("x", x);
		item.stackTagCompound.setInteger("y", y);
		item.stackTagCompound.setInteger("z", z);
		item.stackTagCompound.setInteger("meta", world.getBlockMetadata(x, y, z));
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List par3List, boolean par4) {
        if (itemstack.stackTagCompound != null && itemstack.stackTagCompound.getString("id") != null) {
        	String target = itemstack.stackTagCompound.getString("id");
        	par3List.add("Target: " + target);

        }
    }
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
		for (int i=0; i<itemTypes.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	public String getUnlocalizedName(ItemStack itemstack) {
		
		int dmg = itemstack.getItemDamage();
		
		if (dmg < 0 || dmg >= itemTypes.length) {
			dmg = 0;
		}
		
		return super.getUnlocalizedName() + "." + itemTypes[dmg];
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int dmg) {
		if (dmg < 0 || dmg >= itemTypes.length) {
			dmg = 0;
		}
		
		return this.iconArray[dmg];
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconreg) {
		this.iconArray = new IIcon[itemNamesTex.length];
		
		for (int i=0; i<itemNamesTex.length; i++) {
			this.iconArray[i] = iconreg.registerIcon(this.getIconString() + "_" + itemNamesTex[i]);
		}
	}
	
}

package sourcecoded.creativeA.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import sourcecoded.creativeA.common.Tabs;
import sourcecoded.creativeA.shared.Methods;

import java.util.List;
import java.util.Objects;

public class BlockHelperItem extends Item {

	private static final String[] itemTypes = new String[] {"setBlock", "fallingSand"};
	private static final String[] itemNamesTex = new String[] {"setBlock", "fallingSand"};
		
	@SideOnly(Side.CLIENT)
	private IIcon[] iconArray;
	
	public BlockHelperItem() {
		setMaxStackSize(1);
		setUnlocalizedName("blockhelper");
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
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (spamTimer <= 0) {
			this.world = worldIn;
			this.player = playerIn;
			
			if (Keyboard.isKeyDown(42)) {				
				stack.setTagCompound(new NBTTagCompound());
				set(pos, stack);
				NBTTagCompound tags = stack.getTagCompound();
				
				NBTTagCompound newTags = new NBTTagCompound();
				newTags = (NBTTagCompound) tags.copy();
				
				newTags.removeTag("id");
				newTags.removeTag("x");
				newTags.removeTag("y");
				newTags.removeTag("z");	
				newTags.removeTag("meta");
				
				String commandString = null;
				
				NBTTagCompound fallingTags = new NBTTagCompound();
				
				if (stack.getItemDamage() == 0) {
					commandString = "/setblock " + tags.getInteger("x") + " " + tags.getInteger("y") + " " + tags.getInteger("z") + " " + tags.getString("id") + " " + tags.getInteger("meta") + " replace " + newTags.toString();					
				} else if (stack.getItemDamage() == 1) {
					
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
		
	
	private void set(BlockPos pos, ItemStack item) {
		if (world.getTileEntity(pos) != null) {
			TileEntity tile = world.getTileEntity(pos);
			tile.writeToNBT(item.getTagCompound());
		}
		setBlockTags(pos, item);
	}
	
	private void setBlockTags(BlockPos pos, ItemStack item) {
		IBlockState block = world.getBlockState(pos);
		
		item.getTagCompound().setString("id", Objects.toString(Block.blockRegistry.getNameForObject(block.getBlock())));
		item.getTagCompound().setInteger("x", pos.getX());
		item.getTagCompound().setInteger("y", pos.getY());
		item.getTagCompound().setInteger("z", pos.getZ());
		//item.getTagCompound().setInteger("meta", world.getBlockMetadata(x, y, z));
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List par3List, boolean par4) {
        if (itemstack.getTagCompound() != null && itemstack.getTagCompound().getString("id") != null) {
        	String target = itemstack.getTagCompound().getString("id");
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
			//this.iconArray[i] = iconreg.registerIcon(this.getIconString() + "_" + itemNamesTex[i]);
		}
	}
	
}

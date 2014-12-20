package sourcecoded.creativeA.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import sourcecoded.creativeA.common.Tabs;

import java.util.List;

public class RemoteItem extends Item {
	
	public RemoteItem() {
		setMaxStackSize(1);
		setUnlocalizedName("remote");
		//setTextureName("creativeadditions:remote");
		setCreativeTab(Tabs.creativeAdditions);
	}
	
	int spamTimer = 0;
	
	EntityPlayer player;
	World world;
	
	
	static void trigger(BlockPos pos, EnumFacing side, EntityPlayer player, World world, float xo, float yo, float zo) {
		IBlockState block = world.getBlockState(pos);
		block.getBlock().onBlockActivated(world, pos, block, player, side, xo, yo, zo);
	}
	
	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean par5) {
		if (spamTimer > 0)
			spamTimer--;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World var2, EntityPlayer var3) {
		if (!Keyboard.isKeyDown(42) && spamTimer <=0 && (itemstack.getTagCompound() != null) && !var2.isRemote) {
			this.player = var3;
			this.world = var2;
			
			int x = itemstack.getTagCompound().getInteger("x");
			int y = itemstack.getTagCompound().getInteger("y");
			int z = itemstack.getTagCompound().getInteger("z");
			int side = itemstack.getTagCompound().getInteger("side");
			float xo = itemstack.getTagCompound().getFloat("xo");
			float yo = itemstack.getTagCompound().getFloat("yo");
			float zo = itemstack.getTagCompound().getFloat("zo");

			BlockPos pos = new BlockPos(x, y, z);
			trigger(pos, EnumFacing.values()[side], player, world, xo, yo, zo);
			
			spamTimer = 10;
		}
		return itemstack;
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (Keyboard.isKeyDown(42) && !worldIn.isRemote) {
			itemstack.setTagCompound(new NBTTagCompound());
			itemstack.getTagCompound().setInteger("x", pos.getX());
			itemstack.getTagCompound().setInteger("y", pos.getY());
			itemstack.getTagCompound().setInteger("z", pos.getZ());
			itemstack.getTagCompound().setInteger("side", side.ordinal());
			itemstack.getTagCompound().setFloat("xo", hitX);
			itemstack.getTagCompound().setFloat("yo", hitY);
			itemstack.getTagCompound().setFloat("zo", hitZ);

			return true;
		}
		return false;
	}
	
	@Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addInformation(ItemStack itemstack, EntityPlayer player, List par3List, boolean par4) {
        if (itemstack.getTagCompound() != null) {
        	String target = itemstack.getTagCompound().getInteger("x") + ", " + itemstack.getTagCompound().getInteger("y") + ", " + itemstack.getTagCompound().getInteger("z");
        	par3List.add("Target: " + target);

        }
    }
	
}

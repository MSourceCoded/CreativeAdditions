package creativeAdditions.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import creativeAdditions.common.Tabs;
import creativeAdditions.shared.Methods;

public class RemoteItem extends Item {
	
	public RemoteItem() {
		setMaxStackSize(1);
		setUnlocalizedName("Remote");
		setTextureName("creativeadditions:remote");
		setCreativeTab(Tabs.creativeAdditions);
	}
	
	int spamTimer = 0;
	
	EntityPlayer player;
	World world;
	
	
	static void trigger(int x, int y, int z, int side, EntityPlayer player, World world, float xo, float yo, float zo) {
		Block block = world.getBlock(x, y, z);
		block.onBlockActivated(world, x, y, z, player, side, xo, yo, zo);
	}
	
	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean par5) {
		spamTimer--;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World var2, EntityPlayer var3) {
		if (!Keyboard.isKeyDown(42) && spamTimer <=0 && (itemstack.stackTagCompound != null)) {			
			this.player = Methods.playerEntity;
			this.world = Methods.playerWorld;
			
			int x = itemstack.stackTagCompound.getInteger("x");
			int y = itemstack.stackTagCompound.getInteger("y");
			int z = itemstack.stackTagCompound.getInteger("z");
			int side = itemstack.stackTagCompound.getInteger("side");
			float xo = itemstack.stackTagCompound.getFloat("xo");
			float yo = itemstack.stackTagCompound.getFloat("yo");
			float zo = itemstack.stackTagCompound.getFloat("zo");
			
			trigger(x, y, z, side, player, world, xo, yo, zo);
			
			spamTimer = 10;
		} 
		return itemstack;		
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float xo, float yo, float zo) {
		if (Keyboard.isKeyDown(42)) {
			itemstack.stackTagCompound = new NBTTagCompound();
			itemstack.stackTagCompound.setInteger("x", x);
			itemstack.stackTagCompound.setInteger("y", y);
			itemstack.stackTagCompound.setInteger("z", z);
			itemstack.stackTagCompound.setInteger("side", side);
			itemstack.stackTagCompound.setFloat("xo", xo);
			itemstack.stackTagCompound.setFloat("yo", yo);
			itemstack.stackTagCompound.setFloat("zo", zo);
		}
		return false;
	}
	
	@Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addInformation(ItemStack itemstack, EntityPlayer player, List par3List, boolean par4) {
        if (itemstack.stackTagCompound != null) {
        	String target = itemstack.stackTagCompound.getInteger("x") + ", " + itemstack.stackTagCompound.getInteger("y") + ", " + itemstack.stackTagCompound.getInteger("z");
        	par3List.add("Target: " + target);

        }
    }
	
}

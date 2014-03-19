package sourcecoded.mods.creativeAdditions.shared;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class Summoner {
	
	public static void processSummon(ICommandSender par1, String[] par2, World world) {
		if (par2.length >= 1)
	    {
	        String s = par2[0];
	        double d0 = (double)par1.getPlayerCoordinates().posX + 0.5D;
	        double d1 = (double)par1.getPlayerCoordinates().posY;
	        double d2 = (double)par1.getPlayerCoordinates().posZ + 0.5D;
	
	        if (par2.length >= 4)
	        {
	            d0 = CommandBase.func_110666_a(par1, d0, par2[1]);
	            d1 = CommandBase.func_110666_a(par1, d1, par2[2]);
	            d2 = CommandBase.func_110666_a(par1, d2, par2[3]);
	        }
	        
	            NBTTagCompound nbttagcompound = new NBTTagCompound();
	            boolean flag = false;
	            
	            if (par2.length >= 5)
                {
	            
	            	IChatComponent ichatcomponent = CommandBase.func_147178_a(par1, par2, 4);
	            
	                try
	                {
	                    NBTBase nbtbase = JsonToNBT.func_150315_a(ichatcomponent.getUnformattedText());
	
	                    if (!(nbtbase instanceof NBTTagCompound))
	                    {
	                        return;
	                    }
	
	                    nbttagcompound = (NBTTagCompound)nbtbase;
	                    flag = true;
	                }
	                catch (NBTException nbtexception)
	                {
	                    return;
	                }
	
	            nbttagcompound.setString("id", s);
	            Entity entity1 = EntityList.createEntityFromNBT(nbttagcompound, world);
	
	            if (entity1 != null)
	            {
	                entity1.setLocationAndAngles(d0, d1, d2, entity1.rotationYaw, entity1.rotationPitch);
	
	                if (!flag && entity1 instanceof EntityLiving)
	                {
	                    ((EntityLiving)entity1).onSpawnWithEgg((IEntityLivingData)null);
	                }
	
	                world.spawnEntityInWorld(entity1);
	                Entity entity2 = entity1;
	
	                for (NBTTagCompound nbttagcompound1 = nbttagcompound; nbttagcompound1.hasKey("Riding", 10); nbttagcompound1 = nbttagcompound1.getCompoundTag("Riding"))
	                {
	                    Entity entity = EntityList.createEntityFromNBT(nbttagcompound1.getCompoundTag("Riding"), world);
	
	                    if (entity != null)
	                    {
	                        entity.setLocationAndAngles(d0, d1, d2, entity.rotationYaw, entity.rotationPitch);
	                        world.spawnEntityInWorld(entity);
	                        entity2.mountEntity(entity);
	                    }
	
	                    entity2 = entity;
	                }
	            }
            }
        }
	}
}

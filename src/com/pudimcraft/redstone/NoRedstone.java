package com.pudimcraft.redstone;

import java.util.Iterator;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class NoRedstone extends JavaPlugin implements Listener {
	public final Logger logger = Logger.getLogger("Pudimcraft");
	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info("PUDIMCRAFT URANIUM v" + pdfFile.getVersion() + ", ATIVADO");
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	    blockrecipe();
	    blockrecipe2();
	}
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info("PUDIMCRAFT URANIUM v" + pdfFile.getVersion() + ", DESATIVADO");
	}
	private void blockrecipe2() {
		Iterator<Recipe> recipes1 = Bukkit.recipeIterator();
		while (recipes1.hasNext()) {
			if (recipes1.next().getResult().getType().equals(Material.REDSTONE)) {
				recipes1.remove();
			}
		}
		ItemStack item3 = new ItemStack(Material.REDSTONE);
		ItemMeta im = item3.getItemMeta();
		im.setDisplayName("§aUranium");
		item3.setItemMeta(im);
		ShapelessRecipe hue = new ShapelessRecipe(item3);
		hue.addIngredient(1, Material.REDSTONE_BLOCK);
		Bukkit.getServer().addRecipe(hue);
	}
	
    private void blockrecipe() {
        Iterator<Recipe> recipes = Bukkit.recipeIterator();
        while (recipes.hasNext()) {
            if (recipes.next().getResult().getType().equals(Material.REDSTONE_BLOCK)) {
                recipes.remove();
            }
        }
        ItemStack item2 = new ItemStack(Material.REDSTONE_BLOCK, 1);
        ItemMeta meta2 = item2.getItemMeta();
        meta2.setDisplayName("§aBlock of Uranium");
        item2.setItemMeta(meta2);
       
        ShapelessRecipe precipe = new ShapelessRecipe(item2);
        precipe.addIngredient(9, Material.REDSTONE);
        Bukkit.getServer().addRecipe(precipe);
}
	@EventHandler
	public void onRedstoneBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if(e.getBlock().getType() == Material.REDSTONE_BLOCK) {
			ItemStack item2 = new ItemStack(Material.REDSTONE_BLOCK);
			ItemMeta im2 = item2.getItemMeta();
			im2.setDisplayName("§aBlock of Uranium");
			item2.setItemMeta(im2);
			item2.setAmount(1);
			p.getInventory().addItem(item2);
			e.getBlock().setType(Material.AIR);
		}
		if(e.getBlock().getType() == Material.GLOWING_REDSTONE_ORE || e.getBlock().getType() == Material.REDSTONE_ORE) {

			if(p.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH) || p.getInventory().getItemInOffHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
				ItemStack item = new ItemStack(Material.REDSTONE_ORE);
				ItemMeta im = item.getItemMeta();
				im.setDisplayName("§aUranium Ore");
				item.setItemMeta(im);
				item.setAmount(1);
				p.getInventory().addItem(item);
			} else {
				ItemStack item = new ItemStack(Material.REDSTONE);
				ItemMeta im = item.getItemMeta();
				im.setDisplayName("§aUranium");
				item.setItemMeta(im);
				Random r = new Random();
				int drop = r.nextInt(6);
				item.setAmount(drop);
				p.getInventory().addItem(item);
			}
			e.getBlock().setType(Material.AIR);
			World blockworld = e.getBlock().getWorld();
			int exp = e.getExpToDrop();
			  ((ExperienceOrb)blockworld.spawn(e.getBlock().getLocation(), ExperienceOrb.class)).setExperience(exp);
			e.setCancelled(true);
		}
	}

}
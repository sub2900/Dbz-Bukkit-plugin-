import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DBZTransformation extends JavaPlugin implements Listener {

    private static final String TRANSFORM_MESSAGE = "You have transformed into a Super Saiyan!";
    private static final String REVERT_MESSAGE = "You have reverted to your normal form.";
    private static final String PERMISSION_MESSAGE = "You don't have permission to transform!";
    
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Material itemInHand = event.getItem() != null ? event.getItem().getType() : Material.AIR;
        
        if (itemInHand != Material.DIAMOND || !player.hasPermission("dbz.transform")) {
            player.sendMessage(PERMISSION_MESSAGE);
            return;
        }
        
        player.setGameMode(GameMode.SURVIVAL);
        
        if (isTransformed(player)) {
            revertTransformation(player);
        } else {
            transform(player);
        }
    }
    
    private boolean isTransformed(Player player) {
        return player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE);
    }
    
    private void revertTransformation(Player player) {
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.sendMessage(REVERT_MESSAGE);
    }
    
    private void transform(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
        player.setAllowFlight(true);
        player.setFlying(true);
        player.sendMessage(TRANSFORM_MESSAGE);
    }
}

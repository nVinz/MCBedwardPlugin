package my.nvinz.core.vnizcore.map;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import my.nvinz.core.vnizcore.VnizCore;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Map {
    private VnizCore plugin;
    private File myfile;
    private org.bukkit.World world;
    private Location location = new Location(world, 0, 70, -50);

    public Map(VnizCore pl) throws IOException {
        plugin = pl;
        myfile = new File(plugin.getDataFolder().getAbsolutePath() + "/maps/1.schem");
        world = plugin.variables.arenaWorld;
        System.out.println(myfile);
        System.out.println(world);
    }

    public void map() throws IOException {

        ClipboardFormat format = ClipboardFormats.findByFile(myfile);
        ClipboardReader reader = format.getReader(new FileInputStream(myfile));
        Clipboard clipboard = reader.read();

        try {
            com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(world);
            EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld,-1);
            Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
                    .to(BlockVector3.at(location.getX(), location.getY(), location.getZ())).ignoreAirBlocks(true).build();

            try {
                Operations.complete(operation);
                editSession.flushSession();
            } catch (WorldEditException e) {
                System.out.println("OOPS! Something went wrong, please contact an administrator");
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

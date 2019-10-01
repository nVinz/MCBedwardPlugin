package my.nvinz.core.vnizcore.resources;

import my.nvinz.core.vnizcore.VnizCore;

import java.util.HashMap;
import java.util.Map;

public class ResourceSpawn {

    Map<Resource, ResourceSpawnThread> threads = new HashMap<>();
    VnizCore plugin;
    public ResourceSpawn(VnizCore pl) { plugin = pl; }

    public void setupThreads(){
        plugin.resources.forEach(resource -> {
            ResourceSpawnThread thread = new ResourceSpawnThread(plugin, resource);
            threads.put(resource, thread);
        });
    }

    public void runThreads(){
        threads.forEach( (name, thread) -> {
            thread.start();
        });
    }
}

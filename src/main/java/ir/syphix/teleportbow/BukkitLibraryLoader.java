package ir.syphix.teleportbow;

import com.alessiodp.libby.BukkitLibraryManager;
import com.alessiodp.libby.Library;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

public class BukkitLibraryLoader {

    public BukkitLibraryLoader(TeleportBow teleportBow) {
        BukkitLibraryManager libraryLoader = new BukkitLibraryManager(teleportBow);

        libraryLoader.loadLibrary(
                Library.builder()
                        .repository("https://repo.sayandev.org/snapshots")
                        .groupId("org{}sayandev")
                        .artifactId("stickynote-core")
                        .version("1.0.20")
                        .relocate("org{}sayandev{}stickynote", "ir{}syphix{}teleportbow{}lib{}stickynote")
                        .isolatedLoad(true)
                        .build()
        );
        libraryLoader.loadLibrary(
                Library.builder()
                        .repository("https://repo.sayandev.org/snapshots")
                        .groupId("org{}sayandev")
                        .artifactId("stickynote-bukkit")
                        .version("1.0.20")
                        .relocate("org{}sayandev{}stickynote", "ir{}syphix{}teleportbow{}lib{}stickynote")
                        .isolatedLoad(true)
                        .build()
        );
    }
}

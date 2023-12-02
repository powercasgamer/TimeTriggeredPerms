package dev.mizule.timetriggeredperms.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.mizule.timetriggeredperms.core.TTPPlugin;
import dev.mizule.timetriggeredperms.core.config.Config;
import org.bstats.velocity.Metrics;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import xyz.jpenilla.gremlin.runtime.DependencyCache;
import xyz.jpenilla.gremlin.runtime.DependencyResolver;
import xyz.jpenilla.gremlin.runtime.DependencySet;
import xyz.jpenilla.gremlin.runtime.platformsupport.VelocityClasspathAppender;

import java.nio.file.Path;

@Plugin(
        name = "TimeTriggeredPerms",
        id = "timetriggeredperms",
        version = "@version@",
        authors = "powercas_gamer",
        dependencies = {@Dependency(id = "luckperms")}
)
public class PluginLoader implements TTPPlugin<PluginLoader> {

    final Path path;
    final Logger logger;
    final ProxyServer server;
    final Metrics.Factory metricsFactory;

    public static PluginLoader instance;
    TTP ttp;

    @Inject
    public PluginLoader(@DataDirectory Path dataPath, Logger logger, ProxyServer proxy, Metrics.Factory metricsFactory) {
        this.path = dataPath;
        this.logger = logger;
        this.server = proxy;
        this.metricsFactory = metricsFactory;
        instance = this;
    }

    @Subscribe(order = PostOrder.LATE)
    public void onInit(final ProxyInitializeEvent event) {
        var deps = DependencySet.readDefault(this.getClass().getClassLoader());
        var cache = new DependencyCache(path.resolve("libraries"));
        try (DependencyResolver resolver = new DependencyResolver(logger)) {
            var files = resolver.resolve(deps, cache).jarFiles();
            VelocityClasspathAppender appender = new VelocityClasspathAppender(server, PluginLoader.this);
            appender.append(files);
        }
        cache.cleanup();
        Metrics metrics = metricsFactory.make(this, 20405);

        this.ttp = new TTP(logger, server, path);
        this.ttp.enable();
    }

    @Override
    public @NotNull Config config() {
        return this.ttp.config();
    }

    @Override
    public PluginLoader plugin() {
        return this;
    }

}
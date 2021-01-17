package com.mattworzala.artifact.extension;

import net.minestom.server.extensions.Extension;

public class ArtifactExtension extends Extension {
    @Override
    public void initialize() {
        getLogger().info("Artifact is loading!");
    }

    @Override
    public void terminate() {
        getLogger().info("Artifact is being disabled!");
    }
}

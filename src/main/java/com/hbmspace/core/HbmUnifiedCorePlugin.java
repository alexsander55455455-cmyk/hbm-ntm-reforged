package com.hbmspace.core;

import com.hbm.core.HbmCorePlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.jetbrains.annotations.Nullable;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.TransformerExclusions({"com.hbm.core", "com.hbmspace.core"})
public class HbmUnifiedCorePlugin implements IFMLLoadingPlugin, IEarlyMixinLoader {

    private final HbmCorePlugin hbm = new HbmCorePlugin();
    private final HbmSpaceCorePlugin space = new HbmSpaceCorePlugin();

    @Override
    public String @Nullable [] getASMTransformerClass() {
        return concat(hbm.getASMTransformerClass(), space.getASMTransformerClass());
    }

    @Override
    public @Nullable String getModContainerClass() {
        String hbmContainer = hbm.getModContainerClass();
        return hbmContainer != null ? hbmContainer : space.getModContainerClass();
    }

    @Override
    public @Nullable String getSetupClass() {
        String hbmSetup = hbm.getSetupClass();
        return hbmSetup != null ? hbmSetup : space.getSetupClass();
    }

    @Override
    public void injectData(Map<String, Object> data) {
        hbm.injectData(data);
        space.injectData(data);
    }

    @Override
    public @Nullable String getAccessTransformerClass() {
        String hbmAccessTransformer = hbm.getAccessTransformerClass();
        return hbmAccessTransformer != null ? hbmAccessTransformer : space.getAccessTransformerClass();
    }

    @Override
    public List<String> getMixinConfigs() {
        List<String> configs = new ArrayList<>();
        configs.addAll(hbm.getMixinConfigs());
        configs.addAll(space.getMixinConfigs());
        return configs;
    }

    private static String[] concat(String[] left, String[] right) {
        int leftLength = left == null ? 0 : left.length;
        int rightLength = right == null ? 0 : right.length;
        if(leftLength + rightLength == 0) {
            return null;
        }

        String[] result = new String[leftLength + rightLength];
        if(leftLength > 0) {
            System.arraycopy(left, 0, result, 0, leftLength);
        }
        if(rightLength > 0) {
            System.arraycopy(right, 0, result, leftLength, rightLength);
        }
        return result;
    }
}

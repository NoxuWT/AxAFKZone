package com.artillexstudios.axafkzone.placeholders;

import com.artillexstudios.axafkzone.utils.TimeUtils;
import com.artillexstudios.axafkzone.zones.Zone;
import com.artillexstudios.axafkzone.zones.Zones;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AxAFKZonePlaceholders extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "axzoneafk";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Artillex-Studios";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) return "";

        // %axzoneafk_running% — true si le joueur est dans une zone AFK
        if (params.equals("running")) {
            for (Zone zone : Zones.getZones().values()) {
                if (zone.isPlayerInZone(player)) return "true";
            }
            return "false";
        }

        // %axzoneafk_time% — temps passé dans la zone
        if (params.equals("time")) {
            for (Zone zone : Zones.getZones().values()) {
                int seconds = zone.getPlayerTime(player);
                if (seconds >= 0) return TimeUtils.fancyTime(seconds * 1_000L);
            }
            return "0";
        }

        // %axzoneafk_time_until_next% — temps avant la prochaine récompense
        if (params.equals("time_until_next")) {
            for (Zone zone : Zones.getZones().values()) {
                long ms = zone.timeUntilNext(player);
                if (ms >= 0) return TimeUtils.fancyTime(ms);
            }
            return "0";
        }

        return null;
    }
}

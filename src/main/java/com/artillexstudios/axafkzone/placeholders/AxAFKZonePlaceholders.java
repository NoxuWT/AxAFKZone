package com.artillexstudios.axafkzone.placeholders;

import com.artillexstudios.axafkzone.utils.TimeUtils;
import com.artillexstudios.axafkzone.zones.Zone;
import com.artillexstudios.axafkzone.zones.Zones;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class AxAFKZonePlaceholders extends PlaceholderExpansion {
    private final String identifier;

    public AxAFKZonePlaceholders() {
        this("axafkzone");
    }

    public AxAFKZonePlaceholders(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public @NotNull String getIdentifier() {
        return identifier;
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

        String normalized = params.toLowerCase(Locale.ROOT).replace('-', '_');

        if (normalized.equals("running")) {
            for (Zone zone : Zones.getZones().values()) {
                if (zone.isPlayerInZone(player)) return "true";
            }
            return "false";
        }

        if (normalized.equals("time")) {
            for (Zone zone : Zones.getZones().values()) {
                int seconds = zone.getPlayerTime(player);
                if (seconds >= 0) return TimeUtils.fancyTime(seconds * 1_000L);
            }
            return "0";
        }

        if (normalized.equals("time_until_next") || normalized.equals("timeuntilnext") || normalized.equals("time_until")) {
            for (Zone zone : Zones.getZones().values()) {
                long ms = zone.timeUntilNext(player);
                if (ms >= 0) return TimeUtils.fancyTime(ms);
            }
            return "0";
        }

        return null;
    }
}

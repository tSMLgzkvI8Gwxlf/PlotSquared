/*
 *       _____  _       _    _____                                _
 *      |  __ \| |     | |  / ____|                              | |
 *      | |__) | | ___ | |_| (___   __ _ _   _  __ _ _ __ ___  __| |
 *      |  ___/| |/ _ \| __|\___ \ / _` | | | |/ _` | '__/ _ \/ _` |
 *      | |    | | (_) | |_ ____) | (_| | |_| | (_| | | |  __/ (_| |
 *      |_|    |_|\___/ \__|_____/ \__, |\__,_|\__,_|_|  \___|\__,_|
 *                                    | |
 *                                    |_|
 *            PlotSquared plot management system for Minecraft
 *               Copyright (C) 2014 - 2022 IntellectualSites
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.plotsquared.core.command;

import com.plotsquared.core.configuration.Settings;
import com.plotsquared.core.configuration.caption.TranslatableCaption;
import com.plotsquared.core.player.MetaDataAccess;
import com.plotsquared.core.player.PlayerMetaDataKeys;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.util.task.TaskManager;
import com.plotsquared.core.util.task.TaskTime;
import net.kyori.adventure.text.minimessage.Template;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CmdConfirm {

    public static @Nullable CmdInstance getPending(PlotPlayer<?> player) {
        try (final MetaDataAccess<CmdInstance> metaDataAccess = player.accessTemporaryMetaData(
                PlayerMetaDataKeys.TEMPORARY_CONFIRM)) {
            return metaDataAccess.get().orElse(null);
        }
    }

    public static void removePending(PlotPlayer<?> player) {
        try (final MetaDataAccess<CmdInstance> metaDataAccess = player.accessTemporaryMetaData(
                PlayerMetaDataKeys.TEMPORARY_CONFIRM)) {
            metaDataAccess.remove();
        }
    }

    public static void addPending(
            final PlotPlayer<?> player, String commandStr,
            final Runnable runnable
    ) {
        removePending(player);
        if (commandStr != null) {
            player.sendMessage(
                    TranslatableCaption.of("confirm.requires_confirm"),
                    Template.of("command", commandStr),
                    Template.of("timeout", String.valueOf(Settings.Confirmation.CONFIRMATION_TIMEOUT_SECONDS)),
                    Template.of("value", "/plot confirm")
            );
        }
        TaskManager.runTaskLater(() -> {
            CmdInstance cmd = new CmdInstance(runnable);
            try (final MetaDataAccess<CmdInstance> metaDataAccess = player.accessTemporaryMetaData(
                    PlayerMetaDataKeys.TEMPORARY_CONFIRM)) {
                metaDataAccess.set(cmd);
            }
        }, TaskTime.ticks(1L));
    }

}

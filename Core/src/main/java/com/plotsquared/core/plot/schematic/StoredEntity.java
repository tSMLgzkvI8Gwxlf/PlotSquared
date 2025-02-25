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
package com.plotsquared.core.plot.schematic;

import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.entity.Entity;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.util.Location;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * An implementation of {@link Entity} that stores a {@link BaseEntity} with it.
 *
 * <p>Calls to {@link #getState()} return a clone.</p>
 */
abstract class StoredEntity implements Entity {

    private final Location location;
    private final BaseEntity entity;

    /**
     * Create a new instance.
     *
     * @param location the location
     * @param entity   the entity (which will be copied)
     */
    StoredEntity(Location location, BaseEntity entity) {
        checkNotNull(location);
        checkNotNull(entity);
        this.location = location;
        this.entity = new BaseEntity(entity);
    }

    /**
     * Get the entity state. This is not a copy.
     *
     * @return the entity
     */
    BaseEntity getEntity() {
        return entity;
    }

    @Override
    public BaseEntity getState() {
        return new BaseEntity(entity);
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public Extent getExtent() {
        return location.getExtent();
    }

}

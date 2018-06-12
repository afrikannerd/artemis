/*
 * Copyright (C) 2018 afrikannerd <https://github.com/afrikannerd>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.afrikannerd.core.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author afrikannerd <https://github.com/afrikannerd>
 * @version "0.1pa"
 */
public final class LocalDatabaseConnection {

    /**
     *
     */
    private static volatile Connection link = null;

    private LocalDatabaseConnection() {

    }

    /**
     * @param args
     * @throws ClassNotFoundException
     * @throws SQLException Thread safe singleton LocalDatabaseConnection
     * Connection
     */
    public static Connection getInstance() {
        if (link == null) {
            synchronized (LocalDatabaseConnection.class) {
                if (link == null) {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        link = DriverManager.getConnection("jdbc:mysql://127.0.0.1/artemis", "root", "banter");
                    } catch (Exception ex) {
                        Logger.getLogger(LocalDatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
        return link;
    }

    @Override
    protected Object clone() {
        return false;
    }

}

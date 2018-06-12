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
package com.github.afrikannerd.core;

import com.github.afrikannerd.core.connection.LocalDatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Facilitates Authentication handling
 *
 * @author afrikannerd <https://github.com/afrikannerd>
 * @version "0.1pa"
 */
public class Auth {

    private static Connection link = LocalDatabaseConnection.getInstance();

    public static ResultSet login(String username) throws SQLException {
        String sql = "Select * from users where username=?";
        PreparedStatement st = link.prepareStatement(sql);
        st.setString(1, username);
        ResultSet rs = st.executeQuery();
        return rs;
    }

}

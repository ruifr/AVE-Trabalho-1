/*
 * Copyright (c) 2017, Miguel Gamboa
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

package util;

import java.io.*;

/**
 * @author Miguel Gamboa
 *         created on 08-03-2017
 */
public class FileRequest extends Request {
    public FileRequest() {
        super(FileRequest::getStream);
    }
    private static final String PATH = "C:\\Users\\Marcos\\Desktop\\MPD\\MPD-Trabalho-1\\Vibehub\\src\\test\\resources\\";
    public static InputStream getStream(String path) {
        String[] parts = path.split("/");
        path = "";
        for(int i = 3 ; i < parts.length ; i++) path+=parts[i];
        path = path
                .replace('?', '-')
                .replace('&', '-')
                .replace('=', '-')
                .replace(',', '-')
                .substring(0, path.length());
        try {
            return new FileInputStream(PATH+path+".txt");//ClassLoader.getSystemResource(path).openStream();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
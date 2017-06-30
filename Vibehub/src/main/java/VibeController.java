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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.lang.ClassLoader.getSystemResource;
import static java.nio.file.Files.lines;

public class VibeController {

    private final String root;

    private final VibeService api;
    private final String getSearchView = load("views/search.html");
    private final String getVenues = load("views/venues.html");
    //private final String events = load("views/events.html");
    private final String getVenueRow = load("views/venueRow.html");
    //private final String eventRow = load("views/eventRow.html");

    public VibeController(VibeService api) {
        this.api = api;
        try {
            this.root = getSystemResource(".").toURI().getPath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSearch(HttpServletRequest req) {
        return getSearchView;
    }

    private static String load(String path) {
        return load(ClassLoader.getSystemResource(path));
    }

    private static String load(URL url) {
        try {
            return load(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static String load(URI uri) {
        try {
            Path path = Paths.get(uri);
            return lines(path).collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String searchVenues(HttpServletRequest req) {
        String city = req.getParameter("name");
        String view = getVenues;
        String vrow = getVenueRow;
        String rows = api.searchVenues(city)
                .get()
                .limit(30)
                .map(v -> String.format(vrow, v.getName()))
                .collect(Collectors.joining());
        return String.format(view, rows);
    }

    public String getEvents(HttpServletRequest req) {
        String id = req.getParameter("id");
        String view = getVenues;
        String vrow = getVenueRow;
        String rows = api.getEvents(id)
                .get()
                .limit(30)
                .map(e -> String.format(vrow, e.toString())
                .collect(Collectors.joining());
        return String.format(view, rows);
    }
}
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

import model.Artist;
import model.Event;
import model.Track;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.ClassLoader.getSystemResource;
import static java.nio.file.Files.lines;

public class VibeController {

    private final String root;

    private final VibeService api;
    private final String getSearchView = load("views/search.html");
    private final String getVenues = load("views/venues.html");
    private final String getEvents = load("views/events.html");
    private final String getArtist = load("views/artist.html");
    private final String getTracks = load("views/tracks.html");
    private final String getVenueRow = load("views/venueRow.html");
    private final String getEventRow = load("views/eventRow.html");
    private final String getTrackRow = load("views/trackRow.html");
    private final String notfound = load("views/notfound.html");

    public VibeController(VibeServiceCache api) {
        this.api = api;
        try {
            this.root = getSystemResource(".").toURI().getPath() + "cache/";
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
                .map(v -> String.format(vrow, v.getId(), v.getName()))
                .collect(Collectors.joining());
        return String.format(view, rows);
    }

    public String getEvents(HttpServletRequest req) {
        String id = req.getParameter("id");
        CompletableFuture<String> events = eventsCache.get(id);
        if(events == null) {
            String view = loadFile(id);
            if (view == null) {
                view = String.format(getEvents, api.getEvents(id).get().map(e -> String.format(getEventRow, e.getArtist().getMbid(), e.getArtistName(), e.getEventDate(), e.getSetlistId(), "tracks")).collect(Collectors.joining()));
                writeFile(id, view);
            }
            events = new CompletableFuture<>();
            events.complete(view);
            artistCache.putIfAbsent(id, events);
        }
        return events.join();
    }

    public String getArtist(HttpServletRequest req) {
        String mbid = req.getParameter("mbid");
        CompletableFuture<String> artist = artistCache.get(mbid);
        if(artist == null) {
            String view = loadFile(mbid);
            if (view == null) {
                Artist join = api.getArtist(mbid).join();
                view = String.format(getArtist, join.getUrl(), join.getName(), join.getMbid(), join.getBio());
                writeFile(mbid, view);
            }
            artist = new CompletableFuture<>();
            artist.complete(view);
            artistCache.putIfAbsent(mbid, artist);
        }
        return artist.join();
    }

    public String getTracks(HttpServletRequest req) {
        String id = req.getParameter("id");
        String row = getTrackRow;
        CompletableFuture<String> tracks = tracksCache.get(id);

        if(tracks == null) {
            String view = loadFile(id);
            if (view == null) {
                Event e = api.getEvent(id).join();
                CompletableFuture<Track>[] join = e.getTracks();
                String s = "";
                for(int i = 0 ; i < join.length ; ++i)
                    s+=(String.format(row, join[i].join().getName()));
                view = join.length == 0 ? notfound : String.format(getTracks, s);
                writeFile(id, view);
            }
            tracks = new CompletableFuture<>();
            tracks.complete(view);
            artistCache.putIfAbsent(id, tracks);
        }
        return tracks.join();
    }
    private final ConcurrentHashMap<String, CompletableFuture<String>> eventsCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CompletableFuture<String>> artistCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CompletableFuture<String>> tracksCache = new ConcurrentHashMap<>();

    private String loadFile(String id) {
        String path = root + id + ".html";
        File file = new File(path);
        URI uri = !file.exists() ? null : file.toURI();
        return uri == null ? null : load(uri);
    }

    private String writeFile(String id, String view) {
        try {
            String path = root + id + ".html";
            try (FileWriter fw = new FileWriter(path)) {
                fw.write(view);
                fw.flush();
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return view;
    }
}
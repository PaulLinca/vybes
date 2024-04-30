package com.vybes.service.writer.repository;

import com.vybes.service.writer.entity.Vybe;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VybeRepository {
    private final JdbcTemplate template;

    public void save(Vybe vybe) {
        String sql =
                "insert into vybe (songName, spotifyTrackId, spotifyAlbumId, spotifyArtistIds, postedDate) values (?,?,?,?,?)";

        template.update(
                sql,
                vybe.getSongName(),
                vybe.getSpotifyTrackId(),
                vybe.getSpotifyAlbumId(),
                createSqlArray(vybe.getSpotifyArtistIds()),
                Timestamp.valueOf(vybe.getPostedDate().toLocalDateTime()));
    }

    public List<Vybe> getAllVybes() {
        String sql = "select * from vybe";

        RowMapper<Vybe> mapper =
                (rs, rowNum) ->
                        Vybe.builder()
                                .id(rs.getInt("id"))
                                .songName(rs.getString("songName"))
                                .spotifyTrackId(rs.getString("spotifyTrackId"))
                                .spotifyAlbumId(rs.getString("spotifyAlbumId"))
                                .spotifyArtistIds(
                                        getListFromSqlArray(rs.getArray("spotifyArtistIds")))
                                .postedDate(
                                        Optional.ofNullable(rs.getTimestamp("postedDate"))
                                                .map(Timestamp::toInstant)
                                                .map(t -> t.atZone(ZoneId.systemDefault()))
                                                .orElse(null))
                                .build();

        return template.query(sql, mapper);
    }

    private List<String> getListFromSqlArray(Array spotifyArtistIds) throws SQLException {
        List<String> ids = new ArrayList<>();

        if (spotifyArtistIds == null) {
            return ids;
        }
        ResultSet resultSet = spotifyArtistIds.getResultSet();
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    private Array createSqlArray(List<String> list) {
        java.sql.Array varcharArray = null;
        try {
            varcharArray =
                    template.getDataSource()
                            .getConnection()
                            .createArrayOf("varchar", list.toArray());
        } catch (SQLException ignore) {
        }
        return varcharArray;
    }
}

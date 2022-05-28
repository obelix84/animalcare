package ru.animalcare.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.animalcare.domain.Animal;
import ru.animalcare.domain.Photo;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;

@Repository
@RequiredArgsConstructor
public class PhotoRepository {

    private static final String FIND_FILE_BY_ID = "SELECT * FROM photos WHERE id = ?";
    private static final String CREATE_FILE = "INSERT INTO photos( name, size, keyPhoto,uploadDate, comment) VALUES ( ?, ?, ?, ?, ?)";
    private static final String DELETE_FILE_BY_ID = "DELETE FROM photos WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    public Photo create(final Photo file) {
        LocalDate uploadDate = LocalDate.now();
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(x -> {
            PreparedStatement preparedStatement = x.prepareStatement(CREATE_FILE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, file.getName());
            preparedStatement.setLong(2, file.getSize());
            preparedStatement.setString(3, file.getKeyPhoto());
            preparedStatement.setDate(4, Date.valueOf(uploadDate));
            preparedStatement.setString(5, file.getComment());

            return preparedStatement;
        }, keyHolder);

        return file.toBuilder()
                .id(keyHolder.getKey().longValue())
                .uploadDate(uploadDate)
                .build();
    }

    // поиск по id c использованием jdbcTemplate и RowMapper.
    public Photo findById(Long photoId) {
        return jdbcTemplate.queryForObject(FIND_FILE_BY_ID, rowMapper(), photoId);
    }

    // реализация RowMapper для нашего конкретного случая,
    // для сопоставления данных из БД и полей модели.
    private RowMapper<Photo> rowMapper() {
        return (rs, rowNum) -> Photo.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("photo_name"))
                .size(rs.getLong("photo_size"))
                .keyPhoto(rs.getString("keyPhotos"))
                .uploadDate(rs.getObject("upload_date", LocalDate.class))
                .comment(rs.getString("photo_comment"))
                .build();
    }

    public void delete(Long fileId) {
        jdbcTemplate.update(DELETE_FILE_BY_ID, fileId);
    }

}

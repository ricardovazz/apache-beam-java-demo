package com.example.utils;

import org.apache.beam.vendor.grpc.v1p60p1.com.google.gson.Gson;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.vendor.grpc.v1p60p1.com.google.gson.Gson;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ResultSetToJsonRowMapper implements JdbcIO.RowMapper<String> {
    @Override
    public String mapRow(ResultSet resultSet) throws Exception {
        ResultSetMetaData metadata = resultSet.getMetaData();
        Map<String, Object> row = IntStream.range(1, metadata.getColumnCount() + 1)
                .mapToObj(i -> {
                    try {
                        return KV.of(metadata.getColumnName(i), resultSet.getObject(i));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toMap(KV::getKey, KV::getValue));
        return new Gson().toJson(row);
    }
}

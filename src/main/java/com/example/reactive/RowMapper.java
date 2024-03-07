package com.example.reactive;

import io.r2dbc.spi.RowMetadata;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.values.Row;

public class RowMapper {
    private static final Schema schema = Schema.builder()
            .addStringField("id")
            .addStringField("name")
            .build();

    public static Row mapR2dbcRowToBeamRow(io.r2dbc.spi.Row r2dbcRow, RowMetadata metadata) {
        return Row.withSchema(schema)
                .addValues(
                        r2dbcRow.get("id", String.class),
                        r2dbcRow.get("name", String.class)
                )
                .build();
    }
}


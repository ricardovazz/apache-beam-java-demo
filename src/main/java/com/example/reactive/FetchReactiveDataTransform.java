/*
package com.example.reactive;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class FetchReactiveDataTransform extends PTransform<PCollection<String>, PCollection<Row>> {
    private final String query;

    public FetchReactiveDataTransform(String query) {
        this.query = query;
    }

    @Override
    public PCollection<Row> expand(PCollection<String> input) {
        return input.apply("Fetch Data", ParDo.of(new FetchDataDoFn(query)));
    }

    static class FetchDataDoFn extends DoFn<String, Row> {
        private transient ConnectionFactory connectionFactory;
        private final String query;

        FetchDataDoFn(String query) {
            this.query = query;
        }

        @Setup
        public void setup() {
            // Initialize ConnectionFactory here
            this.connectionFactory = ConnectionFactories.get(ConnectionFactoryOptions.builder()
                    .option(ConnectionFactoryOptions.DRIVER, "mysql")
                    .option(ConnectionFactoryOptions.HOST, "127.0.0.1")
                    .option(ConnectionFactoryOptions.PORT, 3306) // Default MySQL port
                    .option(ConnectionFactoryOptions.USER, "root")
                    .option(ConnectionFactoryOptions.PASSWORD, "")
                    .option(ConnectionFactoryOptions.DATABASE, "test")
                    .build());
        }

        @ProcessElement
        public void processElement(ProcessContext c) {
            // Use connectionFactory to fetch data and emit rows
            Flux<Row> fetchedData = createRowFluxBeam(connectionFactory, query);
            fetchedData.subscribe(c::output, throwable -> {*/
/* Error handling *//*
});
        }
    }

    public static Flux<Row> createRowFluxBeam(ConnectionFactory connectionFactory, String query) {
        return Flux.usingWhen(
                connectionFactory.create(), //Create the resource
                connection -> Flux.from(connection.createStatement(query).execute())
                        .flatMap(result -> result.map(RowMapper::mapR2dbcRowToBeamRow) ), //Use the resource
                connection -> Mono.from(connection.close())//Clean up the resource
        );
    }
}
*/

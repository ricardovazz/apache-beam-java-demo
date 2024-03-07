/*
package com.example.reactive;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class R2dbcIO {

    public static Read read() {
        return new Read();
    }

    public static class Read extends PTransform<PBegin, PCollection<org.apache.beam.sdk.values.Row>> {
        private String query;
        private ConnectionFactory connectionFactory;
        private Function<io.r2dbc.spi.Row, String> rowMapper;

        public Read withConnectionFactory(ConnectionFactory connectionFactory) {
            this.connectionFactory = connectionFactory;
            return this;
        }

        public Read withQuery(String query) {
            this.query = query;
            return this;
        }

        public Read withRowMapper(Function<io.r2dbc.spi.Row, String> rowMapper) {
            this.rowMapper = rowMapper;
            return this;
        }


        @Override
        public PCollection<org.apache.beam.sdk.values.Row> expand(PBegin input) {
            return input.apply("Fetch Data", ParDo.of(new FetchDataDoFn(query, connectionFactory)));
        }

        static class FetchDataDoFn extends DoFn<PBegin, org.apache.beam.sdk.values.Row> {
            private final String query;
            private transient ConnectionFactory connectionFactory;

            FetchDataDoFn(String query, ConnectionFactory connectionFactory) {
                this.query = query;
                this.connectionFactory = connectionFactory;
            }


            @ProcessElement
            public void processElement(ProcessContext c) {
                // Use connectionFactory to fetch data and emit rows
                Flux<org.apache.beam.sdk.values.Row> fetchedData = createRowFluxBeam(connectionFactory, query);
                fetchedData.subscribe(c::output, throwable -> {*/
/* Error handling *//*
});
            }
        }

        public static Flux<org.apache.beam.sdk.values.Row> createRowFluxBeam(ConnectionFactory connectionFactory, String query) {
            return Flux.usingWhen(
                    connectionFactory.create(), //Create the resource
                    connection -> Flux.from(connection.createStatement(query).execute())
                            .flatMap(result -> result.map(RowMapper::mapR2dbcRowToBeamRow) ), //Use the resource
                    connection -> Mono.from(connection.close())//Clean up the resource
            );
        }
    }
}
*/

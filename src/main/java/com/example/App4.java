package com.example;


import com.example.utils.CustomRow;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.options.*;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptors;
import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class App4 {
    public interface Options extends StreamingOptions {
        @Description("Input text to print.")
        @Default.String("My input text")
        String getInputText();

        void setInputText(String value);
    }

    public static CustomRow convertToCustomRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        // Example conversion logic
        Integer id = row.get("id", Integer.class);
        String title = row.get("title", String.class);
        String description = row.get("description", String.class);
        Boolean published = row.get("published", Boolean.class);
        return new CustomRow(title, "metadata");
    }

    public static Flux<CustomRow> publisher(ConnectionFactory connectionFactory, String query) {
        return Flux.usingWhen(
                connectionFactory.create(), //Create the resource
                connection -> Flux.from(connection.createStatement(query).execute())
                        .flatMap(result -> result.map(App4::convertToCustomRow) ), //Use the resource
                connection -> Mono.from(connection.close()) //Clean up the resource
        );
    }

    public static Flux<CustomRow> publisherConvertedToFlux(ConnectionFactory connectionFactory, String query){
        return Flux.from(connectionFactory.create())
                .flatMap( connection ->
                        Flux.from( connection.createStatement(query)
                                .execute())
                                .flatMap(result -> result.map(App4::convertToCustomRow) )
                                .doFinally(signalType -> connection.close())
                );
    }

    public static List<CustomRow> fetchData(ConnectionFactory connectionFactory) {
        return publisher(connectionFactory, "select * from tutorial").collectList().block();
    }


    public static void main(String[] args) {
        var options = PipelineOptionsFactory.fromArgs(args).withValidation().as(Options.class);
        var pipeline = Pipeline.create(options);

        ConnectionFactory connectionFactory = ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, "mysql")
                .option(ConnectionFactoryOptions.HOST, "127.0.0.1")
                .option(ConnectionFactoryOptions.PORT, 3306) // Default MySQL port
                .option(ConnectionFactoryOptions.USER, "root")
                .option(ConnectionFactoryOptions.PASSWORD, "")
                .option(ConnectionFactoryOptions.DATABASE, "test")
                .build());

        List<CustomRow> fetchedDataList = fetchData(connectionFactory);

        PCollection<CustomRow> dataPCollection = pipeline
                .apply(Create.of(fetchedDataList))
                .setCoder(SerializableCoder.of(CustomRow.class));

        // Print transformation
        dataPCollection.apply(MapElements.into(TypeDescriptors.strings())
                        .via(CustomRow::toString))
                .apply(MapElements.via(new SimpleFunction<String, Void>() {
                    @Override
                    public Void apply(String input) {
                        System.out.println(input);
                        return null;
                    }
                }));

        pipeline.run().waitUntilFinish();
    }
}
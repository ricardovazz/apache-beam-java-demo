package com.example;


import com.example.utils.ResultSetToJsonRowMapper;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.options.*;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptors;

public class App3 {
    public interface Options extends StreamingOptions {
        @Description("Input text to print.")
        @Default.String("My input text")
        String getInputText();

        void setInputText(String value);
    }

    public static PCollection<String> buildPipeline(Pipeline pipeline) {
        JdbcIO.DataSourceConfiguration dataSourceConfiguration =
                JdbcIO.DataSourceConfiguration.create(
                                "com.mysql.cj.jdbc.Driver",
                                "jdbc:mysql://localhost:3306/test")
                        .withUsername("root")
                        .withPassword("");

        JdbcIO.Read<String> readIO =
                JdbcIO.<String>read()
                        .withDataSourceConfiguration(dataSourceConfiguration)
                        .withQuery("select * from tutorial")
                        // Transform the resultset to Json.  Multiple results can be returned and Json is easy to serialize.
                        .withRowMapper(new ResultSetToJsonRowMapper())
                        .withCoder(StringUtf8Coder.of());


        return pipeline
                .apply("Read from JdbcIO", readIO)
                .apply("Print elements",
                        MapElements.into(TypeDescriptors.strings()).via(x -> {
                            System.out.println(x);
                            return x;
                        }));
    }


    public static void main(String[] args) {
        var options = PipelineOptionsFactory.fromArgs(args).withValidation().as(Options.class);
        var pipeline = Pipeline.create(options);
        App3.buildPipeline(pipeline);
        pipeline.run().waitUntilFinish();
    }
}
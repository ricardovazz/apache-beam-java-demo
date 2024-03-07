// Copyright 2022 Google LLC
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// https://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or https://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

package com.example;

import java.util.Arrays;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.StreamingOptions;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptors;

public class App2 {
    public interface Options extends StreamingOptions {
        @Description("Input text to print.")
        @Default.String("My input text")
        String getInputText();

        void setInputText(String value);
    }

    public static PCollection<String> buildPipeline(Pipeline pipeline, String inputFilePath) {
        return pipeline
                .apply("(1) Read all lines", TextIO.read().from(inputFilePath))
                .apply("(2) Flatmap to a list of words", FlatMapElements.into(TypeDescriptors.strings())
                        .via(line -> Arrays.asList(line.split("\\s"))))
                .apply("(3) Lowercase all", MapElements.into(TypeDescriptors.strings())
                        .via(word -> word.toLowerCase()))
                .apply("(4) Trim punctuations", MapElements.into(TypeDescriptors.strings())
                        .via(word -> trim(word)))
                .apply("(5) Filter stopwords", Filter.by(word -> !isStopWord(word)))
                .apply("(6) Count words", Count.perElement())
                .apply("Print elements",
                        MapElements.into(TypeDescriptors.strings()).via(x -> {
                            System.out.println(x.getKey() + " - " + x.getValue());
                            return x.getKey();
                        }));
    }

    public static void main(String[] args) {
        var options = PipelineOptionsFactory.fromArgs(args).withValidation().as(Options.class);
        var pipeline = Pipeline.create(options);
        App2.buildPipeline(pipeline, "/Users/rdvz/Downloads/beam-starter-java/src/main/java/com/example/text.txt");
        pipeline.run().waitUntilFinish();
    }

    public static boolean isStopWord(String word) {
        String[] stopwords = {"am", "are", "is", "i", "you", "me",
                "he", "she", "they", "them", "was",
                "were", "from", "in", "of", "to", "be",
                "him", "her", "us", "and", "or"};
        for (String stopword : stopwords) {
            if (stopword.compareTo(word) == 0) {
                return true;
            }
        }
        return false;
    }

    public static String trim(String word) {
        return word.replace("(","")
                .replace(")", "")
                .replace(",", "")
                .replace(".", "")
                .replace("\"", "")
                .replace("'", "")
                .replace(":", "")
                .replace(";", "")
                .replace("-", "")
                .replace("?", "")
                .replace("!", "");
    }

}
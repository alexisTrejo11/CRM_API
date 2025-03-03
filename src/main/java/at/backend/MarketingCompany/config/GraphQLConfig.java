package at.backend.MarketingCompany.config;

import graphql.schema.GraphQLScalarType;
import graphql.schema.idl.RuntimeWiring;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.util.Map;

@Configuration
public class GraphQLConfig {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return new RuntimeWiringConfigurer() {
            @Override
            public void configure(RuntimeWiring.Builder builder) {
                builder.scalar(GraphQLScalarType.newScalar()
                        .name("JSON")
                        .description("Custom scalar type for JSON objects")
                        .coercing(new Coercing<Object, Object>() {
                            @Override
                            public Object serialize(Object dataFetcherResult) throws CoercingSerializeException {
                                try {
                                    return objectMapper.convertValue(dataFetcherResult, Map.class);
                                } catch (Exception e) {
                                    throw new CoercingSerializeException("Error serializing JSON: " + e.getMessage());
                                }
                            }

                            @Override
                            public Object parseValue(Object input) throws CoercingParseValueException {
                                try {
                                    return objectMapper.convertValue(input, Map.class);
                                } catch (Exception e) {
                                    throw new CoercingParseValueException("Error parsing JSON value: " + e.getMessage());
                                }
                            }

                            @Override
                            public Object parseLiteral(Object input) throws CoercingParseLiteralException {
                                if (input instanceof StringValue) {
                                    try {
                                        return objectMapper.readValue(((StringValue) input).getValue(), Map.class);
                                    } catch (Exception e) {
                                        throw new CoercingParseLiteralException("Error parsing JSON literal: " + e.getMessage());
                                    }
                                }
                                throw new CoercingParseLiteralException("Expected a JSON string but got: " + input.getClass().getSimpleName());
                            }
                        })
                        .build());
            }
        };
    }
}
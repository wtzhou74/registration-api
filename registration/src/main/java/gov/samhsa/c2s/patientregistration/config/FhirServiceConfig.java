package gov.samhsa.c2s.patientregistration.config;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.validation.FhirValidator;
import gov.samhsa.c2s.patientregistration.service.util.FhirResourceConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "hie-connection.fhir.enabled", havingValue = "true")
public class FhirServiceConfig {

    @Value("${hie-connection.fhir.serverUrl}")
    private String fhirServerUrl;

    @Value("${hie-connection.fhir.fhirClientSocketTimeoutInMs}")
    private String fhirClientSocketTimeout;

    // Create a context
    private FhirContext fhirContext = FhirContext.forDstu2();

    @Bean
    public IGenericClient fhirClient() {
        // Create a client
        fhirContext.getRestfulClientFactory().setSocketTimeout(Integer.parseInt(fhirClientSocketTimeout));
        return fhirContext.newRestfulGenericClient(fhirServerUrl);
    }

    @Bean
    public IParser fhirXmlParser() {
        return fhirContext.newXmlParser();
    }

    @Bean
    public IParser fhirJsonParser() {
        return fhirContext.newJsonParser();
    }

    @Bean
    public FhirValidator fhirValidator() {
        return fhirContext.newValidator();
    }

    @Bean
    public FhirResourceConverter fhirResourceConverter() {
        return new FhirResourceConverter();
    }
}
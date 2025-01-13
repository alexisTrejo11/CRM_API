package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.ServicePackageInput;
import at.backend.CRM.Models.ServicePackage;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-11T23:34:05-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 23.0.1 (Homebrew)"
)
@Component
public class ServicePackageMappersImpl implements ServicePackageMappers {

    @Override
    public ServicePackage inputToEntity(ServicePackageInput input) {
        if ( input == null ) {
            return null;
        }

        ServicePackage servicePackage = new ServicePackage();

        servicePackage.setName( input.name() );
        servicePackage.setDescription( input.description() );
        servicePackage.setPrice( input.price() );

        return servicePackage;
    }

    @Override
    public ServicePackage inputToUpdatedEntity(ServicePackage existingUser, ServicePackageInput input) {
        if ( input == null ) {
            return existingUser;
        }

        existingUser.setName( input.name() );
        existingUser.setDescription( input.description() );
        existingUser.setPrice( input.price() );

        return existingUser;
    }
}

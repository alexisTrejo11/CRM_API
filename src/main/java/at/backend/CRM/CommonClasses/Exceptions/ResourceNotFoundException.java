package at.backend.CRM.CommonClasses.Exceptions;


public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String resourceType, Long id) {
        super(resourceType + " not found with ID: " + id, "RESOURCE_NOT_FOUND");
    }
}
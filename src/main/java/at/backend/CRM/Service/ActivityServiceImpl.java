package at.backend.CRM.Service;

import at.backend.CRM.Inputs.ActivityInput;
import at.backend.CRM.Mappers.ActivityMappers;
import at.backend.CRM.Models.Activity;
import at.backend.CRM.Repository.ActivityRepository;
import at.backend.CRM.Utils.Result;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements CommonService<Activity, ActivityInput> {

    private final ActivityRepository companyRepository;
    private final ActivityMappers activityMappers;

    @Override
    public List<Activity> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<Activity> findById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public Activity create(ActivityInput input) {
        Activity company = activityMappers.createInputToEntity(input);

        companyRepository.saveAndFlush(company);

        return company;
    }

    @Override
    public Activity update(Long id, ActivityInput input) {
        Activity existingActivity = companyRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("Activity not found"));

        Activity companyUpdated = activityMappers.updateInputToEntity(existingActivity, input);
        companyRepository.saveAndFlush(companyUpdated);

        return companyUpdated;
    }

    @Override
    public void delete(Long id) {
        boolean isActivityExisting = companyRepository.existsById(id);
        if (!isActivityExisting) {
            throw new EntityNotFoundException("Activity not found");
        }

        companyRepository.deleteById(id);
    }

    @Override
    public Result<Void> validate(ActivityInput input) {
        return null;
    }
}

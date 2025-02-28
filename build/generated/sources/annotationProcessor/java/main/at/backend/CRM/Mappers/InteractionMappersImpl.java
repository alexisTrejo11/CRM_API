package at.backend.CRM.Mappers;

import at.backend.CRM.Inputs.InteractionInput;
import at.backend.CRM.Models.Interaction;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-26T20:16:57-0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class InteractionMappersImpl implements InteractionMappers {

    @Override
    public Interaction inputToEntity(InteractionInput input) {
        if ( input == null ) {
            return null;
        }

        Interaction interaction = new Interaction();

        interaction.setType( input.type() );
        interaction.setDateTime( input.dateTime() );
        interaction.setDescription( input.description() );
        interaction.setOutcome( input.outcome() );
        interaction.setFeedbackType( input.feedbackType() );
        interaction.setChannelPreference( input.channelPreference() );

        return interaction;
    }

    @Override
    public Interaction inputToUpdatedEntity(Interaction existingInteraction, InteractionInput input) {
        if ( input == null ) {
            return existingInteraction;
        }

        existingInteraction.setType( input.type() );
        existingInteraction.setDateTime( input.dateTime() );
        existingInteraction.setDescription( input.description() );
        existingInteraction.setOutcome( input.outcome() );
        existingInteraction.setFeedbackType( input.feedbackType() );
        existingInteraction.setChannelPreference( input.channelPreference() );

        return existingInteraction;
    }
}

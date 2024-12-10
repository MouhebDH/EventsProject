package tn.esprit.eventsproject.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.ParticipantRepository;
import tn.esprit.eventsproject.repositories.LogisticsRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventServicesImplTest {

    @Mock
    private EventRepository eventRepository;
    
    @Mock
    private ParticipantRepository participantRepository;
    
    @Mock
    private LogisticsRepository logisticsRepository;
    
    private EventServicesImpl eventServices;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
        eventServices = new EventServicesImpl(eventRepository, participantRepository, logisticsRepository);
    }

    @Test
    public void testAddParticipant() {
        Participant participant = new Participant("John Doe", 30);
        when(participantRepository.save(any(Participant.class))).thenReturn(participant);

        Participant result = eventServices.addParticipant(participant);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(participantRepository, times(1)).save(participant);
    }

    @Test
    public void testAddAffectEvenParticipant() {
        Event event = new Event("Conference");
        Participant participant = new Participant("John Doe", 30);
        participant.setIdPart(1);
        when(participantRepository.findById(1)).thenReturn(java.util.Optional.of(participant));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event result = eventServices.addAffectEvenParticipant(event, 1);

        assertNotNull(result);
        verify(participantRepository, times(1)).findById(1);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    public void testAddAffectLog() {
        Logistics logistics = new Logistics();
        Event event = new Event("Conference");
        when(eventRepository.findByDescription("Conference")).thenReturn(event);
        when(logisticsRepository.save(any(Logistics.class))).thenReturn(logistics);

        Logistics result = eventServices.addAffectLog(logistics, "Conference");

        assertNotNull(result);
        verify(eventRepository, times(1)).findByDescription("Conference");
        verify(logisticsRepository, times(1)).save(logistics);
    }

    // More tests can be added for the other methods

}

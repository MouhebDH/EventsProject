package tn.esprit.eventsproject.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.eventsproject.entities.*;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.ParticipantRepository;
import tn.esprit.eventsproject.repositories.LogisticsRepository;

import java.time.LocalDate;
import java.util.HashSet;

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
        Participant participant = new Participant(0, "John", "Doe", Tache.ORGANISATEUR, new HashSet<>());
        when(participantRepository.save(any(Participant.class))).thenReturn(participant);

        Participant result = eventServices.addParticipant(participant);

        assertNotNull(result);
        assertEquals("John", result.getNom());
        verify(participantRepository, times(1)).save(participant);
    }

    @Test
    public void testAddAffectEvenParticipant() {
        Event event = new Event(0, "Conference", LocalDate.now(), LocalDate.now().plusDays(1), 0.0f, new HashSet<>(), new HashSet<>());
        Participant participant = new Participant(1, "John", "Doe", Tache.INVITE, new HashSet<>());
        when(participantRepository.findById(1)).thenReturn(java.util.Optional.of(participant));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event result = eventServices.addAffectEvenParticipant(event, 1);

        assertNotNull(result);
        verify(participantRepository, times(1)).findById(1);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    public void testAddAffectLog() {
        Logistics logistics = new Logistics(0, "Equipment", true, 100.0f, 10);
        Event event = new Event(0, "Conference", LocalDate.now(), LocalDate.now().plusDays(1), 0.0f, new HashSet<>(), new HashSet<>());
        when(eventRepository.findByDescription("Conference")).thenReturn(event);
        when(logisticsRepository.save(any(Logistics.class))).thenReturn(logistics);

        Logistics result = eventServices.addAffectLog(logistics, "Conference");

        assertNotNull(result);
        verify(eventRepository, times(1)).findByDescription("Conference");
        verify(logisticsRepository, times(1)).save(logistics);
    }
}

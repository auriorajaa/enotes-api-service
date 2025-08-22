package com.example.enotes.scheduler;

import com.example.enotes.entity.Notes;
import com.example.enotes.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotesScheduler {

    @Autowired
    private NotesRepository notesRepo;

    // @Scheduled(cron = "* * * ? * *")
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteNotesScheduler() {
        // System.out.println("========== The function is executed ==========");
        LocalDateTime cutOffDate = LocalDateTime.now().minusDays(7);
        List<Notes> deleteNotes = notesRepo.findAllByIsDeletedAndDeletedOnBefore(true, cutOffDate);

        notesRepo.deleteAll(deleteNotes);
    }
}

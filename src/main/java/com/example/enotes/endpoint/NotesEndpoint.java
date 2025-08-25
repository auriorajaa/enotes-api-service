package com.example.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception;

    @GetMapping("/download/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> getAllNotes();

    @GetMapping("/user-notes")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getAllNotesByUser(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize);

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> searchNotes(@RequestParam(name = "key", defaultValue = "") String key,
                                         @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize);

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;

    @GetMapping("/restore/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;

    @GetMapping("/recycle-bin")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getUserRecycleBinNotes() throws Exception;

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;

    @DeleteMapping("/empty-recycle-bin")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> emptyUserRecycleBin() throws Exception;

    @GetMapping("/favorite/{notesId}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> favoriteNotes(@PathVariable Integer notesId) throws Exception;

    @DeleteMapping("/un-favorite/{favoriteNotesId}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> unFavoriteNotes(@PathVariable Integer favoriteNotesId) throws Exception;

    @GetMapping("/favorite-notes")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getUserFavoriteNotes() throws Exception;

    @GetMapping("/copy/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;

}

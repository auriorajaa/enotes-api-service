package com.example.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.example.enotes.util.Constants.ROLE_USER;
import static com.example.enotes.util.Constants.ROLE_ADMIN;
import static com.example.enotes.util.Constants.ROLE_ANY;
import static com.example.enotes.util.Constants.DEFAULT_PAGE_NO;
import static com.example.enotes.util.Constants.DEFAULT_PAGE_SIZE;
import static com.example.enotes.util.Constants.DEFAULT_KEY_VALUE;

@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

    @PostMapping("/")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception;

    @GetMapping("/download/{id}")
    @PreAuthorize(ROLE_ANY)
    ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;

    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> getAllNotes();

    @GetMapping("/user-notes")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getAllNotesByUser(@RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
                                               @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);

    @GetMapping("/search")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> searchNotes(@RequestParam(name = "key", defaultValue = DEFAULT_KEY_VALUE) String key,
                                         @RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
                                         @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);

    @GetMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;

    @GetMapping("/restore/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;

    @GetMapping("/recycle-bin")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getUserRecycleBinNotes() throws Exception;

    @DeleteMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;

    @DeleteMapping("/empty-recycle-bin")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> emptyUserRecycleBin() throws Exception;

    @GetMapping("/favorite/{notesId}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> favoriteNotes(@PathVariable Integer notesId) throws Exception;

    @DeleteMapping("/un-favorite/{favoriteNotesId}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> unFavoriteNotes(@PathVariable Integer favoriteNotesId) throws Exception;

    @GetMapping("/favorite-notes")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getUserFavoriteNotes() throws Exception;

    @GetMapping("/copy/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;

}
